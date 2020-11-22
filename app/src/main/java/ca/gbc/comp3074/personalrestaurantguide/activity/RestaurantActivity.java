package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import ca.gbc.comp3074.personalrestaurantguide.R;

public class RestaurantActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

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
                Toast.makeText(RestaurantActivity.this,"Your rating "+rating+" stars",Toast.LENGTH_LONG).show();
            }
        });
    }
}