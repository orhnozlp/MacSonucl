package com.example.asus_pc.mobilproje;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
     Context context;
    Secili secili= new Secili();
    List<MobileOS> seciliMaclar = new ArrayList<>();
    List<MobileOS> mobileOSList = new ArrayList<>();
    LayoutInflater layoutInflater;
    public static CheckBox checkBox;


    public CustomAdapter(Context context, List<MobileOS> mobileOSList) {
        this.context=context;

        this.mobileOSList = mobileOSList;
    }


    @Override
    public int getCount() {
     return    mobileOSList.size();
    }

    @Override
    public Object getItem(int position) {
       return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.satir,null);
        TextView durum = (TextView)view.findViewById(R.id.durum);
        TextView evsahibi = (TextView)view.findViewById(R.id.evsahibi);
        TextView skor = (TextView)view.findViewById(R.id.skor);
        TextView deplasman = (TextView)view.findViewById(R.id.deplasman);
         final MobileOS mobileOS = mobileOSList.get(position);
        durum.setText(mobileOS.durum);
        evsahibi.setText(mobileOS.Evsahibi);
         checkBox = (CheckBox)view.findViewById(R.id.yildizli);

        skor.setText(mobileOS.Skor);
        deplasman.setText(mobileOS.getDeplasman());
        String sonuc = mobileOS.getSonuc();

        if(dbKontrol(mobileOS.getEvsahibi())){
            checkBox.setChecked(true);


        }
         if (sonuc.equals("devam")) {
            durum.setTextColor(Color.parseColor("#82ed36"));
            skor.setTextColor(Color.parseColor("#82ed36"));

        }
        else{

            durum.setTextColor(Color.parseColor("#B59E6C"));
            skor.setTextColor(Color.parseColor("#B59E6C"));

        }

        checkBox    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.db.macEkle(mobileOS.getDurum(),mobileOS.getEvsahibi(),mobileOS.getSkor(),mobileOS.getDeplasman(),mobileOS.getSonuc());

                }

                else {

                    int macid=Integer.parseInt(idGetir(mobileOS.getEvsahibi()));
                    MainActivity.db.macSil(macid);

                }

            }
        });


        return  view;
    }

    private String idGetir(String evsahibi){
        String macid="";
        String gelen_deger;
        ArrayList<HashMap<String,String>> mac_liste;
        mac_liste=MainActivity.db.maclar();
        for(int i=0;i<mac_liste.size();i++){
            gelen_deger=mac_liste.get(i).get("ev_sahibi");
            if(evsahibi.equals(gelen_deger)) macid=mac_liste.get(i).get("id");
        }

        return macid;
    }
    private  boolean dbKontrol(String evsahibi){
        boolean  sonuc=false;
        String gelenDeger;
        ArrayList<HashMap<String, String>> mac_liste;
        mac_liste=MainActivity.db.maclar();
        for(int i=0;i<mac_liste.size();i++){
            gelenDeger=mac_liste.get(i).get("ev_sahibi");
            if (evsahibi.equals(gelenDeger)) sonuc=true;

        }


        return sonuc;
    }



}
