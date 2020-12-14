package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "RestaurantActivity";
    private GoogleMap mMap;
    private ImageButton mBtnFullscreen;
    private LatLng mRestaurantLocation;

    public static final String RESTAURANT_KEY = "restaurant";

    private RatingBar ratingBar;
    private TextView mTVAddress;
    private TextView mTVDescription;
    private TextView mTVName;
    private TextView mTVType;
    private TextView mTVTelephone;
    private Restaurant mRestaurant;
    private DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mBtnFullscreen = findViewById(R.id.btn_fullscreen);
        mBtnFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRestaurantLocation != null) {
                    Intent intent = new Intent(RestaurantActivity.this, FullscreenMapActivity.class);
                    intent.putExtra("latitude", mRestaurantLocation.latitude);
                    intent.putExtra("longitude", mRestaurantLocation.longitude);
                    intent.putExtra("name", mRestaurant.getName());
                    startActivity(intent);
                }
            }
        });

        mDB = new DatabaseHelper(this);

        Intent intent = getIntent();
        mRestaurant = intent.getParcelableExtra(RESTAURANT_KEY);

        mTVAddress = findViewById(R.id.Address);
        mTVDescription=findViewById(R.id.description);
        mTVName=findViewById(R.id.name);
        mTVType=findViewById(R.id.type);
        mTVTelephone = findViewById(R.id.telephone);

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
            mTVTelephone.setText(mRestaurant.getTelephone());
            ratingBar.setRating(mRestaurant.getRating());
        }
    }

    private LatLng getLocationFromAddress(String strAddress){
        Geocoder geocoder = new Geocoder(this);
        List<Address> address;
        LatLng latLng = null;

        try {
            address = geocoder.getFromLocationName(strAddress,1);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException IOE) {
            Log.d(TAG, "getLocationFromAddress: " + IOE.getLocalizedMessage());
        }
        return latLng;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mRestaurantLocation = getLocationFromAddress(mRestaurant.getAddress());
        if (mRestaurantLocation != null) {
            Marker marker = mMap.addMarker(
                    new MarkerOptions().position(mRestaurantLocation).title(mRestaurant.getName()));
            marker.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mRestaurantLocation, 10));
        }
    }
}