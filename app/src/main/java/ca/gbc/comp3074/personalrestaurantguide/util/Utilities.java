package ca.gbc.comp3074.personalrestaurantguide.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ca.gbc.comp3074.personalrestaurantguide.database.DatabaseHelper;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class Utilities {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void addRows(DatabaseHelper db) {

        if (db.getAllRestaurants().isEmpty()) {
            db.insertRestaurant(Restaurant.builder()
                    .name("Craque de Creme")
                    .address("1360 Bathurst St, Toronto, ON M5R 3H7, Canada")
                    .description("Dessert restaurant")
                    .type("Fast Food")
                    .telephone("123-456-7890")
                    .rating(4.5f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("The Cheesecake Factory")
                    .address("3401 Dufferin St, North York, ON M6A 2T9, Canada")
                    .description("The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. " +
                            "Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.")
                    .type("Fast Food")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Ilden")
                    .address("Algade 62, 4000 Roskilde, Denmark")
                    .description("All you can eat buffet")
                    .type("Fast Food")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Miku Toronto")
                    .address("10 Bay St #105, Toronto, ON M5J 2R8, Canada")
                    .description("Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. " +
                            "With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.")
                    .type("Fast Food")
                    .telephone("123-456-7890")
                    .rating(4.6f)
                    .build());

            db.insertRestaurant(Restaurant.builder()
                    .name("Craque de Creme")
                    .address("1360 Bathurst St, Toronto, ON M5R 3H7, Canada")
                    .description("Dessert restaurant")
                    .type("Fast Casual")
                    .telephone("123-456-7890")
                    .rating(4.5f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("The Cheesecake Factory")
                    .address("3401 Dufferin St, North York, ON M6A 2T9, Canada")
                    .description("The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. " +
                            "Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.")
                    .type("Fast Casual")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Ilden")
                    .address("Algade 62, 4000 Roskilde, Denmark")
                    .description("All you can eat buffet")
                    .type("Fast Casual")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Miku Toronto")
                    .address("10 Bay St #105, Toronto, ON M5J 2R8, Canada")
                    .description("Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. " +
                            "With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.")
                    .type("Fast Casual")
                    .telephone("123-456-7890")
                    .rating(4.6f)
                    .build());

            db.insertRestaurant(Restaurant.builder()
                    .name("Craque de Creme")
                    .address("1360 Bathurst St, Toronto, ON M5R 3H7, Canada")
                    .description("Dessert restaurant")
                    .type("Casual Dining")
                    .telephone("123-456-7890")
                    .rating(4.5f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("The Cheesecake Factory")
                    .address("3401 Dufferin St, North York, ON M6A 2T9, Canada")
                    .description("The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. " +
                            "Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.")
                    .type("Casual Dining")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Ilden")
                    .address("Algade 62, 4000 Roskilde, Denmark")
                    .description("All you can eat buffet")
                    .type("Casual Dining")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Miku Toronto")
                    .address("10 Bay St #105, Toronto, ON M5J 2R8, Canada")
                    .description("Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. " +
                            "With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.")
                    .type("Casual Dining")
                    .telephone("123-456-7890")
                    .rating(4.6f)
                    .build());

            db.insertRestaurant(Restaurant.builder()
                    .name("Craque de Creme")
                    .address("1360 Bathurst St, Toronto, ON M5R 3H7, Canada")
                    .description("Dessert restaurant")
                    .type("Cafe Hybrid")
                    .telephone("123-456-7890")
                    .rating(4.5f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("The Cheesecake Factory")
                    .address("3401 Dufferin St, North York, ON M6A 2T9, Canada")
                    .description("The Cheesecake Factory story begins in Detroit, Michigan in the 1940’s. " +
                            "Evelyn Overton found a recipe in the local newspaper that would inspire her “Original” Cheesecake.")
                    .type("Cafe Hybrid")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Ilden")
                    .address("Algade 62, 4000 Roskilde, Denmark")
                    .description("All you can eat buffet")
                    .type("Cafe Hybrid")
                    .telephone("123-456-7890")
                    .rating(4.0f)
                    .build());
            db.insertRestaurant(Restaurant.builder()
                    .name("Miku Toronto")
                    .address("10 Bay St #105, Toronto, ON M5J 2R8, Canada")
                    .description("Aburi Restaurant’s first East Coast location is situated in Toronto’s Harbour Front at Bay and Queen’s Quay. " +
                            "With over 7000 square feet, a raw bar, sushi bar, and large patio, Miku brings contemporary upscale design to the Southern Financial District.")
                    .type("Cafe Hybrid")
                    .telephone("123-456-7890")
                    .rating(4.6f)
                    .build());
        }
    }
}
