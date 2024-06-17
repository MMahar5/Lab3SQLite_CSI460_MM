package com.example.lab3_sqlite_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SeeAllCars extends AppCompatActivity {

    //Declares Variables for our recycler view, db handler and a new array list
    private ArrayList<CarListing> carsArr;
    private RclAdapter rclAdapter;
    private RecyclerView rclView;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_cars);

        //Initializes variables
        carsArr = new ArrayList<>();
        dbHandler = new DBHandler(SeeAllCars.this);

        //Reads the car data list from DbHandler and pass it to the rcl adapter class
        carsArr = dbHandler.readCars();
        rclAdapter = new RclAdapter(SeeAllCars.this, carsArr);
        rclView = findViewById(R.id.rclView);

        //Sets the layout manager and the rcl adapter for the recycler view
        LinearLayoutManager linLayout = new LinearLayoutManager(SeeAllCars.this, RecyclerView.VERTICAL, false);
        rclView.setLayoutManager(linLayout);
        rclView.setAdapter(rclAdapter);
    }
}