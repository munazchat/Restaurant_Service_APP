package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.adapter.RestaurantAdapter;
import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class RestaurantListActivity extends AppCompatActivity {

    public static final String TAG = "RestaurantListActivity";
    public static final String RESTAURANT_KEY = "restaurant";

    private TextView mTopPickTextView;
    private RestaurantAdapter mAdapter;
    private List<Restaurant> mRestaurants = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DatabaseHelper mDB;

    private Restaurant mTopRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        init();
    }

    private void init() {
        mDB = new DatabaseHelper(this);
        addRows();

        mTopPickTextView = findViewById(R.id.top_pick_value);
        mRecyclerView = findViewById(R.id.recycler_view);

        //TODO: Add check for data from intent to see if we came from search or not
        mRestaurants.addAll(mDB.getAllRestaurants());

        mAdapter = new RestaurantAdapter(this, mRestaurants);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        if (!mRestaurants.isEmpty()) {
            mTopPickTextView.setText(getTopPick().getName());
        }

        mAdapter.setOnRestaurantClickListener(new RestaurantAdapter.OnRestaurantClickListener() {
            @Override
            public void onRestaurantClicked(int position) {
                goToRestaurant(mRestaurants.get(position));

                // TODO: remove this temporary code for deletion after prototypes
                mDB.deleteRestaurant(mRestaurants.get(position));
                mRestaurants.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private Restaurant getTopPick() {
        mTopRestaurant = Collections.max(mRestaurants);
        Log.d(TAG, "getTopPick: " + mTopRestaurant.getName());

        return mTopRestaurant;
    }

    public void goToTopPick(View v) {
        Log.d(TAG, "goToTopPick: clicked" );
        if (mTopRestaurant != null) {
            goToRestaurant(mTopRestaurant);
        }
    }

    //TODO: fix this when we know what target Activity is called
    private void goToRestaurant(Restaurant restaurant) {
//        Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
//        //TODO: parse the whole model in the future (requires Parcelable for model)
//        intent.putExtra(RESTAURANT_KEY, restaurant.getId());
//        startActivity(intent);
    }

    private void addRows() {
        if (mDB.getAllRestaurants().isEmpty()) {
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Fast Food", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "Fast Casual", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Casual Dining", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Cafe Hybrid", 4.6f));


            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Dessert", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "A la carte", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Buffet", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Japanese", 4.6f));
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Dessert", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "A la carte", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Buffet", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Japanese", 4.6f));
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Dessert", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "A la carte", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Buffet", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Japanese", 4.6f));
        }
    }
}