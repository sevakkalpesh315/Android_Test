package com.cameron.verticalphoto.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cameron.verticalphoto.model.entities.Picture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handle all the operations concerning the SQLite database table of pictures.
 *
 * @author Cameron
 * @version 1.0
 */
public class PicturesModel extends SQLiteOpenHelper{
    //General database constants
    private static String DATABASE_NAME="pictures.db";
    private static int VERSION = 1;
    //Tables constants
    private static String TABLE_NAME = "pictures";
    //Fields constants
    private static String ID_FIELD = "_id";
    private static String IMAGE_ID_FIELD = "imageId";
    private static String TITLE_FIELD = "title";
    private static String USER_ID_FIELD = "userId";
    private static String USER_NAME_FIELD = "userName";

    public PicturesModel(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + TABLE_NAME + "(" +
                ID_FIELD + " int, " +
                IMAGE_ID_FIELD + " int, " +
                TITLE_FIELD + " Varchar(100), " +
                USER_ID_FIELD + " int, " +
                USER_NAME_FIELD + " Varchar(20), " +
                "Primary Key(" + ID_FIELD + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + TABLE_NAME, null);
        onCreate(db);
    }

    /**
     * <p>This method prepares the values to be inserted or updated. A picture object is used to
     * get these values and a boolean is used to determine if the id is necessary or not.</p>
     * @param picture is the picture object to prepare the content values.
     * @param addId if true, the id is added to the values.
     * @return a content values object to be used in an insert or an update statement.
     */
    private ContentValues prepareValues(Picture picture, boolean addId)
    {
        ContentValues values = new ContentValues();
        if (addId)
            values.put(ID_FIELD, picture.getID());
        values.put(IMAGE_ID_FIELD, picture.getImageID());
        values.put(TITLE_FIELD, picture.getTitle());
        values.put(USER_ID_FIELD, picture.getUserID());
        values.put(USER_NAME_FIELD, picture.getUserName());
        return values;
    }

    /**
     * <p>Converts a cursor into a list of pictures. The method is used to process the results of
     * the performed queries.</p>
     *
     * @param cursor cursor containing the results of the query.
     * @return an array list of pictures.
     */
    private ArrayList<Picture> processResult(Cursor cursor)
    {
        ArrayList<Picture> pictures = new ArrayList<>();
        boolean hasRecord = cursor.moveToFirst();
        while (hasRecord)
        {
            Picture picture = new Picture();
            picture.setID(cursor.getInt(cursor.getColumnIndex(ID_FIELD)));
            picture.setImageID(cursor.getInt(cursor.getColumnIndex(IMAGE_ID_FIELD)));
            picture.setTitle(cursor.getString(cursor.getColumnIndex(TITLE_FIELD)));
            picture.setUserID(cursor.getInt(cursor.getColumnIndex(USER_ID_FIELD)));
            picture.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME_FIELD)));
            pictures.add(picture);
            hasRecord = cursor.moveToNext();
        }
        cursor.close();
        return pictures;
    }

    /**
     * <p>Inserts a picture.</p>
     * @param picture is the picture to insert.
     */
    public void insert(Picture picture)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, prepareValues(picture, true));
        db.close();
    }

    /**
     * <p>Updates a picture.</p>
     * @param picture is the picture to be updated.
     */
    public void update(Picture picture)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, prepareValues(picture, false), ID_FIELD + " = ?", new String[]{Integer.toString(picture.getID())});
        db.close();
    }

    /**
     * <p>Deletes a picture.</p>
     * @param picture is the picture to be deleted.
     */
    public void delete(Picture picture)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_FIELD + " = ?", new String[]{Integer.toString(picture.getID())});
        db.close();
    }

    /**
     * <p>Deletes all the pictures in the database.</p>
     */
    public void delete()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    /**
     * <p>Finds all the pictures registered in the database.</p>
     * @return an array list of pictures.
     */
    public ArrayList<Picture> findAll()
    {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Picture> pictures = processResult(db.query(TABLE_NAME, null, null, null, null, null, null));
        db.close();
        return pictures;
    }

    /**
     * <p>Returns the picture that corresponds to the given identifier.</p>
     * @param id is the identifier of the picture.
     * @return a picture entity object containing the data of the found image.
     */
    public Picture findById(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Picture> pictures = processResult(db.query(TABLE_NAME, null, ID_FIELD + " = ?", new String[]{Integer.toString(id)}, null, null, null));
        db.close();
        Picture picture = null;
        if (pictures.size() == 1)
            picture = pictures.get(0);
        return picture;
    }

    /**
     * Generates a report saying how many images each user has posted.
     * @return a hashmap whose key is the user name and the value is the number of pictures.
     */
    public HashMap<String,Integer> generateReport()
    {
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, Integer> result = new HashMap<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{USER_NAME_FIELD, "Count(*) As posts"}, null, null, USER_NAME_FIELD, null, null);
        boolean hasRecord = cursor.moveToFirst();
        while(hasRecord)
        {
            result.put(cursor.getString(cursor.getColumnIndex(USER_NAME_FIELD)), cursor.getInt(cursor.getColumnIndex("posts")));
            hasRecord = cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return result;
    }
}
