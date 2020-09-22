package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.FilterData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExRestCategoriesAdapter extends RecyclerView.Adapter<ExRestCategoriesAdapter.ViewHolder> {
    Context context;

    public ExRestCategoriesAdapter(ArrayList<FilterData> data, Context context) {
        this.data = data;
        this.context=context;
    }

    private ArrayList<FilterData> data;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView4);
            textView=itemView.findViewById(R.id.resturant_names);

        }
    }


    @NonNull
    @Override
    public ExRestCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_of_resturants,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExRestCategoriesAdapter.ViewHolder holder, int position) {
        FilterData filterData=data.get(position);
        holder.textView.setText(filterData.getText());

        //holder.checkBox.setImageResource(filterData.getImage());
        Picasso.get().load(filterData.getImage()).centerCrop().
                resize(250, 250).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
