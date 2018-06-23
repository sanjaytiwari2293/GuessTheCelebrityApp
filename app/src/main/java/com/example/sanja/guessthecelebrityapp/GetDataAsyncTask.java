package com.example.sanja.guessthecelebrityapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDataAsyncTask extends AsyncTask<String, Void, ArrayList<Celebrity>> {

    ArrayList<Celebrity> list = new ArrayList<>();

    @Override
    protected ArrayList<Celebrity> doInBackground(String... strings) {

        String strUrl = strings[0];

        try {

            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            InputStream is = connection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is);

            BufferedReader reader = new BufferedReader(isReader);
            String line="";

            Pattern p1 = Pattern.compile("img src=\"(.*?)\"");


            Pattern p2 = Pattern.compile("alt=\"(.*?)\"/>");



            while((line=reader.readLine())!=null){
                Matcher m1 = p1.matcher(line);
                Matcher m2 = p2.matcher(line);

                while(m1.find() && m2.find()){
                    String img = m1.group(1);
                    String name = m2.group(1);

                    Celebrity c = new Celebrity(name, img);
                    list.add(c);
                }

            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("test","list size: "+list.size());
        return list;
    }
}
