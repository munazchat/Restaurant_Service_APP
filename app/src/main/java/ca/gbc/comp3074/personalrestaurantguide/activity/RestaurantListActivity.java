package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private boolean fromSearch;
    private List<Restaurant> mResults = new ArrayList<>();

    private Restaurant mTopRestaurant;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Log.d(TAG, "onCreate: " + getIntent().hasExtra("type"));

        init();
        setUpBottomNav();
    }

    private void init() {
        fromSearch = getIntent().hasExtra("searchtext");
        mDB = new DatabaseHelper(this);
        addRows();

        mTopPickTextView = findViewById(R.id.top_pick_value);
        mRecyclerView = findViewById(R.id.recycler_view);

        mRestaurants.addAll(mDB.getAllRestaurants());
        //TODO: Add check for data from intent to see if we came from search or not

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
//                mDB.deleteRestaurant(mRestaurants.get(position));
//                mRestaurants.remove(position);
//                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setUpBottomNav() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Whenever an item in the bottom navigation is selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.action_search:
                        intent = new Intent(RestaurantListActivity.this, SearchActivity.class);
                        break;
                    case R.id.action_restaurants:
                        break;
                    case R.id.action_admin:
                        intent = new Intent(RestaurantListActivity.this, AdminActivity.class);
                        break;
//                    case R.id.action_about:
//                        intent = new Intent(RestaurantListActivity.this, AboutActivity.class);
//                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.restaurant_list_top_nav_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(RestaurantListActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
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

    private void goToRestaurant(Restaurant restaurant) {
        Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
        //TODO: parse the whole model in the future (requires Parcelable for model)
        intent.putExtra(RESTAURANT_KEY, restaurant.getId());
        startActivity(intent);
    }

    private void addRows() {
        if (mDB.getAllRestaurants().isEmpty()) {
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Fast Food", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "Fast Food", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Fast Food", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Fast Food", 4.6f));


            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Fast Casual", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "Fast Casual", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Fast Casual", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Fast Casual", 4.6f));
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Casual Dining", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "Casual Dining", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Casual Dining", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Casual Dining", 4.6f));
            mDB.insertRestaurant(
                    new Restaurant("Craque de Creme", "1360 Bathurst St, Toronto, ON M5R 3H7, Canada",
                            "Dessert restaurant", "Cafe Hybrid", 4.5f));
            mDB.insertRestaurant(
                    new Restaurant("The Cheesecake Factory", "3401 Dufferin St, North York, ON M6A 2T9, Canada",
                            "The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.",
                            "Cafe Hybrid", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Ilden", "Algade 62, 4000 Roskilde, Denmark",
                            "All you can eat buffet", "Cafe Hybrid", 4.0f));
            mDB.insertRestaurant(
                    new Restaurant("Miku Toronto", "10 Bay St #105, Toronto, ON M5J 2R8, Canada",
                            "Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.",
                            "Cafe Hybrid", 4.6f));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.action_restaurants);
        if (fromSearch) {
            Intent intent = getIntent();
            String searchText = intent.getStringExtra("searchtext");
            String type = intent.getStringExtra("type");

            for (Restaurant r : mRestaurants) {
                if (r.getType().equals(type) && r.getName().contains(searchText)) {
                    mResults.add(r);
                }
            }
            mAdapter = new RestaurantAdapter(this, mResults);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
