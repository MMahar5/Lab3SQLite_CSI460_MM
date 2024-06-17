package com.example.lab3_sqlite_csi460_mm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    //Database Variables
    private static final String DB_NAME = "carsDb.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myCars";
    private static final String ID_COL = "id";
    private static final String MAKE_COL = "make";
    private static final String MODEL_COL = "model";
    private static final String YEAR_COL = "year";
    private static final String COLOR_COL = "color";
    private static final String MILES_COL = "miles";
    private static final String PRICE_COL = "price";
    private static final String IMAGE_PATH_COL = "image";

    //Database constructor
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //Method to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to set column names and data types
        String carsDbQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MAKE_COL + " TEXT,"
                + MODEL_COL + " TEXT,"
                + YEAR_COL + " TEXT,"
                + COLOR_COL + " TEXT,"
                + MILES_COL + " INTEGER,"
                + PRICE_COL + " DOUBLE,"
                + IMAGE_PATH_COL + " BLOB)";

        db.execSQL(carsDbQuery);
    }


    //Method to add new car listing to our carsDb.
    public void addCarListing(String carMake, String carModel, String carYear, String carColor, Integer carMiles, Double carPrice, Bitmap carImg) {

        //Sets DB and Content values
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        //Convert image bitmap to byte array
        ByteArrayOutputStream bitImg = new ByteArrayOutputStream();
        carImg.compress(Bitmap.CompressFormat.PNG, 100, bitImg);
        byte[] byteArr = bitImg.toByteArray();

        //Pass key value pairs of car data
        v.put(MAKE_COL, carMake);
        v.put(MODEL_COL, carModel);
        v.put(YEAR_COL, carYear);
        v.put(COLOR_COL, carColor);
        v.put(MILES_COL, carMiles);
        v.put(PRICE_COL, carPrice);
        v.put(IMAGE_PATH_COL, byteArr);

        //Pass content value to the table and close carsDb
        db.insert(TABLE_NAME, null, v);
        db.close();
    }


    //Method used to check if table exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //Method for reading the car listing data
    public ArrayList<CarListing> readCars(){

        //Creates database and query to read the data from the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor carsCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        //Creates array list for storing the car data
        ArrayList<CarListing> carsArr = new ArrayList<>();

        //Puts cursor at the start position
        if(carsCursor.moveToFirst()){
            //do while loop to add data to the array list
            do{
                //Converts the byte array back to bitmap for our image
                @SuppressLint("Range") byte[] byteArr = carsCursor.getBlob(carsCursor.getColumnIndex(IMAGE_PATH_COL));
                Bitmap carImg = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);


                carsArr.add(new CarListing(carsCursor.getInt(0),carsCursor.getString(1), carsCursor.getString(2),
                        carsCursor.getString(3), carsCursor.getString(4),
                        carsCursor.getInt(5), carsCursor.getDouble(6), carImg));

            }while(carsCursor.moveToNext());
        }
        carsCursor.close();

        //Returns the list of all car listing data
        return carsArr;
    }




    //Method to update a car listing
    public void updateCar(Integer carId, String make, String model, String year, String color, Integer miles, Double price, Bitmap img)
    {

        //Gets our sqlite database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Need to convert our bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imgByteArr = stream.toByteArray();

        //Passes our updated content values
        values.put(MAKE_COL, make);
        values.put(MODEL_COL, model);
        values.put(YEAR_COL, year);
        values.put(COLOR_COL, color);
        values.put(MILES_COL, miles);
        values.put(PRICE_COL, price);
        values.put(IMAGE_PATH_COL, imgByteArr);

        //Updates our database table with the content values where the car is equal to the given unique id
        db.update(TABLE_NAME, values, ID_COL + "=?", new String[]{String.valueOf(carId)});
        db.close();
    }


    //Method for deleting a car listing
    public void deleteCar(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Deletes the car from the db table where the car is equal to the given unique id
        db.delete(TABLE_NAME, ID_COL+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
