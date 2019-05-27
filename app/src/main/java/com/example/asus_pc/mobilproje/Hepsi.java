package com.example.asus_pc.mobilproje;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.kd.dynamic.calendar.generator.ImageGenerator;

import org.json.JSONObject;
import  org.json.JSONArray;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;

public class Hepsi extends Fragment  {
    public static List<MobileOS> mobileOSList = new ArrayList<>();
    Context thiscontext;
    ArrayList<String> denemelist = new ArrayList<>();
    public static CheckBox secili;
    public static  ListView hepsi;
    Secili seciliAkti;
    Bitmap mGeneratedIcon;
    public  static int saat;
    Calendar mCurrentDate;
    int sayac=0;
    public    String seciliEpoch;
    public static String currentEpoch;
    static  long startDate,endDate;
    Handler handler;
    ImageGenerator mImageGenerator;
    MainActivity mainActivity=new MainActivity();

    ArrayList<HashMap<String, String>> mac_liste;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     thiscontext=getActivity().getApplicationContext();


        View view = inflater.inflate(R.layout.hepsi,container,false);
       //TextView textView = (TextView)view.findViewById(R.id.textView);
      mobileOSList.clear();
     //   liveList=(ListView)view.findViewById(R.id.item1);
    //  liste = (ListView)view.findViewById(R.id.item);
        hepsi=(ListView)view.findViewById(R.id.hepsi);

       Takvim();
        mobileOSList.clear();
        new CanliListe().execute();
        new Macliste().execute();

        //text = (TextView)view.findViewById(R.id.textviews);
       // liste.setAdapter(customAdapter);
         handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {

                sayac++;
                if(sayac==5) {
                    mobileOSList.clear();
                    new CanliListe().execute();
                    new Macliste().execute();
                    sayac=0;

                }
                handler.postDelayed(this,5000);
            }
        };
        handler.post(run);

        System.out.println("secili tarih"+mainActivity.seciliEpoch());
        MainActivity.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takvimAc();
            }
        });
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
               hepsi.setAdapter(customAdapter);





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
                            mobileOSList.add(new MobileOS("MS", homeTeamName, score, awayTeamName,"bitti"));

                        }
                    }

            }catch (Exception e){

            }
            return null;
            }
    }
    private  class CanliListe extends  AsyncTask<Void,Void,Void>{
        String evsahibi;
        String result = "";
        String homeTeamName,awayTeamName,score;
        JSONObject results;
        @Override
        protected void onPostExecute(Void aVoid) {
            try {

                // tüm bilgiler listeye atıldı
               // CustomAdapter customAdapter = new CustomAdapter(thiscontext,mobileOSList,"canli");
           //    liveList.setAdapter(customAdapter);
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
                        else  dakika+="'";
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

    private  void  Takvim(){

        // Create an object of ImageGenerator class in your activity
// and pass the context as the parameter
        mImageGenerator = new ImageGenerator(thiscontext);

// Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 50);

// Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

// Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

// Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#f2f4f7"));
        mImageGenerator.setMonthColor(Color.WHITE);
        mImageGenerator.setStorageToSDCard(true);
        mCurrentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        int year = mCurrentDate.get(Calendar.YEAR);
        int month=mCurrentDate.get(Calendar.MONTH);
        int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mCurrentDate.set(year,month,day);
        mGeneratedIcon=mImageGenerator.generateDateImage(mCurrentDate, R.drawable.tarih);
        MainActivity.imageView.setImageBitmap(mGeneratedIcon);
        MainActivity.imageView.setMaxWidth(10);

    }
    private  void takvimAc(){


        mCurrentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        int year = mCurrentDate.get(Calendar.YEAR);
        int month=mCurrentDate.get(Calendar.MONTH);
        int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        saat=mCurrentDate.get(Calendar.HOUR_OF_DAY);
        System.out.println("saat:"+saat);
        DatePickerDialog datePicker;
        datePicker =  new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                mCurrentDate.set(year,monthOfYear,dayOfMonth);
                long epoch=mCurrentDate.getTimeInMillis()/1000;
                seciliEpoch=""+epoch;


                mGeneratedIcon=mImageGenerator.generateDateImage(mCurrentDate, R.drawable.tarih);
                MainActivity.imageView.setImageBitmap(mGeneratedIcon);
                Calistir(seciliEpoch);
            }
        },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
        datePicker.setTitle("Tarih Seçiniz");

        datePicker.show();


    }

  private  void Calistir(String seciliEpoch){
      int gunsaat=saat+3;

      // System.out.println("tarih epoc"+currentEpoch);
      long hourofepoch=gunsaat*3600;
      System.out.println("currentepco"+seciliEpoch);
      startDate=Long.parseLong(seciliEpoch)-hourofepoch;
      System.out.println("Start"+startDate);
      System.out.println("Suan"+System.currentTimeMillis()/1000);
      long secilenEpc =Long.parseLong(seciliEpoch);
      long sistemEpc =System.currentTimeMillis()/1000;
      endDate=startDate+86400;
      System.out.println(secilenEpc-sistemEpc);
      System.out.println("endTie"+endDate);
      long fark=secilenEpc-sistemEpc;
      if(-fark<1000) {

          mobileOSList.clear();
          new CanliListe().execute();
          new Macliste().execute();
      }
      if(startDate>System.currentTimeMillis()/1000) {
          System.out.println("ileri tarih");
      }


      else {
          mobileOSList.clear();
          new GecmisMaclar().execute();
      }


  }


    private  class GecmisMaclar extends  AsyncTask<Void,Void,Void>{

        String evsahibi;
        String result = "";
        String homeTeamName,awayTeamName,score;
        JSONObject results;



        @Override
        protected void onPostExecute(Void aVoid) {

            try {

                // tüm bilgiler listeye atıldı
                CustomAdapter customAdapter = new CustomAdapter(thiscontext,mobileOSList);
                hepsi.setAdapter(customAdapter);




            } catch (Exception e) {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            System.out.println(startDate);
            System.out.println(endDate);
            String url = "https://www.tuttur.com/live-score/completed-event-list?startDate="+startDate+"&endDate="+endDate;
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
                        mobileOSList.add(new MobileOS("MS", homeTeamName, score, awayTeamName,"bitti"));

                    }
                }

            }catch (Exception e){

            }
            return null;
        }
    }

}
