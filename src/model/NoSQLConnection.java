package model;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by chris on 2015-12-10.
 */
public class NoSQLConnection implements DBCommunication{

    private String user;
    private String password;
    private String host;
    private String database;
    private MongoDatabase db;
    MongoClient client;

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

        client = new MongoClient("83.251.46.169");
        db = client.getDatabase("medialibrary");



        return true; //temp
    }

    @Override
    public void closeConnection() {
        client.close();
    }

    @Override
    public void addAlbum(String title, String artist, String nationality, Date date, Genre genre, Grade grade) throws SQLException {

    }

    @Override
    public ArrayList<Object> getAlbumsByArtist(String name){
        ArrayList<Object> list = new ArrayList<>();
        Document result;
        Document docId = null;

        MongoCursor<Document> cursor = db.getCollection("artist").find(new Document("name", new BasicDBObject("$regex",name))).iterator();
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
    }

    @Override
    public ArrayList<Object> getAlbumByTitle(String name) {
        MongoCursor<Document> cursor = db.getCollection("album").find(new Document("name", new BasicDBObject("$regex",name))).iterator();
        ArrayList<Object> list = new ArrayList<>();
        Document result;
        while(cursor.hasNext()){
            result = cursor.next();
            Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
            list.add(a);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Genre> getGenre() {
        MongoCursor<Document> cursor = db.getCollection("genre").find().iterator();
        ArrayList<Genre> list = new ArrayList<>();
        Document result;

        while(cursor.hasNext()){
            result = cursor.next();
            Genre g = new Genre(result.getObjectId("_id"), result.getString("name"));
            list.add(g);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Grade> getGrades() {
        MongoCursor<Document> cursor = db.getCollection("grade").find().iterator();
        ArrayList<Grade> list = new ArrayList<>();
        Document result;
        while(cursor.hasNext()){

            result = cursor.next();
            Grade g = new Grade(result.getObjectId("_id"), result.getString("name"));
            list.add(g);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Object> getAlbumByGenre(ObjectId genre) {
        ArrayList<Object> list = new ArrayList<>();
        Document result;
        MongoCursor<Document> cursor = db.getCollection("album").find(new Document("genre", genre)).iterator();

        while(cursor.hasNext()){
            result = cursor.next();
            Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
            list.add(a);
        }

        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Object> getAlbumByGrade(ObjectId grade) {
        ArrayList<Object> list = new ArrayList<>();
        Document result;
        MongoCursor<Document> cursor = db.getCollection("album").find(new Document("grade", grade)).iterator();

        while(cursor.hasNext()){
            result = cursor.next();
            Album a = new Album(result.getObjectId("_id"), result.getString("name"), result.getDate("ReleaseDate"));
            list.add(a);
        }

        System.out.println(list);
        return list;
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
