package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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

        client = new MongoClient("192.168.0.4");
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
        return null;
    }

    @Override
    public ArrayList<Object> getAlbumByTitle(String name) {
        return null;
    }

    @Override
    public ArrayList<Genre> getGenre() {
        MongoCursor<Document> cursor = db.getCollection("genre").find().iterator();
        ArrayList<Genre> list = new ArrayList<>();
        Document result = null;
        while(cursor.hasNext()){
            result = cursor.next();
            Genre g = new Genre(1, result.getString("name"));
            list.add(g);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Grade> getGrades() {
        MongoCursor<Document> cursor = db.getCollection("grade").find().iterator();
        ArrayList<Grade> list = new ArrayList<>();
        Document result = null;
        while(cursor.hasNext()){
            result = cursor.next();
            Grade g = new Grade(1, result.getString("name"));
            list.add(g);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public ArrayList<Object> getAlbumByGenre(int genre) {
        return null;
    }

    @Override
    public ArrayList<Object> getAlbumByGrade(int grade) {
        return null;
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
        return getUsername();
    }
}
