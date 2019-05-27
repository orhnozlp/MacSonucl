package com.example.asus_pc.mobilproje;

import android.widget.CheckBox;

public class MobileOS {
    String sonuc,durum,Evsahibi,Skor,Deplasman;

    private boolean checked = false ;
    public MobileOS(String durum, String evsahibi, String skor, String deplasman,String sonuc ) {
        this.sonuc=sonuc;
        this.durum = durum;
        this.Evsahibi = evsahibi;
        this.Skor = skor;
        this.Deplasman = deplasman;


    }

    public  boolean getCheck() {


        return  checked;
    }
    public  String getSonuc() {return  sonuc;}
    public String getDurum() {
        return durum;
    }

    public String getEvsahibi() {
        return Evsahibi;
    }

    public String getSkor() {
        return Skor;
    }

    public String getDeplasman() {
        return Deplasman;
    }
}
