package model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Collection;

/**
 * Grade contains methods to access the result from the database
 * @author Christian Ekenstedt & Gustaf Holmström
 */
/**
 * Grade contains methods to access the result from the database
 */
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;
    private ObjectId gradeID;
    private String name;
    private Collection<Album> albumCollection;

    /**
     * constructor reciving integer with gradeID
     *
     * @param gradeID
     */
    public Grade(ObjectId gradeID) {
        this.gradeID = gradeID;
    }

    /**
     * constructor reciving gradeID and grade name
     *
     * @param gradeID
     * @param name
     */
    public Grade(ObjectId gradeID, String name) {
        this.gradeID = gradeID;
        this.name = name;
    }

    /**
     * get's the grade ID in integer
     *
     * @return
     */
    public ObjectId getGradeID() {
        return gradeID;
    }

    /**
     * set's the gradeID with integer
     *
     * @param gradeID
     */
    public void setGradeID(ObjectId gradeID) {
        this.gradeID = gradeID;
    }

    /**
     * get's the grade name, returns string
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set's the grade name with string
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns informaiton about grade with string
     *
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }

}
