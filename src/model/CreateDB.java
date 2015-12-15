package model;

import com.mongodb.BulkWriteUpsert;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.mongodb.client.model.Filters;


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
    private MongoCollection albumArtist;
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
        albumArtist = db.getCollection("album_artist");

        UpdateOptions u = new UpdateOptions().upsert(true);



        genres.add(new Document("name", "Rock"));genres.add(new Document("name", "Pop"));genres.add(new Document("name", "Dance"));genres.add(new Document("name", "Reggae"));genres.add(new Document("name", "RnB"));
        grades.add(new Document("name", "Bad")); grades.add(new Document("name", "Meh")); grades.add(new Document("name", "Okay"));grades.add(new Document("name", "Good"));grades.add(new Document("name", "Very good"));

        genre.insertMany(genres);
        grade.insertMany(grades);
        artist.insertOne(new Document("name", "Michael Jackson").append("Nationality", "USA"));



        MongoCursor<Document> cursor = artist.find(new Document("name", "Michael Jackson")).iterator();
        Document docId = null;
        while(cursor.hasNext()){
             docId = cursor.next();
        }
        cursor = genre.find(new Document("name", "Rock")).iterator();
        Document docGenre = null;
        while(cursor.hasNext()){
            docGenre = cursor.next();
        }

        cursor = grade.find(new Document("name", "Very good")).iterator();
        Document docGrade = null;
        while(cursor.hasNext()){
            docGrade = cursor.next();
        }



        try {
            album.insertOne(
                new Document("name","Thriller").append("artist", docId.get("_id"))
                        .append("ReleaseDate",format.parse("2002/01/01"))
                        .append("genre",docGenre.getObjectId("_id"))
                        .append("grade",docGrade.getObjectId("_id")));
        } catch (ParseException e) {
            e.printStackTrace();
        }


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
