package com.example.diary;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class ChatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<ChatVO> chatData;
    private LayoutInflater inflater;
    private String id;


    public ChatAdapter(Context applicationContext, int talklist, ArrayList<ChatVO> list, String id) {
        this.context = applicationContext;
        this.layout = talklist;
        this.chatData = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.id= id;
    }

    @Override
    public int getCount() { // 전체 데이터 개수
        return chatData.size();
    }

    @Override
    public Object getItem(int position) { // position번째 아이템
        return chatData.get(position);
    }

    @Override
    public long getItemId(int position) { // position번째 항목의 id인데 보통 position
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.img= (ImageView)convertView.findViewById(R.id.iv_profile);
            holder.tv_msg = (TextView)convertView.findViewById(R.id.tv_content);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_id);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.my_msg = (TextView)convertView.findViewById(R.id.my_msg);
            holder.my_time = (TextView)convertView.findViewById(R.id.my_time);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        //누군지 판별
        if(chatData.get(position).getId().equals(id)){
            holder.tv_time.setVisibility(View.GONE);
            holder.tv_name.setVisibility(View.GONE);
            holder.tv_msg.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);

            holder.my_msg.setVisibility(View.VISIBLE);
            holder.my_time.setVisibility(View.VISIBLE);

            holder.my_time.setText(chatData.get(position).getTime());
            holder.my_msg.setText(chatData.get(position).getContent());
        }else{
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_name.setVisibility(View.VISIBLE);
            holder.tv_msg.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.VISIBLE);

            holder.my_msg.setVisibility(View.GONE);
            holder.my_time.setVisibility(View.GONE);

            holder.img.setImageResource(chatData.get(position).getImageID());
            holder.tv_msg.setText(chatData.get(position).getContent());
            holder.tv_time.setText(chatData.get(position).getTime());
            holder.tv_name.setText(chatData.get(position).getId());
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView img;
        TextView tv_msg;
        TextView tv_time;
        TextView tv_name;
        TextView my_time;
        TextView my_msg;
    }

}