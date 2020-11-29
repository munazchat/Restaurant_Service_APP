package ca.gbc.comp3074.personalrestaurantguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.gbc.comp3074.personalrestaurantguide.R;
import ca.gbc.comp3074.personalrestaurantguide.model.Restaurant;

public class AdminRestaurantAdapter extends RecyclerView.Adapter<AdminRestaurantAdapter.MyViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;
    private OnRestaurantClickListener listener;

    public AdminRestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View restaurantView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_restaurant_card, parent, false);
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
        public Button btn_edit;
        public Button btn_delete;


        public MyViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurant_name);
            btn_edit = view.findViewById(R.id.btn_edit);
            btn_delete = view.findViewById(R.id.btn_delete);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRestaurantClicked(getAdapterPosition());
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onEditClicked(getAdapterPosition());
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnRestaurantClickListener {
        void onRestaurantClicked(int position);
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

    public void setOnRestaurantClickListener(OnRestaurantClickListener l) {
        listener = l;
    }
}
