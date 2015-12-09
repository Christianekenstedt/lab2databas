package model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public interface DBCommunication {
    
    /**
     * Add's album to the database
     * @param title
     * @param artist
     * @param nationality
     * @param date
     * @param genre
     * @param grade
     * @throws SQLException
     */
    public void addAlbum(String title, String artist, String nationality, Date date, Genre genre, Grade grade)throws SQLException;

    /**
     * 
     * @param name
     * @return
     * @throws SQLException
     */
    public ArrayList<Object> getAlbumsByArtist(String name)throws SQLException;

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public ArrayList<Object> getAlbumByTitle(String name)throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Genre> getGenre()throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Grade> getGrades()throws SQLException;

    /**
     *
     * @param genre
     * @return
     * @throws SQLException
     */
    public ArrayList<Object> getAlbumByGenre(int genre) throws SQLException;

    /**
     *
     * @param grade
     * @return
     * @throws SQLException
     */
    public ArrayList<Object> getAlbumByGrade(int grade) throws SQLException;
}   
