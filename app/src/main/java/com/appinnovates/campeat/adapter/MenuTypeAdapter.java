package com.appinnovates.campeat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuTypeModel;

import java.util.List;

public class MenuTypeAdapter extends RecyclerView.Adapter<MenuTypeAdapter.ViewHolder> {

    private final List<MenuTypeModel> menuTypeModelList;

    public MenuTypeAdapter(List<MenuTypeModel> menuTypeModelList) {
        this.menuTypeModelList = menuTypeModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuTypeModel menuModel = menuTypeModelList.get(position);
        holder.checkBox.setText(menuModel.getMenu_type_name());
        if (menuModel.isSelected()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                menuModel.setSelected(true);
            } else {
                menuModel.setSelected(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuTypeModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.check_box_menu_type);
        }
    }
}
