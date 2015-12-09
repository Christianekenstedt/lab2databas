package model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Genre contains methods to access the result from the database
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class Genre implements Serializable {

    private Integer genreID;
    private String name;
    private Collection<Album> albumCollection;

    /**
     * constructor reciving the genre ID
     * @param genreID
     */
    public Genre(Integer genreID) {
        this.genreID = genreID;
    }

    /**
     * constructor reciving genre ID and string name
     * @param genreID
     * @param name
     */
    public Genre(Integer genreID, String name) {
        this.genreID = genreID;
        this.name = name;
    }

    /**
     * get's the genreID in integer
     * @return
     */
    public Integer getGenreID() {
        return genreID;
    }

    /**
     * set's the genreID with integer
     * @param genreID 
     */
    public void setGenreID(Integer genreID) {
        this.genreID = genreID;
    }

    /**
     * get's the genre's name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set's the genre name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns string with genre name
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }
    
}
