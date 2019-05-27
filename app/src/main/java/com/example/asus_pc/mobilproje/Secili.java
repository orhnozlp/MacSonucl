package com.example.asus_pc.mobilproje;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Secili extends Fragment {
 //   ListView liste;

    ListView listView;
    public static List<MobileOS> seciliListe = new ArrayList<>();
    public static List<MobileOS> tempList = new ArrayList<>();
    Context thiscontext;
    EditText editText;
    ArrayList<HashMap<String, String>> mac_liste;
    //private String[] sutunlar = {"durum","ev_sahibi","skor","deplasman","SONUC"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        thiscontext=getActivity().getApplicationContext();
        final View view = inflater.inflate(R.layout.secili,container,false);
        listView=(ListView)view.findViewById(R.id.seciliMaclar);
        editText=(EditText)view.findViewById(R.id.editText);


        seciliListe.clear();
        new SeciliMaclar().execute();
       /* CustomAdapter.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    Secili fg = new Secili();
                    fg.setArguments(savedInstanceState);

                    getFragmentManager()  // or getSupportFragmentManager() if your fragment is part of support library
                            .beginTransaction()
                            .replace(R.layout.secili, fg)
                            .commit();
                }

            }
        });*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s=s.toString().toUpperCase();
                tempList.clear();

                String mac_durum, ev_sahibi, skor, deplasman, sonuc;
                for (int i =0;i<mac_liste.size();i++) {
                    mac_durum = mac_liste.get(i).get("durum");
                    ev_sahibi = mac_liste.get(i).get("ev_sahibi");
                    skor = mac_liste.get(i).get("skor");
                    deplasman = mac_liste.get(i).get("deplasman");
                    sonuc = mac_liste.get(i).get("sonuc");
                    if(ev_sahibi.toUpperCase().contains(s)||deplasman.toUpperCase().contains(s)){
                        tempList.add(new MobileOS(mac_durum,ev_sahibi,skor,deplasman,sonuc));

                    }

                }
                if (tempList!=null&&tempList.size()>1){
                    CustomAdapter customAdapter=new CustomAdapter(thiscontext,tempList);
                    listView.setAdapter(customAdapter);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return  view;


    }


    private  class SeciliMaclar extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            CustomAdapter customAdapter = new CustomAdapter(thiscontext,seciliListe);

            listView.setAdapter(customAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mac_liste = MainActivity.db.maclar();

            String mac_durum, ev_sahibi, skor, deplasman, sonuc;

            for (int i = 0; i < mac_liste.size(); i++) {
                mac_durum = mac_liste.get(i).get("durum");
                ev_sahibi = mac_liste.get(i).get("ev_sahibi");
                skor = mac_liste.get(i).get("skor");
                deplasman = mac_liste.get(i).get("deplasman");
                sonuc = mac_liste.get(i).get("sonuc");
                seciliListe.add(new MobileOS(mac_durum, ev_sahibi, skor, deplasman, sonuc));

            }
            return  null;
    }

    }




}
