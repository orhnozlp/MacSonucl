package com.example.asus_pc.mobilproje;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Canli extends Fragment {

    public static  List <MobileOS> mobileOSList= new ArrayList<>();
    Context context;
    ArrayList<String> denemelist = new ArrayList<>();
    ArrayAdapter adapter;

    public  static ListView liste;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.canli,container,false);
         mobileOSList.clear();
        liste=(ListView)view.findViewById(R.id.liste);
        context =getActivity().getApplicationContext();
        new Macliste().execute();

        return  view ;


    }

    private  class Macliste extends  AsyncTask<Void,Void,Void>{


        String evsahibi;
        String result = "";
        String homeTeamName,awayTeamName,score;
        JSONObject results;



        @Override
        protected void onPostExecute(Void aVoid) {

            try {

                // tüm bilgiler listeye atıldı
                CustomAdapter customAdapter = new CustomAdapter(context,mobileOSList);
                liste.setAdapter(customAdapter);



            } catch (Exception e) {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.tuttur.com/live-score/event-list";
            String jsonStr = sh.makeServiceCall(url);
            JSONObject firstObj;
            String state;
            String curentTimestampt;

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject started = jsonObject.getJSONObject("started");

                JSONArray matchlist = started.getJSONArray("matches");


                for (int i = 0 ; i<matchlist.length();i++) {
                    firstObj = matchlist.getJSONObject(i);
                    if (firstObj.getString("type").equals("football")) {
                        homeTeamName = firstObj.getString("homeTeamName");
                        awayTeamName = firstObj.getString("awayTeamName");

                        results = firstObj.getJSONObject("result");

                        score = results.getString("Current");


                        score= score.replace("[","");
                        score= score.replace("]","");
                        score= score.replace(",",":");
                        state=firstObj.getString("state");
                        curentTimestampt=firstObj.getString("currentPeriodTimestamp");
                        String dakika=""+epochTime(curentTimestampt,state);
                        if(dakika.equals("4555")) dakika=""+"DA";
                        else if(dakika.equals("111")) dakika=""+"45+" + "'";
                        else if (dakika.equals("222")) dakika="" + "90+"+"'";

                        else
                        {
                            dakika+="'";
                        }

                        mobileOSList.add(new MobileOS(dakika, homeTeamName, score, awayTeamName,"devam"));

                    }


                }



            }catch (Exception e){


            }

            return null;



        }



    }



    public long epochTime(String matchTime,String State)
    {
        long dakika = 2;

        if (State.equals("1st half"))
        {
            long epochtimee=Long.parseLong(matchTime);
            long currentEpoch=System.currentTimeMillis()/1000;
            dakika= TimeUnit.SECONDS.toMinutes((currentEpoch-epochtimee));
            if(dakika>45) dakika=111;

        }


        else if(State.equals("Halftime"))
        {
            dakika=4555;

        }

        else if(State.equals("2nd half"))
        {
            long epochtimee=Long.parseLong(matchTime);
            long currentEpoch=System.currentTimeMillis()/1000;
            dakika= TimeUnit.SECONDS.toMinutes((currentEpoch-epochtimee));
            dakika+=44;
            if (dakika>90) dakika=222;
        }


        return dakika;
    }

}
