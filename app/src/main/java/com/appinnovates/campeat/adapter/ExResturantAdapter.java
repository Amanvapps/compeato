package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.ExRestData;
import com.appinnovates.campeat.model.FilterData;
import com.appinnovates.campeat.views.activities.ItemDetailActivity;

import java.util.ArrayList;

public class ExResturantAdapter extends RecyclerView.Adapter<ExResturantAdapter.ViewHolder> {

    public ExResturantAdapter(ArrayList<ExRestData> data, Context context) {
        this.data = data;
        this.context=context;
    }

    private ArrayList<ExRestData> data;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
/*            Button button=itemView.findViewById(R.id.cancel_deal);*/
            textView=itemView.findViewById(R.id.resturant_names);
            recyclerView=itemView.findViewById(R.id.recyclerView_categories);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setHasFixedSize(true);

            ArrayList<FilterData> data =new ArrayList<>();
/*

            data.add(new FilterData(R.drawable.a1,"Burger"));
            data.add(new FilterData(R.drawable.a2,"Pizza"));
            data.add(new FilterData(R.drawable.a3,"Burger"));
            data.add(new FilterData(R.drawable.a4,"Burger"));
            data.add(new FilterData(R.drawable.a5,"Burger"));
            data.add(new FilterData(R.drawable.a6,"Burger"));
*/

            ExRestCategoriesAdapter adapter=new ExRestCategoriesAdapter(data,context);
            recyclerView.setAdapter(adapter);


            itemView.setOnClickListener(view -> {
                Intent intent=new Intent(context, ItemDetailActivity.class);
                context.startActivity(intent);
            });
        }
    }


    @NonNull
    @Override
    public ExResturantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.resturantscardlayout,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExResturantAdapter.ViewHolder holder, int position) {
        ExRestData filterData=data.get(position);
        holder.textView.setText(filterData.getText());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
