package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.AdService.Option;

import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Option> options;

    public OptionsAdapter(Context context, ArrayList<Option> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_option,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Option option = options.get(position);
        holder.txtOption.setText(option.name);
        holder.btn.setChecked(option.isSelected);
        holder.btn.setClickable(false);
        holder.itemView.setOnClickListener(v -> {
            for (Option option1 :options) {
                option1.isSelected = false;
            }
            option.isSelected = true;
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return options != null ? options.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtOption;
        RadioButton btn;
        public ViewHolder(View itemView) {
            super(itemView);
            txtOption = itemView.findViewById(R.id.txt_option);
            btn = itemView.findViewById(R.id.btn);
        }
    }
}
