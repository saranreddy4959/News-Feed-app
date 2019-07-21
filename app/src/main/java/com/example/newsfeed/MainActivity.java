package com.example.newsfeed;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String api_key="24cd804caf9d4af699be1bc8dfe92e99";
    String news_source="cnn";
    ListView news;
    ProgressBar page_loading;

    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    static  final String publishedat_key="publishedAt";
    static final String urltoimage_key="urlToImage";
    static final String url_key="url";
    static final String description_key="description";
    static final String titile_key="title";
    static final String author_key="author";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        news = (ListView) findViewById(R.id.listNews);
        page_loading = (ProgressBar) findViewById(R.id.progressBar);
        news.setEmptyView(page_loading);

        if(Connection.network_availability(getApplicationContext()))
        {
            News_Download news = new News_Download();
            news.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    class News_Download extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        protected String doInBackground(String... args){
            String xml="";
            String urlparameters="";
            xml=Connection.excution("https://newsapi.org/v1/articles?source="+news_source+"&sortBy=top&apikey="+api_key,urlparameters);
            return xml;
        }
        @Override
        protected void onPostExecute(String xml){
            if(xml.length()>10)
                {
                    try{
                        JSONObject jsonResponse= new JSONObject(xml);
                        JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put(author_key,jsonObject.optString(author_key).toString());
                            map.put(titile_key,jsonObject.optString(titile_key).toString());
                            map.put(description_key,jsonObject.optString(description_key).toString());
                            map.put(url_key,jsonObject.optString(url_key).toString());
                            map.put(urltoimage_key,jsonObject.optString(urltoimage_key).toString());
                            map.put(publishedat_key,jsonObject.optString(publishedat_key).toString());
                            list.add(map);
                        }


                    }
                    catch (JSONException e){
                        Toast.makeText(getApplicationContext(),"There is an error",Toast.LENGTH_SHORT).show();
                    }

                    NewsAdapter adapter = new NewsAdapter(MainActivity.this,list);
                    news.setAdapter(adapter);

                    news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent i = new Intent(MainActivity.this, BrowserActivity.class);
                            i.putExtra("url", list.get(+position).get(url_key));
                            startActivity(i);
                        }
                    });
                }else{
                Toast.makeText(getApplicationContext(),"No News Found here",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
