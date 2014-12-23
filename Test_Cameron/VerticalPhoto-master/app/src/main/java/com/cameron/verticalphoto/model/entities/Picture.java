package com.cameron.verticalphoto.model.entities;

import java.io.Serializable;

/**
 * <p>This class is the picture entity object. This object is going to be the one that will store
 * all the information of every individual picture that is on the database or received from the
 * RESTful service.</p>
 *
 * <p>For every picture, an identifier is saved in order to get the picture from the webservice.
 * There's also an image identifier and title. A user identifier and a username identify the user.
 * </p>
 *
 * <p>The class implements serializable in order to deal with screen rotation. </p>
 *
 * @author Pablo Sánchez Alonso
 * @version 1.0
 */
public class Picture implements Serializable {
    private int ID;
    private int ImageID;
    private String Title;
    private int UserID;
    private String UserName;

    /**
     * <p></p>Returns the identifier of this record in the webservice. This field will be used to
     * download the picture from the webservice. </p>
     * @return an integer representing the record identifier in the webservice.
     */
    public int getID() {
        return ID;
    }

    /**
     * <p>Sets the record identifier in the webservice. This information is used to download the
     * picture from it. </p>
     * @param ID record identifier to set.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * <p>This method returns the picture identifier.</p>
     * @return an integer representing the picture identifier.
     */
    public int getImageID() {
        return ImageID;
    }

    /**
     * <p>Set the picture identifier. </p>
     * @param imageID picture identifier to set.
     */
    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    /**
     * <p>Returns the title of the picture.</p>
     * @return a string representing the title of the picture.
     */
    public String getTitle() {
        return Title;
    }

    /**
     * <p>Sets the title of the picture.</p>
     * @param title new title for the picture.
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * <p>Returns the user identifier. </p>
     * @return the user identifier as an integer.
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * <p>Sets the user identifier.</p>
     * @param userID the user identifier to set.
     */
    public void setUserID(int userID) {
        UserID = userID;
    }

    /**
     * <p>Returns the user name.</p>
     * @return the user name as a string.
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * <p>Sets the user name.</p>
     * @param userName a string containing the user name.
     */
    public void setUserName(String userName) {
        UserName = userName;
    }
}
