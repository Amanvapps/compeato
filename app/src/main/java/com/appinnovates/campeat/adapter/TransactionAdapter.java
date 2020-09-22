package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.services.AdService.TADPEntry;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionVH> {

    private Context context;
    private ArrayList<TADPEntry> tadpEntries;
    public TransactionAdapter(Context context,ArrayList<TADPEntry> tadpEntries) {
        this.context = context;
        this.tadpEntries = tadpEntries;
    }

    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item_layout,parent,false);
        return new TransactionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {
        try{
           TADPEntry entry = tadpEntries.get(position);
            if (entry.ad != null){
                holder.txtName.setText(entry.ad.type.name +" - "+ entry.ad.name);
                holder.txtPoints.setText("+ "+ entry.points +" "+context.getString(R.string.tadp));
            }else{

                BranchModel branchModel = entry.branchModel;
                String title = "Debited";
                if (branchModel != null && branchModel.getBranchName() != null){
                    title = (context.getResources().getString(R.string.payment)) + " - " + branchModel.getBranchName();
                }

                holder.txtName.setText(title);
                holder.txtPoints.setTextColor(entry.points > 0 ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.redColor));
                String pointsText = (entry.points > 0 ? "+ " : "- ") + String.valueOf(Math.abs(entry.points)) + (entry.isTad ? " "+context.getString(R.string.tad) : " "+context.getString(R.string.tadp));
                holder.txtPoints.setText(pointsText);
            }

            holder.txtDate.setText(entry.createdAt);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return tadpEntries.size();
    }

    class TransactionVH extends RecyclerView.ViewHolder{
        TextView txtName,txtDate,txtPoints;
        public TransactionVH(View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtPoints = itemView.findViewById(R.id.txt_points);
        }
    }

    public void updateData(ArrayList<TADPEntry> tadpEntries){
        this.tadpEntries.clear();
        this.tadpEntries.addAll(tadpEntries);
        notifyDataSetChanged();
    }

}
