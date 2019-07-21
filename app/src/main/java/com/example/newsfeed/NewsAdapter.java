package com.example.newsfeed;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String,String>> li;

    public NewsAdapter(Activity a, ArrayList<HashMap<String,String>> l)
    {
        activity=a;
        li=l;

    }
    public int getCount(){
        return li.size();
    }
    public Object getItem(int position){
        return position;

    }
    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ListNewsViewHolder holder=null;
        if (convertView==null){
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_list, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = li.get(position);

        try{
            holder.author.setText(song.get(MainActivity.author_key));
            holder.title.setText(song.get(MainActivity.titile_key));
            holder.time.setText(song.get(MainActivity.publishedat_key));
            holder.sdetails.setText(song.get(MainActivity.description_key));

            if(song.get(MainActivity.urltoimage_key).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(song.get(MainActivity.urltoimage_key).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}
