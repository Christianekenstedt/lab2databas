package model;

import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * TUser represents a User in a database.
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class TUser implements Serializable {

    private ObjectId userID;
    private String name;

    /**
     * Constructor creates a new TUser with a unique userID.
     *
     * @param userID, unique id for the user.
     */
    public TUser(ObjectId userID) {
        this.userID = userID;
    }

    /**
     * Returns the userID.
     *
     * @return th userID.
     */
    public ObjectId getUserID() {
        return userID;
    }

    /**
     * Set the userID for the user.
     *
     * @param userID, the userID to be set.
     */
    public void setUserID(ObjectId userID) {
        this.userID = userID;
    }

    /**
     * Returns the name of the TUser.
     *
     * @return as String with the name of the TUser.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the User.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a String representing the user.
     *
     * @return a String representing the user.
     */
    @Override
    public String toString() {
        return "model.TUser[ userID=" + userID + " ]";
    }

}
