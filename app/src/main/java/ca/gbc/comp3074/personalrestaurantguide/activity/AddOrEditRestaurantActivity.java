package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class AddOrEditRestaurantActivity extends AppCompatActivity {

    public static final String RESTAURANT_KEY = "restaurant";

    private TextView mTitle;
    private EditText mName;
    private EditText mAddress;
    private EditText mDescription;
    private Spinner mType;
    private Button mBtnSubmit;
    private DatabaseHelper mDB;

    private Restaurant mRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_restaurant);

        Intent intent = getIntent();
        mRestaurant = intent.getParcelableExtra(RESTAURANT_KEY);

        init();
    }

    private void init() {
        mDB = new DatabaseHelper(this);

        mTitle = findViewById(R.id.tv_title);
        mName = findViewById(R.id.et_restaurant_name);
        mAddress = findViewById(R.id.et_restaurant_address);
        mDescription = findViewById(R.id.et_restaurant_description);
        mType = findViewById(R.id.spinner);
        mBtnSubmit = findViewById(R.id.btn_submit);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AddOrEditRestaurantActivity.this, android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.restauranttypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(myAdapter);

        if (mRestaurant == null) {
            mTitle.setText("Add Restaurant");
        } else {
            mTitle.setText("Edit Restaurant");
            mName.setText(mRestaurant.getName());
            mAddress.setText(mRestaurant.getAddress());
            mDescription.setText(mRestaurant.getDescription());
            mType.setSelection(myAdapter.getPosition(mRestaurant.getType()));
        }

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We are adding a new restaurant
                if (mRestaurant == null) {
                    Restaurant restaurant = new Restaurant(
                            mName.getText().toString(),
                            mAddress.getText().toString(),
                            mDescription.getText().toString(),
                            mType.getSelectedItem().toString(),
                            0.0f);

                    mDB.insertRestaurant(restaurant);
                    Toast.makeText(getApplicationContext(), "Restaurant was created", Toast.LENGTH_LONG).show();
                    // we're editing a restaurant
                } else {
                    mRestaurant.setName(mName.getText().toString());
                    mRestaurant.setAddress(mAddress.getText().toString());
                    mRestaurant.setDescription(mDescription.getText().toString());
                    mRestaurant.setType(mType.getSelectedItem().toString());

                    mDB.updateRestaurant(mRestaurant);
                    Toast.makeText(getApplicationContext(), "Restaurant was updated", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
