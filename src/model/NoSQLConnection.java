package model;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

//import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chris on 2015-12-10.
 */
public class NoSQLConnection implements DBCommunication{

    private String user;
    private String password;
    private String host;
    private String database;
    private MongoDatabase db;
    private MongoClient client;
    private MongoCursor<Document> cursor =  null;
    public NoSQLConnection(){

    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDb() {
        return db;
    }

    @Override
    public boolean connectToDatabase() {


        List<MongoCredential> credentials = new ArrayList<>();
        List<ServerAddress> serverAdresses = new ArrayList<>();
        serverAdresses.add(new ServerAddress("83.251.46.169"));
        credentials.add(
                MongoCredential.createCredential(
                        "clientapp",
                        "medialibrary",
                        "1234567890".toCharArray()
                )
        );
        client = new MongoClient(serverAdresses, credentials);

        db = client.getDatabase("medialibrary");


        return true; //temp
    }

    @Override
    public void closeConnection() {
        client.close();
    }

    @Override
    public void addAlbum(String title, String artist, String nationality, Date date, Genre genre, Grade grade){

        try{
            cursor =  db.getCollection("artist").find(Filters.and(Filters.eq("name", artist), Filters.eq("nationality", nationality))).iterator();
            Document docId = null;
            if(!cursor.hasNext()){
                System.out.println("finns inte");
                db.getCollection("artist").insertOne(new Document("name", artist).append("nationality", nationality));
                cursor =  db.getCollection("artist").find().sort(new BasicDBObject("_id", -1)).limit(1).iterator();

                System.out.println(db.getCollection("artist").find().sort(new BasicDBObject("_id", -1)).limit(1).toString());
            }

            while(cursor.hasNext()){
                docId = cursor.next();
            }

            System.out.println(docId.getString("name"));
            db.getCollection("album").insertOne(new Document("name", title).append("artist", docId.getObjectId("_id")).append("ReleaseDate", date).append("genre", genre.getGenreID()).append("grade", grade.getGradeID())); //här behöver vi fixa genreid och genrenamn

        }finally{
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}

        }


    }

    @Override
    public ArrayList<Object> getAlbumsByArtist(String name){

        try{
            ArrayList<Object> list = new ArrayList<>();
            Document result;
            Document docId = null;

            cursor = db.getCollection("artist").find(new Document("name", new BasicDBObject("$regex",name))).iterator();
            while(cursor.hasNext()){
                docId = cursor.next();
            }


            if(docId != null){
                cursor = db.getCollection("album").find(new Document("artist", docId.getObjectId("_id"))).iterator();
                while(cursor.hasNext()){
                    result = cursor.next();
                    Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
                    list.add(a);
                }
            }

            System.out.println(list);

            return list;
        }finally{
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}

        }

    }

    @Override
    public ArrayList<Object> getAlbumByTitle(String name) {

        try{
            cursor = db.getCollection("album").find(new Document("name", new BasicDBObject("$regex",name))).iterator();
            ArrayList<Object> list = new ArrayList<>();
            Document result;
            while(cursor.hasNext()){
                result = cursor.next();
                Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
                list.add(a);
            }
            System.out.println(list);
            return list;
        }finally {
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public ArrayList<Genre> getGenre() {

        try{
            cursor = db.getCollection("genre").find().iterator();
            ArrayList<Genre> list = new ArrayList<>();
            Document result;

            while(cursor.hasNext()){
                result = cursor.next();
                Genre g = new Genre(result.getObjectId("_id"), result.getString("name"));
                list.add(g);
            }
            System.out.println(list);
            return list;
        }finally {
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}
        }

    }

    @Override
    public ArrayList<Grade> getGrades() {


        try{
            cursor = db.getCollection("grade").find().iterator();
            ArrayList<Grade> list = new ArrayList<>();
            Document result;
            while(cursor.hasNext()){

                result = cursor.next();
                Grade g = new Grade(result.getObjectId("_id"), result.getString("name"));
                list.add(g);
            }
            System.out.println(list);
            return list;
        }finally {
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}
        }

    }

    @Override
    public ArrayList<Object> getAlbumByGenre(ObjectId genre) {

        try{
            ArrayList<Object> list = new ArrayList<>();
            Document result;
            cursor = db.getCollection("album").find(new Document("genre", genre)).iterator();

            while(cursor.hasNext()){
                result = cursor.next();
                Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
                list.add(a);
            }

            System.out.println(list);
            return list;
        }finally {
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public ArrayList<Object> getAlbumByGrade(ObjectId grade) {


        try{
            ArrayList<Object> list = new ArrayList<>();
            Document result;
            cursor = db.getCollection("album").find(new Document("grade", grade)).iterator();

            while(cursor.hasNext()){
                result = cursor.next();
                Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
                list.add(a);
            }

            System.out.println(list);
            return list;
        }finally {
            try { if (cursor != null) cursor.close(); } catch (Exception e) {e.printStackTrace();}
        }
    }

    public String getConnectedUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return user;
    }
}
