package ca.gbc.comp3074.personalrestaurantguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ca.gbc.comp3074.personalrestaurantguide.R;

public class SearchActivity extends AppCompatActivity {

    private Spinner restaurantType;
    private EditText searchText;
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

         restaurantType = findViewById(R.id.spinner);
         searchText =  findViewById(R.id.search);
         search =  findViewById(R.id.button);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.restauranttypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantType.setAdapter(myAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent(SearchActivity.this, RestaurantListActivity.class);
                intent.putExtra("searchtext",searchText.getText().toString());
                intent.putExtra("type", restaurantType.getSelectedItem().toString());
                startActivity(intent);
            }
        });


    }
}
