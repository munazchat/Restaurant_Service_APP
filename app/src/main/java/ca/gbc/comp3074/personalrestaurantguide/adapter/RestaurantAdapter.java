package ca.gbc.comp3074.personalrestaurantguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;
    private OnRestaurantClickListener listener;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View restaurantView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card, parent, false);
        return new MyViewHolder(restaurantView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Restaurant restaurant = restaurants.get(position);
        holder.restaurantName.setText(restaurant.getName());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName;

        public MyViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurant_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    listener.onRestaurantClicked(position);
                }
            });
        }
    }

    public interface OnRestaurantClickListener {
        void onRestaurantClicked(int position);
    }

    public void setOnRestaurantClickListener(OnRestaurantClickListener l) {
        listener = l;
    }
}
