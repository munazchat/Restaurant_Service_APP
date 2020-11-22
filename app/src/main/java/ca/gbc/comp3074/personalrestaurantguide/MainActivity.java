package ca.gbc.comp3074.personalrestaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner restaurantType;
    private EditText searchText;
    private Button Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         restaurantType = findViewById(R.id.spinner);
         searchText =  findViewById(R.id.search);
         Search =  findViewById(R.id.button);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,
                getResources().getStringArray(R.array.restauranttypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantType.setAdapter(myAdapter);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent(MainActivity.this,RestaurantListActivity.class);
                intent.putExtra("searchtext",searchText.getText().toString());
                intent.putExtra("type", restaurantType.getSelectedItem().toString());
                startActivity(intent);


            }
        });


    }
}
