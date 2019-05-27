package com.example.asus_pc.mobilproje;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_databasee";//database adı

    private static final String TABLE_NAME = "secili_maclar";
    private static String MAC_ID = "id";
    private static String MAC_DURUMU = "durum";
    private static String EV_SAHIBI = "ev_sahibi";
    private static String SKOR = "skor";
    private static String DEPLASMAN = "deplasman";
    private static String SONUC = "sonuc";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + MAC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MAC_DURUMU + " TEXT,"
                + EV_SAHIBI + " TEXT,"
                + SKOR + " TEXT,"
                + DEPLASMAN + " TEXT,"
                + SONUC + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    public void macSil(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, MAC_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void macEkle(String durum, String ev_sahibi,String skor,String deplasman,String sonuc) {
        //kitapEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAC_DURUMU, durum);
        values.put(EV_SAHIBI, ev_sahibi);
        values.put(SKOR, skor);
        values.put(DEPLASMAN, deplasman);
        values.put(SONUC, sonuc);

        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public HashMap<String, String> kitapDetay(int id){
        //Databeseden id si belli olan row u çekmek için.
        //Bu methodda sadece tek row değerleri alınır.
        //HashMap bir çift boyutlu arraydir.anahtar-değer ikililerini bir arada tutmak için tasarlanmıştır.
        //map.put("x","300"); mesala burda anahtar x değeri 300.

        HashMap<String,String> maclar = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            maclar.put(MAC_DURUMU, cursor.getString(1));
            maclar.put(EV_SAHIBI, cursor.getString(2));
            maclar.put(SKOR, cursor.getString(3));
            maclar.put(DEPLASMAN, cursor.getString(4));
            maclar.put(SONUC, cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return kitap
        return maclar;
    }

    public  ArrayList<HashMap<String, String>> maclar(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                kitaplist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return kitap liste
        return kitaplist;
    }

    public void macDuzenle(String kitap_adi, String kitap_yazari,String kitap_basim_yili,String kitap_fiyat,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(MAC_DURUMU, kitap_adi);
        values.put(EV_SAHIBI, kitap_yazari);
        values.put(SKOR, kitap_basim_yili);
        values.put(DEPLASMAN, kitap_fiyat);

        // updating row
        db.update(TABLE_NAME, values, MAC_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        //Login uygulamasında kullanacağız
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables(){
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {


    }

}