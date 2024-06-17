package com.example.lab3_sqlite_csi460_mm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class UpdateCar extends AppCompatActivity {

    //Variables
    private EditText carMake, carModel, carYear, carColor, carMiles, carPrice;
    private ImageView carImg;
    private Button updateBtn, changeImgBtn, deleteBtn;
    private DBHandler dbHandler;
    String make, model, year, color;
    Integer id, miles, carPos;
    Double price;
    byte[] imgByteArr;
    Bitmap imgBit;

    //Request code for picking an image
    private static final int PICK_IMAGE_REQUEST = 1002;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);

        //Initializes the variables
        carMake = findViewById(R.id.carMake);
        carModel = findViewById(R.id.carModel);
        carYear = findViewById(R.id.carYear);
        carColor = findViewById(R.id.carColor);
        carMiles = findViewById(R.id.carMiles);
        carPrice = findViewById(R.id.carPrice);
        carImg = findViewById(R.id.carImg);

        //Initialize buttons
        updateBtn = findViewById(R.id.updateBtn);
        changeImgBtn = findViewById(R.id.changeImgBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        dbHandler = new DBHandler(UpdateCar.this);

        //Gets the car data passed from intent
        id = getIntent().getIntExtra("id", -1);
        make = getIntent().getStringExtra("make");
        model = getIntent().getStringExtra("model");
        year = getIntent().getStringExtra("year");
        color = getIntent().getStringExtra("color");
        miles = getIntent().getIntExtra("miles", 0);
        price = getIntent().getDoubleExtra("price", 0);
        imgByteArr = getIntent().getByteArrayExtra("image");

        //Convert the image byte array to a bitmap
        if(imgByteArr != null){
            imgBit = BitmapFactory.decodeByteArray(imgByteArr, 0, imgByteArr.length);
        }

        //Set the values of our layout components
        carMake.setText(make);
        carModel.setText(model);
        carYear.setText(year);
        carColor.setText(color);
        carMiles.setText(miles.toString());
        carPrice.setText(price.toString());
        if(imgBit != null){
            carImg.setImageBitmap(imgBit);
        }else{
            carImg.setImageResource(R.drawable.car_placeholder);
        }

        //Button to select/change the image
        changeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uses intent to let the user go and select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        //Update button gets and updates the old values with the new ones
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the car listing data entered by the user
                make = carMake.getText().toString();
                model = carModel.getText().toString();
                year = carYear.getText().toString();
                color = carColor.getText().toString();
                String milesString = carMiles.getText().toString();
                String priceString = carPrice.getText().toString();

                //Validates whether all text areas have a value
                if(make.isEmpty() || model.isEmpty() || year.isEmpty() || color.isEmpty() || milesString.isEmpty() || priceString.isEmpty()){
                    Toast.makeText(UpdateCar.this, "One or More Input Fields Are Missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses the miles to make sure its a valid number
                try {
                    miles = Integer.parseInt(milesString);
                }catch(NumberFormatException e){
                    Toast.makeText(UpdateCar.this, "Please Enter a Valid Number for Miles", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Parses and format the price to 2 decimal places
                try {
                    DecimalFormat df = new DecimalFormat("#.00");
                    price = Double.parseDouble(df.format(Double.parseDouble(priceString)));
                }catch(NumberFormatException e){
                    Toast.makeText(UpdateCar.this, "Please Enter a Valid Price Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Gets the bitmap from the image view
                imgBit = ((BitmapDrawable) carImg.getDrawable()).getBitmap();

                //Calls update method and passes our input values
                dbHandler.updateCar(id, make, model, year, color, miles, price, imgBit);
                //Message to let user know that the car has been updated
                Toast.makeText(UpdateCar.this, "Car Listing Has Been Updated", Toast.LENGTH_SHORT).show();

                //Go back to MainActivity
                Intent intent = new Intent(UpdateCar.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Delete button to erase car listing from database
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Alert dialog box used to confirm deletion
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(UpdateCar.this);
                adBuilder.setTitle("Delete Vehicle");
                adBuilder.setMessage("Are You Sure You Want To Delete This Listing?");

                adBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                adBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Calls delete method from DB handler
                        dbHandler.deleteCar(id);
                        //Displays message to the user
                        Toast.makeText(UpdateCar.this, "Car Listing Has Been Deleted", Toast.LENGTH_SHORT).show();



                        //Return to see all cars activity
                        Intent intent = new Intent(UpdateCar.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = adBuilder.create();
                dialog.show();
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