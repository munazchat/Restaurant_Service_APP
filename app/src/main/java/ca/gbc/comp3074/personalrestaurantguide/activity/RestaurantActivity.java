package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    public static final String RESTAURANT_KEY = "restaurant";

    private RatingBar ratingBar;
    private TextView mTVAddress;
    private TextView mTVDescription;
    private TextView mTVName;
    private TextView mTVType;
    private Restaurant mRestaurant;
    private DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mDB = new DatabaseHelper(this);

        Intent intent = getIntent();
        mRestaurant = intent.getParcelableExtra(RESTAURANT_KEY);

        mTVAddress = findViewById(R.id.Address);
        mTVDescription=findViewById(R.id.description);
        mTVName=findViewById(R.id.name);
        mTVType=findViewById(R.id.type);

        Button btn_s=(Button)findViewById(R.id.share);
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "body here";
                String shareSub = "subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share using"));
            }
        });
        ratingBar = findViewById(R.id.ratingBar);
        Button btn = findViewById(R.id.btn_rating);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                if (mRestaurant.getRating() != rating) {
                    mRestaurant.setRating(rating);
                    mDB.updateRestaurant(mRestaurant);
                    Toast.makeText(RestaurantActivity.this,"Your rating "+rating+" stars",Toast.LENGTH_LONG).show();
                }
            }
        });

        if (mRestaurant != null) {
            mTVAddress.setText(mRestaurant.getAddress());
            mTVDescription.setText(mRestaurant.getDescription());
            mTVName.setText(mRestaurant.getName());
            mTVType.setText(mRestaurant.getType());

            ratingBar.setRating(mRestaurant.getRating());
        }
    }
}