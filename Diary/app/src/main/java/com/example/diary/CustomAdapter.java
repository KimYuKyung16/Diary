package com.example.diary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    private ArrayList<DiaryItem> mdiaryItems;
    private Context mContext;
    private ArrayList<DiaryItem> filtered_mdiaryItems;

    public CustomAdapter(ArrayList<DiaryItem> mdiaryItems, Context mContext) {
        this.mdiaryItems = mdiaryItems;
        this.mContext = mContext;
        this.filtered_mdiaryItems = mdiaryItems;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(filtered_mdiaryItems.get(position).getTitle());
        holder.tv_content.setText(filtered_mdiaryItems.get(position).getContent());
        holder.tv_year.setText(filtered_mdiaryItems.get(position).getYear());
        holder.tv_month.setText(filtered_mdiaryItems.get(position).getMonth());
        holder.tv_day.setText(filtered_mdiaryItems.get(position).getDay());

        if(filtered_mdiaryItems.get(position).getMood_id().equals("1")) {
            holder.mood_image.setImageResource(R.drawable.moon1);
        }
        else if(filtered_mdiaryItems.get(position).getMood_id().equals("2")) {
            holder.mood_image.setImageResource(R.drawable.moon2);
        }
        else if(filtered_mdiaryItems.get(position).getMood_id().equals("3")) {
            holder.mood_image.setImageResource(R.drawable.moon3);
        }
        else if(filtered_mdiaryItems.get(position).getMood_id().equals("4")) {
            holder.mood_image.setImageResource(R.drawable.moon4);
        }
        else if(filtered_mdiaryItems.get(position).getMood_id().equals("5")) {
            holder.mood_image.setImageResource(R.drawable.moon5);
        }



    }

    @Override
    public int getItemCount() {
        return filtered_mdiaryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_year;
        private TextView tv_month;
        private TextView tv_day;
        private ImageView mood_image;
        //private TextView tv_writeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title= itemView.findViewById(R.id.tv_title);
            tv_content= itemView.findViewById(R.id.tv_content);
            tv_year= itemView.findViewById(R.id.tv_year);
            tv_month= itemView.findViewById(R.id.tv_month);
            tv_day= itemView.findViewById(R.id.tv_day);
            mood_image= itemView.findViewById(R.id.mood_image);


            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, DiaryListClickActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //
                        intent.putExtra("id",filtered_mdiaryItems.get(pos).getId());

                        intent.putExtra("title",filtered_mdiaryItems.get(pos).getTitle());
                        intent.putExtra("content",filtered_mdiaryItems.get(pos).getContent());

                        intent.putExtra("year",filtered_mdiaryItems.get(pos).getYear());
                        intent.putExtra("month",filtered_mdiaryItems.get(pos).getMonth());
                        intent.putExtra("day",filtered_mdiaryItems.get(pos).getDay());

                        intent.putExtra("imageUrl",filtered_mdiaryItems.get(pos).getImageUrl());

                        intent.putExtra("mood_id",filtered_mdiaryItems.get(pos).getMood_id());
                        intent.putExtra("weather_id",filtered_mdiaryItems.get(pos).getWeather_id());

                        mContext.startActivity(intent);
                    }
                    notifyItemChanged(pos);
                }
            });

        }

    }

    public void addItem(DiaryItem _item){
        mdiaryItems.add(0, _item);
        notifyItemInserted(0);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                     filtered_mdiaryItems= mdiaryItems;
                } else {
                    ArrayList<DiaryItem> filtering_mdiaryItems = new ArrayList<>();
                    for(DiaryItem item : mdiaryItems) {
                        if(item.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filtering_mdiaryItems.add(item);
                        }
                    }
                    filtered_mdiaryItems = filtering_mdiaryItems;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_mdiaryItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered_mdiaryItems = (ArrayList<DiaryItem>)results.values;
                notifyDataSetChanged();
            }
        };
    }





}
