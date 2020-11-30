package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.adapter.AdminRestaurantAdapter;
import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class AdminActivity extends AppCompatActivity {

    public static final String RESTAURANT_KEY = "restaurant";

    private AdminRestaurantAdapter mAdapter;
    private List<Restaurant> mRestaurants = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init() {
        mDB = new DatabaseHelper(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRestaurants.addAll(mDB.getAllRestaurants());

        mAdapter = new AdminRestaurantAdapter(this, mRestaurants);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRestaurantClickListener(new AdminRestaurantAdapter.OnRestaurantClickListener() {
            @Override
            public void onRestaurantClicked(int position) {
                Log.d("TEST", "onRestaurantClicked: ");
            }

            @Override
            public void onEditClicked(int position) {
                Log.d("TEST", "onEditClicked: ");
                Intent intent = new Intent(AdminActivity.this, AddOrEditRestaurantActivity.class);
                intent.putExtra(RESTAURANT_KEY, mRestaurants.get(position));
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked(int position) {
                Log.d("TEST", "onDeleteClicked: ");
                new AlertDialog.Builder(AdminActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this restaurant:\n" + mRestaurants.get(position).getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDB.deleteRestaurant(mRestaurants.get(position));
                                mRestaurants.remove(position);
                                mAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.admin_top_nav_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_restaurant:
                Intent intent = new Intent(AdminActivity.this, AddOrEditRestaurantActivity.class);
                startActivity(intent);
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
