package model;

import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * This class represent a review.
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class Review implements Serializable {

    private ObjectId reviewID;
    private String text;
    private Album album;
    private TUser user;

    /**
     * Creates a Review with a unique id.
     *
     * @param reviewID
     */
    public Review(ObjectId reviewID) {
        this.reviewID = reviewID;
    }

    /**
     * Returns the unique id of the Review.
     *
     * @return the unique id of the Review.
     */
    public ObjectId getReviewID() {
        return reviewID;
    }

    /**
     * Sets the unique id of the review.
     *
     * @param reviewID
     */
    public void setReviewID(ObjectId reviewID) {
        this.reviewID = reviewID;
    }

    /**
     * Get the text in the Review.
     *
     * @return a string with the text of the Review.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the review.
     *
     * @param text, the text to be set for the review.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the album where the review is set to.
     *
     * @return
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Sets the album to review.
     *
     * @param album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Get the user who created the review.
     *
     * @return the user who created the review.
     */
    public TUser getUser() {
        return user;
    }

    /**
     * Set the user who created the review.
     *
     * @param user, the user who created the review.
     */
    public void setUser(TUser user) {
        this.user = user;
    }

    /**
     * Returns a String representation of the review.
     *
     * @return a String representation of the review.
     */
    @Override
    public String toString() {
        return "model.Review[ reviewID=" + reviewID + " ] on" + album + ": [" + text + "] Created by: " + user;
    }

}
