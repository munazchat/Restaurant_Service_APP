package ca.gbc.comp3074.personalrestaurantguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Restaurant implements Comparable<Restaurant>, Parcelable {

    public static final String TABLE_NAME = "restaurants";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_RATING = "rating";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_ADDRESS + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT, "
                    + COLUMN_TYPE + " TEXT, "
                    + COLUMN_TELEPHONE + " TEXT, "
                    + COLUMN_RATING + " REAL"
                    + ")";

    private int id;
    private String name;
    private String address;
    private String description;
    private String type;
    private String telephone;
    private float rating;

    @Builder
    public Restaurant(int id, String name, String address, String description, String type, String telephone, float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.type = type;
        this.telephone = telephone;
        this.rating = rating;
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

    protected Restaurant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        type = in.readString();
        telephone = in.readString();
        rating = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(telephone);
        dest.writeFloat(rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Restaurant:\n" +
                "Name: " + name + "\n" +
                "Type: " + type + "\n" +
                "Description: " + description + "\n" +
                "Address: " + address + "\n" +
                "Contact: " + telephone + "\n" +
                "Rating: " + rating + "\n";

    }
}
