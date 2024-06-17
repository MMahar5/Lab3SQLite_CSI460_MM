package com.example.lab3_sqlite_csi460_mm;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class CarListing {
    //Declare variables for the data from our myCars table
    private Integer id;
    private String make;
    private String model;
    private String year;
    private String color;
    private Integer miles;
    private Double price;
    private Bitmap image;

    //Constructor
    public CarListing(Integer id, String make, String model, String year, String color, Integer miles, Double price, Bitmap image) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.miles = miles;
        this.price = price;
        this.image = image;
    }

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {return color;}

    public void setColor(String color) { this.color = color;}
    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    /* Saved temp code for trying to use parcelable to fix image size problem
    //Declare variables for the data from our myCars table
    private Integer id;
    private String make;
    private String model;
    private String year;
    private String color;
    private Integer miles;
    private Double price;
    private Bitmap image;

    //Constructor
    public CarListing(Integer id, String make, String model, String year, String color, Integer miles, Double price, Bitmap image) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.miles = miles;
        this.price = price;
        this.image = image;
    }

    protected CarListing(Parcel in) {
        id = in.readInt();
        make = in.readString();
        model = in.readString();
        year = in.readString();
        color = in.readString();
        if (in.readByte() == 0) {
            miles = null;
        } else {
            miles = in.readInt();
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<CarListing> CREATOR = new Creator<CarListing>() {
        @Override
        public CarListing createFromParcel(Parcel in) {
            return new CarListing(in);
        }

        @Override
        public CarListing[] newArray(int size) {
            return new CarListing[size];
        }
    };

    //Getters
    public Integer getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Integer getMiles() {
        return miles;
    }

    public Double getPrice() {
        return price;
    }

    public Bitmap getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(color);
        if (miles == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(miles);
        }
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeParcelable(image, flags);
    }

*/

}
