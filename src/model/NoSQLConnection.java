package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

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
        db = client.getDatabase("test");



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
        return null;
    }

    @Override
    public ArrayList<Grade> getGrades() {
        return null;
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
