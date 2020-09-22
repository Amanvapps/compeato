package com.appinnovates.campeat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.appinnovates.campeat.views.activities.CouponMenu.couponValue;
import static com.appinnovates.campeat.views.activities.DealsMenu.discountedPrice;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {


    private Context context;
    private ArrayList<MenuItem> data;
    private OnMenuItemClickInterFace actionListener;
    private Long type;

    MenuItemsAdapter(ArrayList<MenuItem> data, Context context, OnMenuItemClickInterFace actionListener, Long type) {
        this.type = type;
        this.actionListener = actionListener;
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView itemName;
        ImageView image;
        TextView price;
        TextView discountedPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.menu_items_txt);
            price = itemView.findViewById(R.id.item_prices);
            image = itemView.findViewById(R.id.menu_image);
            discountedPrice = itemView.findViewById(R.id.item_discounted_price);
        }
    }

    @NonNull
    @Override
    public MenuItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MenuItemsAdapter.ViewHolder holder, int position) {
        MenuItem item = data.get(position);
        holder.itemName.setText(item.getMenu_name());
        holder.price.setText(String.format("%s %d", context.getString(R.string.krw), item.getPrice()));
        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (context.getClass().getName().equalsIgnoreCase("com.appinnovates.campeat.views.activities.DealsMenu")) {
            int discount = item.getPrice() / 100 * (100 - discountedPrice);
            holder.discountedPrice.setText(context.getString(R.string.krw) + " " + discount);
        } else {
            if (type == 1) {
                long discount = item.getPrice() / 100 * (100 - couponValue);
                    holder.discountedPrice.setText(context.getString(R.string.krw) + " " + discount);
            } else {
                int discount = item.getPrice() - Integer.parseInt(String.valueOf(couponValue));
                if (discount>0)
                holder.discountedPrice.setText(context.getString(R.string.won_symbol) + discount);
                else
                    holder.discountedPrice.setText(context.getString(R.string.won_symbol) + "0");
            }

        }
/*         {
            Picasso.get().load(item.getPicture().getUrl()).centerCrop().resize(50, 50).into(holder.image);
        }*/
        if (item.getPicture() != null) {
            Glide.with(context)
                    .load(item.getPicture().getUrl())
                    .override(150, 150)
                    .error(R.drawable.image_not_found)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("TAG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(view -> actionListener.onMenuItemClick(item, data, position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnMenuItemClickInterFace {
        void onMenuItemClick(MenuItem menuItem, ArrayList<MenuItem> menuItemList, int position);
    }

}
