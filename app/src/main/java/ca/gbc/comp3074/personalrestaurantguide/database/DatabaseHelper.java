package ca.gbc.comp3074.personalrestaurantguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurants_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Restaurant.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Restaurant.TABLE_NAME);
        onCreate(db);
    }

    public long insertRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Restaurant.COLUMN_NAME, restaurant.getName());
        values.put(Restaurant.COLUMN_ADDRESS, restaurant.getAddress());
        values.put(Restaurant.COLUMN_DESCRIPTION, restaurant.getDescription());
        values.put(Restaurant.COLUMN_RATING, restaurant.getRating());
        values.put(Restaurant.COLUMN_TYPE, restaurant.getType());
        values.put(Restaurant.COLUMN_TELEPHONE, restaurant.getTelephone());

        long id = db.insert(Restaurant.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int updateRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Restaurant.COLUMN_ID, restaurant.getId());
        values.put(Restaurant.COLUMN_NAME, restaurant.getName());
        values.put(Restaurant.COLUMN_ADDRESS, restaurant.getAddress());
        values.put(Restaurant.COLUMN_DESCRIPTION, restaurant.getDescription());
        values.put(Restaurant.COLUMN_RATING, restaurant.getRating());
        values.put(Restaurant.COLUMN_TYPE, restaurant.getType());
        values.put(Restaurant.COLUMN_TELEPHONE, restaurant.getTelephone());

        int count = db.update(Restaurant.TABLE_NAME, values, Restaurant.COLUMN_ID + "=?",
                new String[]{String.valueOf(restaurant.getId())});

        return count;
    }

    public Restaurant getRestaurant(long id) {
        SQLiteDatabase db = getWritableDatabase();

        Restaurant restaurant = null;

        Cursor cursor = db.query(Restaurant.TABLE_NAME,
                new String[]{
                        Restaurant.COLUMN_ID,
                        Restaurant.COLUMN_NAME,
                        Restaurant.COLUMN_ADDRESS,
                        Restaurant.COLUMN_DESCRIPTION,
                        Restaurant.COLUMN_TYPE,
                        Restaurant.COLUMN_TELEPHONE,
                        Restaurant.COLUMN_RATING
                },
                Restaurant.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            restaurant = Restaurant.builder()
                    .id(cursor.getInt(cursor.getColumnIndex(Restaurant.COLUMN_ID)))
                    .name(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_NAME)))
                    .address(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_ADDRESS)))
                    .description(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_DESCRIPTION)))
                    .type(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_TYPE)))
                    .telephone(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_TELEPHONE)))
                    .rating(cursor.getFloat(cursor.getColumnIndex(Restaurant.COLUMN_RATING)))
                    .build();
            cursor.close();
        }

        db.close();
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        SQLiteDatabase db = getReadableDatabase();

        List<Restaurant> list = new ArrayList<>();

        String query = "SELECT * FROM " + Restaurant.TABLE_NAME +
                " ORDER BY " + Restaurant.COLUMN_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Restaurant item = Restaurant.builder()
                        .id(cursor.getInt(cursor.getColumnIndex(Restaurant.COLUMN_ID)))
                        .name(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_NAME)))
                        .address(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_ADDRESS)))
                        .description(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_DESCRIPTION)))
                        .type(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_TYPE)))
                        .telephone(cursor.getString(cursor.getColumnIndex(Restaurant.COLUMN_TELEPHONE)))
                        .rating(cursor.getFloat(cursor.getColumnIndex(Restaurant.COLUMN_RATING)))
                        .build();
                list.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public int deleteRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete(Restaurant.TABLE_NAME,
                Restaurant.COLUMN_ID + "=?",
                new String[]{String.valueOf(restaurant.getId())});

        db.close();
        return count;
    }
}
