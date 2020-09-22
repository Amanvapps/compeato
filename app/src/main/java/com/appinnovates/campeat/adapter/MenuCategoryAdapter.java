package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuItem;
import com.appinnovates.campeat.model.MenuSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.ViewHolder> {

    private List<MenuSection> list = new ArrayList<>();
    private MenuItemsAdapter.OnMenuItemClickInterFace listener;
    private Context context;
    private Long type;

    public MenuCategoryAdapter(Context context, MenuItemsAdapter.OnMenuItemClickInterFace listener,Long type) {
        this.listener = listener;
        this.context = context;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expansion_panel_recycler_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuSection itemSection = list.get(position);
        ArrayList<MenuItem> menuItem = (ArrayList<MenuItem>) itemSection.getMenuItem();
        holder.categories.setText(itemSection.getCatName());
        holder.itemsCount.setText(String.valueOf(menuItem.size()));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setHasFixedSize(true);
        MenuItemsAdapter adapter = new MenuItemsAdapter(menuItem, context, listener,type);
        if (position == 0) {
            holder.subItem.setVisibility(View.VISIBLE);
            holder.subItem.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down));
            holder.itemImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.open_menu));
        }
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.itemLayout.setOnClickListener(v -> {
            Animation open = AnimationUtils.loadAnimation(context, R.anim.open_menu);
            Animation close = AnimationUtils.loadAnimation(context, R.anim.close_menu);
            Animation slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            Animation slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            if (holder.subItem.getVisibility() == View.VISIBLE) {
                holder.subItem.setVisibility(View.GONE);
                holder.subItem.setAnimation(slide_up);
                holder.itemImage.setAnimation(close);
            } else {
                holder.subItem.setVisibility(View.VISIBLE);
                holder.subItem.setAnimation(slide_down);
                holder.itemImage.setAnimation(open);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categories;
        TextView items;
        TextView itemsCount;
        RecyclerView recyclerView;
        View subItem;
        ConstraintLayout itemLayout;
        AppCompatImageView itemImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            categories = itemView.findViewById(R.id.categories_names);
            items = itemView.findViewById(R.id.menu_items_txt);
            itemsCount = itemView.findViewById(R.id.item_count);
            recyclerView = itemView.findViewById(R.id.menuItemsRecyclerview);
            itemLayout = itemView.findViewById(R.id.item_layout);
            subItem = itemView.findViewById(R.id.sub_item);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }

    public void setItems(List<MenuSection> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }
}