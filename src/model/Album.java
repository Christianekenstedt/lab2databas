package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Album contains methods to access the result from the database
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class Album implements Serializable {

    private Integer albumID;
    private String name;
    private Date releaseDate;
    private Genre genre;
    private Grade grade;

    /**
     * Constructor recives a integer
     *
     * @param albumID, a unique integer
     */
    public Album(Integer albumID) {
        this.albumID = albumID;
    }

    /**
     * constructor reciving int albumID, String name and date of album release
     *
     * @param albumID
     * @param name
     * @param releaseDate
     */
    public Album(Integer albumID, String name, Date releaseDate) {
        this.albumID = albumID;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    /**
     * returns albumID in integer
     *
     * @return
     */
    public Integer getAlbumID() {
        return albumID;
    }

    /**
     * set's the albumID. Recives integer
     *
     * @param albumID
     */
    public void setAlbumID(Integer albumID) {
        this.albumID = albumID;
    }

    /**
     * returns String name of the album
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set's the albums name, recives string name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returna the data of the albums release
     *
     * @return
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * set's the album release date, recives a Date
     *
     * @param releaseDate
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * returns the albums genre
     *
     * @return
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * set's the albums genre
     *
     * @param genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * returns the albums grade
     *
     * @return
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * set's the albums grade
     *
     * @param grade
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * returns string with information of what it is
     *
     * @return
     */
    @Override
    public String toString() {
        return "model.Album[ albumID=" + albumID + " ]";
    }

}
