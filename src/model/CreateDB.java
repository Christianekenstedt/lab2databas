package model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by chris on 2015-12-10.
 */
public class CreateDB {
    private MongoDatabase db;
    private MongoCollection album;
    private MongoCollection artist;
    private MongoCollection grade;
    private MongoCollection genre;
    private MongoCollection review;
    private MongoCollection user;
    private ArrayList<Document> genres = new ArrayList<>();
    private ArrayList<Document> grades = new ArrayList<>();

    public CreateDB(MongoDatabase db) {
        //Fill the database, probably only one-time-use
        this.db = db;


    }

    public void createCollections() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");

        album = db.getCollection("album");
        artist = db.getCollection("artist");
        grade = db.getCollection("grade");
        genre = db.getCollection("genre");
        review = db.getCollection("review");
        user = db.getCollection("user");


        genres.add(new Document("name", "Rock"));genres.add(new Document("name", "Pop"));genres.add(new Document("name", "Dance"));genres.add(new Document("name", "Reggae"));genres.add(new Document("name", "RnB"));
        grades.add(new Document("name", "Bad")); new Document("name", "Meh"); new Document("name", "Okay");new Document("name", "Good");new Document("name", "Very good");

        genre.insertMany(genres);
        grade.insertMany(grades);

        try {
            album.insertOne(
                new Document("name","Thriller")
                        .append("ReleaseDate",format.parse("2002/01/01"))
                        .append("genre","rock")
                        .append("grade","very good"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        artist.insertOne(new Document("name", "Michael Jackson").append("Nationality", "USA"));

    }

    public void dropDatabase(){

        db.getCollection("album").drop();
        db.getCollection("artist").drop();
        db.getCollection("genre").drop();
        db.getCollection("grade").drop();
        db.getCollection("review").drop();
        db.getCollection("user").drop();
    }


}
