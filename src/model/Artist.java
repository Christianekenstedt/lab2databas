package model;

import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Artist contains methods to access the result from the database
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class Artist implements Serializable {

    private ObjectId artistID;
    private String name;
    private String nationality;

    /**
     * empty constructor
     */
    public Artist() {
    }

    /**
     * Recives integer with artistID
     * @param artistID
     */
    public Artist(ObjectId artistID) {
        this.artistID = artistID;
    }

    /**
     * recives integer with artistID, string with name and string with nationality
     * @param artistID
     * @param name
     * @param nationality
     */
    public Artist(ObjectId artistID, String name, String nationality) {
        this.artistID = artistID;
        this.name = name;
        this.nationality = nationality;
    }

    /**
     * returns the artist's ID in integer
     * @return
     */
    public ObjectId getArtistID() {
        return artistID;
    }

    /**
     * set's the artist ID, recives integer
     * @param artistID
     */
    public void setArtistID(ObjectId artistID) {
        this.artistID = artistID;
    }

    /**
     * get's the artist's name in string
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set's the artist's name with string
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get's the artist's nationality
     * @return
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * set's the artist's nationality
     * @param nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    /**
     * returns string with information of what it is
     * @return
     */
    @Override
    public String toString() {
        return "model.Artist[ artistID=" + artistID + " ]";
    }
    
}
