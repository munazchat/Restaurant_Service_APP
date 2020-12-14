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
import ca.gbc.comp3074.personalrestaurantguide.util.Utilities;

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
        Utilities.addRows(mDB);

        mTopPickTextView = findViewById(R.id.top_pick_value);
        mRecyclerView = findViewById(R.id.recycler_view);

        mRestaurants.addAll(mDB.getAllRestaurants());

        mAdapter = new RestaurantAdapter(this, mRestaurants);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setUpAdapter();
    }

    private void setUpAdapter() {
        mRecyclerView.setAdapter(mAdapter);

        if (fromSearch) {
            mAdapter.setOnRestaurantClickListener(new RestaurantAdapter.OnRestaurantClickListener() {
                @Override
                public void onRestaurantClicked(int position) {
                    goToRestaurant(mResults.get(position));
                }
            });
        } else {
            mAdapter.setOnRestaurantClickListener(new RestaurantAdapter.OnRestaurantClickListener() {
                @Override
                public void onRestaurantClicked(int position) {
                    goToRestaurant(mRestaurants.get(position));
                }
            });
        }
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

    private void getTopPick() {
        if (fromSearch) {
            if (!mResults.isEmpty()) {
                mTopRestaurant = Collections.max(mResults);
            }
        } else {
            if (!mRestaurants.isEmpty()) {
                mTopRestaurant = Collections.max(mRestaurants);
            }
        }
        if (mTopRestaurant != null) {
            mTopPickTextView.setText(mTopRestaurant.getName());
        } else {
            mTopPickTextView.setText("No Restaurants matches your search");
        }
    }

    public void goToTopPick(View v) {
        Log.d(TAG, "goToTopPick: clicked" );
        if (mTopRestaurant != null) {
            goToRestaurant(mTopRestaurant);
        }
    }

    private void goToRestaurant(Restaurant restaurant) {
        Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
        intent.putExtra(RESTAURANT_KEY, restaurant);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.action_restaurants);
        if (fromSearch) {
            Intent intent = getIntent();
            String searchText = intent.getStringExtra("searchtext").toLowerCase();
            String type = intent.getStringExtra("type");

            for (Restaurant r : mRestaurants) {
                if (type != null && type.equalsIgnoreCase("Restaurant Type")) {
                    if (r.getName().toLowerCase().contains(searchText)) {
                        mResults.add(r);
                    }
                } else {
                    if (r.getType().equals(type) && r.getName().toLowerCase().contains(searchText)) {
                        mResults.add(r);
                    }
                }
            }
            mAdapter = new RestaurantAdapter(this, mResults);
            setUpAdapter();
        }
        getTopPick();
    }
}
