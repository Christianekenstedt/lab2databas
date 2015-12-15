package model;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by chris on 2015-12-10.
 */
public class CreateDB {
    private MongoDatabase db;
    public CreateDB(MongoDatabase db) {
        //Fill the database, probably only one-time-use
        this.db = db;


    }

    public void createCollections() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");

        try {
            db.getCollection("album").insertOne(
                    new Document("name","Thriller")
                            .append("ReleaseDate",format.parse("2002/01/01"))
                            .append("genre","rock")
                            .append("grade","very good")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.getCollection("artist").insertOne(new Document("name", "Michael Jackson").append("Nationality", "USA"));

        db.getCollection("genre").insertOne(new Document("name", "Rock"));
        db.getCollection("grade").insertOne(new Document("name", "Bad"));
        db.getCollection("grade").insertOne(new Document("name", "Good"));
        db.getCollection("grade").insertOne(new Document("name", "Very good"));
        db.createCollection("grade");
        db.createCollection("review");
        db.createCollection("user");



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
