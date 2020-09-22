package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.FilterData;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    public FilterAdapter(ArrayList<FilterData> data, Context context, OnCategoryItemInterface onCategoryItemInterface) {
        this.data = data;
        this.context = context;
        this.onCategoryItemInterface = onCategoryItemInterface;
    }

/*    public void setOnItemClickListener(OnCategoryItemInterface onCategoryItemInterface) {
        this.onCategoryItemInterface = onCategoryItemInterface;
    }*/

    private ArrayList<FilterData> data;
    Context context;
    private ArrayList<String> items;
    private Boolean isClicked = false;
    private OnCategoryItemInterface onCategoryItemInterface;
    private ArrayList<String> categoryTypes;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView filteritems;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            items = new ArrayList<>();
            categoryTypes = new ArrayList<>();
            imageView = itemView.findViewById(R.id.filter_icon_imageview);
            textView = itemView.findViewById(R.id.filter_text);
            filteritems = itemView.findViewById(R.id.filter_cardview);
            addItems();

        }
    }


    @NonNull
    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filters_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.ViewHolder holder, int position) {
        FilterData filterData = data.get(position);
        holder.textView.setText(filterData.getText());
        holder.imageView.setImageResource(filterData.getImage());
/*        Glide.with(context)
                .load(filterData.getImage())
                .override(100,100)
                .into(holder.imageView);*/
        filterData.setSelected(true);

        holder.itemView.setOnClickListener(v -> {
            if (filterData.getSelected()) {
                holder.filteritems.setBackground(context.getResources().getDrawable(R.drawable.border_unfill_orange_white));
                filterData.setSelected(false);
                items.add(categoryTypes.get(position));
            } else {
                holder.filteritems.setBackground(context.getResources().getDrawable(R.drawable.border_unfill_white));
                filterData.setSelected(true);
                items.remove(categoryTypes.get(position));
            }
            // if (onCategoryItemInterface != null) {
            onCategoryItemInterface.onCategoryItemClick(position, isClicked, items);

        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnCategoryItemInterface {
        void onCategoryItemClick(int text, Boolean isClicked, ArrayList<String> items);
    }

    private void addItems() {
        categoryTypes.add("Korean");
        categoryTypes.add("Japanese");
        categoryTypes.add("Chinese");
        categoryTypes.add("Fast food");
        categoryTypes.add("Chicken");
        categoryTypes.add("Pizza");
        categoryTypes.add("Desert");
        categoryTypes.add("Global");


//
//        categoryTypes.add("Kitchens");
//        categoryTypes.add("Fastfood");
//        categoryTypes.add("Sushi");
//        categoryTypes.add("Coffee");
//        categoryTypes.add("Pubs");
//        categoryTypes.add("Restaurants");
//        categoryTypes.add("Grocery");
//        categoryTypes.add("Others");


    }
}
