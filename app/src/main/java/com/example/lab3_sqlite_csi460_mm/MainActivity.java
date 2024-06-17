package com.example.lab3_sqlite_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Declared Variables for our layout components and DB handler
    EditText carMake, carModel, carYear, carColor, carMiles, carPrice;
    ImageView carImg;
    Button addImgBtn, submitBtn, seeAllBtn;
    DBHandler dbHandler;

    //Request code for picking an image
    private static final int PICK_IMAGE_REQUEST = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Variables
        carMake = findViewById(R.id.carMake);
        carModel = findViewById(R.id.carModel);
        carYear = findViewById(R.id.carYear);
        carColor = findViewById(R.id.carColor);
        carMiles = findViewById(R.id.carMiles);
        carPrice = findViewById(R.id.carPrice);
        carImg = findViewById(R.id.carImg);

        addImgBtn = findViewById(R.id.imgBtn);
        submitBtn = findViewById(R.id.submitBtn);
        seeAllBtn = findViewById(R.id.seeAllBtn);

        dbHandler = new DBHandler(MainActivity.this);


        //Button for selecting an image
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to let the user go and select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the car listing data entered by the user
                String make = carMake.getText().toString();
                String model = carModel.getText().toString();
                String year = carYear.getText().toString();
                String color = carColor.getText().toString();
                String milesString = carMiles.getText().toString();
                String priceString = carPrice.getText().toString();

                //Validates whether all text areas have a value
                if(make.isEmpty() || model.isEmpty() || year.isEmpty() || color.isEmpty() || milesString.isEmpty() || priceString.isEmpty() ){
                    Toast.makeText(MainActivity.this, "One or More Input Fields Are Missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses the miles to make sure its a valid number
                int miles;
                try {
                    miles = Integer.parseInt(milesString);
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Number for Miles", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses and format the price to 2 decimal places
                double price;
                try {
                    DecimalFormat df = new DecimalFormat("#.00");
                    price = Double.parseDouble(df.format(Double.parseDouble(priceString)));
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Price Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Gets the bitmap from the image view
                Bitmap bitImg = ((BitmapDrawable) carImg.getDrawable()).getBitmap();

                //Calls method from dbHandler to add car listing to the SQLite database
                dbHandler.addCarListing(make, model, year, color, miles, price, bitImg);

                //Displays message that data has been saved to the db
                Toast.makeText(MainActivity.this, "Car Listing Has Been Added.", Toast.LENGTH_SHORT).show();

                //Clears the text fields and sets imageview back to placeholder image
                carMake.setText("");
                carModel.setText("");
                carYear.setText("");
                carColor.setText("");
                carMiles.setText("");
                carPrice.setText("");
                carImg.setImageResource(R.drawable.car_placeholder);
            }
        });


        seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to go to the activity to see/read the list of car listings.
                Intent intent = new Intent(MainActivity.this, SeeAllCars.class);
                startActivity(intent);
            }
        });

    }

    //Gets and displays the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imgUri = data.getData();

            //Displays the img in the ImageView
            carImg.setImageURI(imgUri);
        }
    }

}