package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import static java.util.Arrays.asList;

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
                    new Document("name","Jack Black")
                            .append("ReleaseDate",format.parse("2006/01/01"))
                            .append("genre","rock")
                            .append("grade","very good")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.createCollection("artist");
        db.createCollection("genre");
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
