package com.example.lab3_sqlite_csi460_mm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class RclAdapter extends RecyclerView.Adapter<RclAdapter.ListViewHolder> {

    //Variables for the context and Array List for car listing data
    private Context context;
    private ArrayList<CarListing> carsArr;

    //Constructor
    public RclAdapter(Context context, ArrayList<CarListing> carsArr) {
        this.context = context;
        this.carsArr = carsArr;
    }


    @NonNull
    @Override
    public RclAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_listing_item, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RclAdapter.ListViewHolder h, int pos) {

        //Set the text views
        CarListing car = carsArr.get(pos);
        h.make.setText(car.getMake());
        h.model.setText(car.getModel());
        h.year.setText(car.getYear());
        h.color.setText("Color: "+car.getColor());

        //Format and set the text views for miles and price
        NumberFormat numF = NumberFormat.getNumberInstance();
        String milesF = numF.format(car.getMiles());
        milesF = milesF + " mi";
        h.miles.setText(milesF);

        DecimalFormat decF = new DecimalFormat("#,##0");
        String priceF = decF.format(car.getPrice());
        priceF = "$"+priceF;
        h.price.setText(priceF);

        //Set the image or a placeholder if its null
        Bitmap bitImg = car.getImage();
        if(bitImg != null){
            h.img.setImageBitmap(bitImg);
        }else{
            h.img.setImageResource(R.drawable.car_placeholder);
        }

        //Added for the car listing item, so that when it is clicked, it goes to the update activity
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateCar.class);

                //Passes the current values of the car with the intent
                intent.putExtra("id", car.getId());
                intent.putExtra("make", car.getMake());
                intent.putExtra("model",car.getModel());
                intent.putExtra("year",car.getYear());
                intent.putExtra("color",car.getColor());
                intent.putExtra("miles",car.getMiles());
                intent.putExtra("price",car.getPrice());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                car.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imgByteArr = stream.toByteArray();

                intent.putExtra("image",imgByteArr);

                //Starts the update car activity
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carsArr.size();
    }

    //Viewholder class
    public class ListViewHolder extends RecyclerView.ViewHolder {

        //Variables for all of our layout components
        private TextView make, model, year, color, miles, price;
        private ImageView img;

        public ListViewHolder(@NonNull View item){
            super(item);
            //Initalize our layout components
            make = item.findViewById(R.id.makeTxt);
            model = item.findViewById(R.id.modelTxt);
            year = item.findViewById(R.id.yearTxt);
            color = item.findViewById(R.id.colorTxt);
            miles = item.findViewById(R.id.milesTxt);
            price = item.findViewById(R.id.priceTxt);
            img = item.findViewById(R.id.imgCar);
        }
    }
}


