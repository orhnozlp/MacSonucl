package com.example.asus_pc.mobilproje;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    private Toolbar toolbar;
    ViewPager viewPager;
    ListView liste ;
   public static ImageView imageView;
    Bitmap mGeneratedIcon;
    public  static int saat;
    Calendar mCurrentDate;
   public static String currentEpoch;
    ImageGenerator mImageGenerator;
   public static Database db;
    List<MobileOS> mobileOSList = new ArrayList<>();

    PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("başdı");
        db = new Database(this);
      String languageToLoad  = "tr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);
        TimeZone tz  = TimeZone.getDefault() ;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        imageView=(ImageView)findViewById(R.id.imageView2);
        //  Takvim();

      /*  imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takvimAc();
            }
        });
*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        setSupportActionBar(toolbar);

        // liste = (ListView)findViewById(R.id.item);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.container);

        SetupPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
       setupTabIcons();
     android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setLogo(R.drawable.logo2);
        actionBar.setIcon(R.drawable.ball);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
    }

    private void setupTabIcons() {

        LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        LinearLayout tabLinearLayout1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        LinearLayout tabLinearLayout2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        LinearLayout tabLinearLayout3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView tabContent = (TextView) tabLinearLayout.findViewById(R.id.tabContent);
        TextView tabContent1 = (TextView) tabLinearLayout1.findViewById(R.id.tabContent);
        TextView tabContent2 = (TextView) tabLinearLayout2.findViewById(R.id.tabContent);
        TextView tabContent3 = (TextView) tabLinearLayout3.findViewById(R.id.tabContent);
        tabContent.setText(" HEPSİ");
        tabContent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hepsi1, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabContent);

        tabContent1.setText("CANLI");
        tabContent1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.canli1, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabContent1);

        tabContent2.setText("BİTMİŞ");
        tabContent2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bitmis1, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabContent2);

        tabContent3.setText("SEÇİLİ");
        tabContent3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabContent3);





    }


    private  void  SetupPager (ViewPager viewPager) {

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Hepsi(),"HEPSİ");
        adapter.AddFragment(new Canli(),"CANLI");
        adapter.AddFragment(new Bitmis(),"BİTMİŞ");
        adapter.AddFragment(new Secili(),"SEÇİLİ");
        viewPager.setAdapter(adapter);

    }

    private  void  Takvim(){

        // Create an object of ImageGenerator class in your activity
// and pass the context as the parameter
        mImageGenerator = new ImageGenerator(this);
        imageView=(ImageView) findViewById(R.id.imageView2);
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
        imageView.setImageBitmap(mGeneratedIcon);
        imageView.setMaxWidth(10);

    }
  private  void takvimAc(){


            mCurrentDate = Calendar.getInstance();

            int year = mCurrentDate.get(Calendar.YEAR);
            int month=mCurrentDate.get(Calendar.MONTH);
            int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
            saat=mCurrentDate.get(Calendar.HOUR_OF_DAY);

            DatePickerDialog datePicker;//Datepicker objemiz
            datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub

                    mCurrentDate.set(year,monthOfYear,dayOfMonth);
                    mGeneratedIcon=mImageGenerator.generateDateImage(mCurrentDate, R.drawable.tarih);
                    imageView.setImageBitmap(mGeneratedIcon);
                    long epoch=mCurrentDate.getTimeInMillis()/1000;
                    currentEpoch=""+epoch;
                    Calistir(currentEpoch);


                }
            },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz

      datePicker.setTitle("Tarih Seçiniz");


        datePicker.show();
        System.out.println("çalışma bitt");

    }

    public  long  seciliEpoch(){
        int gunsaat=saat+3;
        long epoch=0;
        long hourofepoch=gunsaat*86400;


        return epoch;
    }
    private void Calistir(String currentEpoch){
        int gunsaat=saat+3;

       // System.out.println("tarih epoc"+currentEpoch);
        long hourofepoch=gunsaat*3600;
        System.out.println("currentepco"+currentEpoch);
        long epoch=Long.parseLong(currentEpoch)-hourofepoch;

        System.out.println("epoch ="+epoch);
    }
}
