package ca.gbc.comp3074.personalrestaurantguide.model;

public class Restaurant implements Comparable<Restaurant>{

    public static final String TABLE_NAME = "restaurants";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_RATING = "rating";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_ADDRESS + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT, "
                    + COLUMN_TYPE + " TEXT, "
                    + COLUMN_RATING + " REAL"
                    + ")";

    private int id;
    private String name;
    private String address;
    private String description;
    private String type;
    private float rating;

    public Restaurant(String name, String address, String description, String type, float rating) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.type = type;
        this.rating = rating;
    }

    public Restaurant(int id, String name, String address, String description, String type, float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.type = type;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Restaurant restaurant) {
        if (this.getRating() > restaurant.getRating()) {
            return 1;
        } else if (this.getRating() < restaurant.getRating()) {
            return -1;
        }
        return 0;
    }
}
