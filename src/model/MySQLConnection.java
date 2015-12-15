package model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Platform;

/**
 * ConnectionToDB contains the methods used to connect to the database and methods to make SQL querys
 * @author Christian Ekenstedt & Gustaf Holmström
 */


public class MySQLConnection implements DBCommunication{
    private Connection con;
    private String username;
    private String password;
    private String host;
    private String database;
       
    /**
     * 
     * @param host, the address for the server
     * @param database, which database to connect to
     * @param username, name of the user(has to be defined in the database)
     * @param password, password to the selected user
     */
    public MySQLConnection(String host, String database, String username, String password){
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        con = null;
    }

    /**
     *
     * @return  the current username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return  the adress of the server
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @return  which database is selected
     */
    public String getDatabase() {
        return database;
    }
    
    /**
     *
     * @return  boolean wherer the connection is successful, true if success
     * @throws SQLException
     */
    public boolean connectToDatabase(){
        String server = "jdbc:mysql://" + host + ":3306/" + database +
			"?UseClientEnc=UTF8";
        try {	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
                        
			con = DriverManager.getConnection(server, username, password);
			System.out.println("Connected!");
                        return true;
        }
		catch(Exception e) {
                    // Here we should throw the exception to the calling method and handle it there.
                     return false;
		}
        finally {
                
        	//Maybe something important here.
        }
    }
    
    /**
     * Closes the connection to the server
     * @throws SQLException 
     */
    @Override
    public void closeConnection(){
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Connection closed.");
            Platform.exit();
        }
    }
    
    /**
     * Add album to database
     * @param title, of the album you want to add
     * @param artist, of the album you want to add(will check if artist is already in the database)
     * @param nationality, nationality of the artist
     * @param date, date the album was released
     * @param genre, of the album
     * @param grade, of the album
     * @throws SQLException
     */
    @Override
    public void addAlbum(String title, String artist, String nationality, Date date, Genre genre, Grade grade){
        try{
            PreparedStatement addAlbumPrepSt = con.prepareStatement("INSERT INTO Album(name, releaseDate, genre, grade) VALUES(?, ?, ?, ?)");
            PreparedStatement addArtistPrepSt = con.prepareStatement("INSERT INTO Artist(name, nationality) VALUES(?, ?)");
            PreparedStatement checkIfArtistExists = con.prepareStatement("SELECT artistID from Artist where name = ? and nationality = ?");
            PreparedStatement getAlbumID = con.prepareStatement("SELECT albumID from Album where name = ?");
            PreparedStatement addArtistToAlbumSt = con.prepareStatement("INSERT INTO Album_Artist(album,artist) VALUES (?,?)");

            int artistID;
            int albumID;
            con.setAutoCommit(false);
            addAlbumPrepSt.clearParameters();
            addArtistPrepSt.clearParameters();
            checkIfArtistExists.clearParameters();
            getAlbumID.clearParameters();
            addArtistToAlbumSt.clearParameters();
            
            checkIfArtistExists.setString(1, artist);
            checkIfArtistExists.setString(2, nationality);
            ResultSet rs = checkIfArtistExists.executeQuery();
            
            addAlbumPrepSt.setString(1, title); addAlbumPrepSt.setDate(2, date); addAlbumPrepSt.setInt(3, genre.getGenreID()); addAlbumPrepSt.setInt(4, grade.getGradeID());
            addAlbumPrepSt.execute();
            
            getAlbumID.setString(1, title);
            ResultSet rsAlbumID = getAlbumID.executeQuery();
            if(rsAlbumID.next()){
              albumID = rsAlbumID.getInt(1);
            if ( !rs.next() ){
                addArtistPrepSt.setString(1, artist); addArtistPrepSt.setString(2, nationality);
                addArtistPrepSt.execute();
                rs = checkIfArtistExists.executeQuery();
                if(rs.next()){
                   artistID = rs.getInt(1); 
                }else{
                    artistID = -1;
                }
            }else{
                artistID = rs.getInt(1);
            }
            
            addArtistToAlbumSt.setInt(1,albumID);
            addArtistToAlbumSt.setInt(2,artistID);
                
            addArtistToAlbumSt.execute();
            
            }
            
            
            con.commit();
            
        }catch(SQLException e){
            if (con != null) try {
                con.rollback();
                System.err.print("Transaction rollback");
                throw e;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }finally{

            /*addAlbumPrepSt.close();
            addArtistPrepSt
            checkIfArtistExists
            getAlbumID
            addArtistToAlbumSt
            */

            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }
    
    /**
     * search for album by artist
     * @param name, name of the arist
     * @return, will return arraylist of the artist's album
     * @throws SQLException
     */
    @Override
    public ArrayList<Object> getAlbumsByArtist(String name){
        ResultSet rs = null;
        try{
            PreparedStatement albumByArtist = con.prepareStatement("SELECT Album.* FROM Album, Album_Artist, Artist WHERE Album.albumID = "
                    + "Album_Artist.album and Album_Artist.artist = Artist.artistID AND Artist.name = ?");
            albumByArtist.clearParameters();
            albumByArtist.setString(1,name);
            
            rs = albumByArtist.executeQuery();
            ArrayList<Object> list = new ArrayList<>();
            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2),rs.getDate(3));
                list.add(album);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * get album by title in a arraylist
     * @param name, of the album
     * @return, will return artist's name from the database
     * @throws SQLException
     */
    @Override
    public ArrayList<Object> getAlbumByTitle(String name){
        ResultSet rs = null;

        try{
            PreparedStatement albumByName = con.prepareStatement("SELECT * FROM Album WHERE name LIKE ?");
            albumByName.clearParameters();
            albumByName.setString(1,name + "%");
            
            rs = albumByName.executeQuery();
            ArrayList<Object> list = new ArrayList<>();
            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2),rs.getDate(3));
                list.add(album);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * get's arraylist of all available genras
     * @return, get's all the current genras available to use from the database
     * @throws SQLException
     */
    @Override
    public ArrayList<Genre> getGenre(){
        ResultSet rs = null;

        try{
            PreparedStatement gradesPreStatement = con.prepareStatement("SELECT * FROM Genre");
            gradesPreStatement.clearParameters();
            
            rs = gradesPreStatement.executeQuery();
            ArrayList<Genre> list = new ArrayList<>();
            while(rs.next()){
                Genre genre = new Genre(rs.getInt(1), rs.getString(2));
                list.add(genre);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * get's arraylsit with available grades
     * @return, names of the grades.
     * @throws SQLException
     */
    @Override
    public ArrayList<Grade> getGrades(){
        ResultSet rs = null;
        try{
            PreparedStatement gradesPreStatement = con.prepareStatement("SELECT * FROM Grade");
            gradesPreStatement.clearParameters();
            
            rs = gradesPreStatement.executeQuery();
            ArrayList<Grade> list = new ArrayList<>();
            while(rs.next()){
                Grade grade = new Grade(rs.getInt(1), rs.getString(2));
                list.add(grade);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * returns string with teh connected users name
     * @return get's the connected users name
     */
    public String getConnectedUser(){
        return username;
    }

    /**
     * get's all albums with selected genre
     * @param genre, chosen genra
     * @return, arraylist of all album with chosen genra
     * @throws SQLException
     */
    @Override
    public ArrayList<Object> getAlbumByGenre(int genre){
        ResultSet rs = null;
        try{
            PreparedStatement genreByName = con.prepareStatement("SELECT * FROM Album WHERE genre = ?");
            genreByName.clearParameters();
            genreByName.setInt(1,genre);
            
            rs = genreByName.executeQuery();
            ArrayList<Object> list = new ArrayList<>();
            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2),rs.getDate(3));
                list.add(album);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get's all album by selected grade
     * @param grade, chosen grade
     * @return, arraylist of all album with the chosen grade
     * @throws SQLException
     */
    @Override
    public ArrayList<Object> getAlbumByGrade(int grade){
        ResultSet rs = null;
        try{
            PreparedStatement gradeByName = con.prepareStatement("SELECT * FROM Album WHERE grade = ?");
            gradeByName.clearParameters();
            gradeByName.setInt(1,grade);
            
            rs = gradeByName.executeQuery();
            ArrayList<Object> list = new ArrayList<>();
            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2),rs.getDate(3));
                list.add(album);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
