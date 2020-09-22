package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuItem;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<MenuItem> mData;
    private ActionListener actionListener;


    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        final TextView menuName;
        private final TextView price;
        private final TextView desc;

        SimpleViewHolder(View view) {
            super(view);
            menuName =  view.findViewById(R.id.text_view_name);
            price =  view.findViewById(R.id.text_view_price);
            desc =  view.findViewById(R.id.text_view_desc);
        }
    }

    public MenuAdapter(Context context, List<MenuItem> data) {
        mContext = context;
        this.mData = data;
    }

    @NonNull
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_menu_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, final int position) {
        if (mData.size()>0){
        holder.menuName.setText(mData.get(position).getMenu_name());
        holder.desc.setText(mData.get(position).getMenu_extra_details());
        String price = "₩" +mData.get(position).getPrice();
        holder.price.setText(price);
        }
        holder.itemView.setOnClickListener(view -> actionListener.onItemClick(mData.get(position)));
    }

    public void setAction(ActionListener listener) {
        actionListener = listener;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ActionListener {
        void onItemClick(MenuItem menuItem);
    }
}
