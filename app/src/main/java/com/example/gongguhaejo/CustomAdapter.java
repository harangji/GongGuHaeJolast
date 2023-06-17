package com.example.gongguhaejo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private ArrayList<GongguList> arrayList;
    private Context context;


    public CustomAdapter(ArrayList<GongguList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonggu_list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getFood_cate_image())
                .into(holder.iv_foodcateimage);
        int recruPerson = arrayList.get(position).getRecru_person();
        holder.tv_recruperson.setText(String.valueOf(recruPerson));
        holder.tv_restname.setText(arrayList.get(position).getRest_name());
        holder.tv_foodcate.setText(arrayList.get(position).getFood_cate());
        holder.tv_food_deliveryprice.setText(String.valueOf(arrayList.get(position).getFood_deliveryprice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GongguList gongguList = arrayList.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("gongguItem", gongguList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foodcateimage;
        TextView tv_restname;
        TextView tv_foodcate;
        TextView tv_food_deliveryprice;
        TextView tv_recruperson;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_foodcateimage = itemView.findViewById(R.id.iv_foodcateimage);
            this.tv_restname = itemView.findViewById(R.id.tv_restname);
            this.tv_foodcate = itemView.findViewById(R.id.tv_foodcate);
            this.tv_food_deliveryprice = itemView.findViewById(R.id.tv_fooddeliveryprice);
            this.tv_recruperson = itemView.findViewById(R.id.tv_recruperson);
        }
    }
}