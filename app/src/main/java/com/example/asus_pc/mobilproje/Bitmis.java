package com.example.asus_pc.mobilproje;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.UriMatcher;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.sun.jna.platform.win32.WinDef;

import org.json.JSONException;
import org.json.JSONObject;
import  org.json.JSONArray;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bitmis extends Fragment {
    public static List<MobileOS> mobileOSList = new ArrayList<>();
    Context thiscontext;
    ArrayList<String> denemelist = new ArrayList<>();


        ListView liste;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thiscontext=getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.bitmis,container,false);

        mobileOSList.clear();
        liste = (ListView)view.findViewById(R.id.liste);



        // liste.setAdapter(customAdapter);
        new Macliste().execute();

        return  view;

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
                CustomAdapter customAdapter = new CustomAdapter(thiscontext,mobileOSList);
                liste.setAdapter(customAdapter);



            } catch (Exception e) {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.tuttur.com/live-score/completed-event-list";
            String jsonStr = sh.makeServiceCall(url);
            JSONObject firstObj;


            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray matchlist = jsonObject.getJSONArray("matches");


                for (int i = 0 ; i<matchlist.length();i++) {
                    firstObj = matchlist.getJSONObject(i);
                    if (firstObj.getString("type").equals("football")) {
                        homeTeamName = firstObj.getString("homeTeamName");
                        awayTeamName = firstObj.getString("awayTeamName");

                        results = firstObj.getJSONObject("officialResult");

                        score = results.getString("NormalTime");


                        score= score.replace("[","");
                        score= score.replace("]","");
                        score= score.replace(",",":");
                        mobileOSList.add(new MobileOS("MS", homeTeamName, score, awayTeamName,"bitmis"));

                    }


                }



            }catch (Exception e){


            }

            return null;



        }



    }





}
