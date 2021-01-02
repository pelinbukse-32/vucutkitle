package com.indeks.vucutkitle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {

    private EditText editText;
    private TextView boy_tv , kilo_tv , ideal_tv , durum_tv ;
    private SeekBar kilo_sb;
    private RadioGroup radioGroup;
    private boolean erkekmi = true;
    private double boy = 0.0 ;
    private int kilo = 50;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HwAds.init(this); //HMS ads servisini tetikledik.
        loadBannerAdd(); //yüklenirken banneri yükledik

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdId("testb4znbuh3n2");//video test id ekledik.
        interstitialAd.setAdListener(adListener);
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);

    }
    public void loadBannerAdd() {
        //ad parmeter objesini oluşturduk
        AdParam adParam = new AdParam.Builder().build();
        //banner view oluşturduk.
        BannerView bannerView = new BannerView(this);
        // Reklam ıd miz
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        // Add BannerView to the layout.
        //Reklamın gözükeceği layout eriştik.
        RelativeLayout rootView = findViewById(R.id.Bannerüst);
        rootView.addView(bannerView);

        bannerView.loadAd(adParam);
    } //Reklam kodları

    private final AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            Toast.makeText(MainActivity.this, "Reklam Yüklendi", Toast.LENGTH_SHORT).show();
            // Display an interstitial ad.
            showInterstitial();
        }

        @Override
        public void onAdFailed(int errorCode) {
            Toast.makeText(MainActivity.this, "Reklam yüklemesi hata koduyla başarısız oldu: " + errorCode,
                    Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Reklam yüklemesi hata koduyla başarısız oldu: " + errorCode);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            Log.d("TAG", "Kapanan Reklam");
        }

        @Override
        public void onAdClicked() {
            Log.d("TAG", "Tıklanan Reklam");
            super.onAdClicked();
        }

        @Override
        public void onAdOpened() {
            Log.d("TAG", "Açılan Reklam");
            super.onAdOpened();
        }
    };//Reklam kodları

    private void showInterstitial() {

        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Reklam Yüklenmedi", Toast.LENGTH_SHORT).show();
        }


        editText = (EditText) findViewById(R.id.editText);
        boy_tv = (TextView) findViewById(R.id.boy_tv);
        kilo_tv = (TextView) findViewById(R.id.kilo_tv);
        ideal_tv = (TextView) findViewById(R.id.ideal_tv);
        durum_tv= (TextView) findViewById(R.id.durum_tv);
        kilo_sb=(SeekBar) findViewById(R.id.kilo_sb);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        editText.addTextChangedListener(this);
        kilo_sb.setOnSeekBarChangeListener((this));
        radioGroup.setOnCheckedChangeListener(this);

        guncelle();


    }

    private void guncelle() {
        kilo_tv.setText(String.valueOf(kilo)+ " kg");
        boy_tv.setText(String.valueOf(boy));

        int ideal_kiloErkek= (int) (50+2.3*(boy*39.37-60));
        int ideal_kiloKadın= (int) (46+2.3*(boy*39.37-60));
        double vki = kilo/(boy*boy);

        if (erkekmi){
            ideal_tv.setText(String.valueOf(ideal_kiloErkek));

            if (vki <= 20.7 ){
                durum_tv.setText(R.string.Zayıf);
                durum_tv.setBackgroundResource(R.color.greenYellow);
            }else if (20.7 < vki && vki <= 26.4){
                durum_tv.setText(R.string.normal_kilo);
                durum_tv.setBackgroundResource(R.color.springGreen);
            }else if (26.4 < vki && vki <= 27.8) {
                durum_tv.setText(R.string.normalden_fazla);
                durum_tv.setBackgroundResource(R.color.sari);
            }else if (27.8 < vki && vki <= 31.1){
                durum_tv.setText(R.string.fazla_kilo);
                durum_tv.setBackgroundResource(R.color.darkOrange);
            }else if (31.1 < vki && vki <= 34.9){
                durum_tv.setText(R.string.obez);
                durum_tv.setBackgroundResource(R.color.orangeRed);
            }else if (34.9 < vki && vki <= 40){
                durum_tv.setText(R.string.obez2);
                durum_tv.setBackgroundResource(R.color.fireBrick);
            }else {
                durum_tv.setText(R.string.obez3);
                durum_tv.setBackgroundResource(R.color.darkRed);
            }

        }else {
            ideal_tv.setText(String.valueOf(ideal_kiloKadın));

            if (vki <= 19.1 ){
                durum_tv.setText(R.string.Zayıf);
                durum_tv.setBackgroundResource(R.color.greenYellow);
            }else if (19.1 < vki && vki <= 25.8){
                durum_tv.setText(R.string.normal_kilo);
                durum_tv.setBackgroundResource(R.color.springGreen);
            }else if (25.8 < vki && vki <= 27.3) {
                durum_tv.setText(R.string.normalden_fazla);
                durum_tv.setBackgroundResource(R.color.sari);
            }else if (27.3< vki && vki <= 32.3){
                durum_tv.setText(R.string.fazla_kilo);
                durum_tv.setBackgroundResource(R.color.darkOrange);
            }else if (32.3 < vki && vki <= 34.9){
                durum_tv.setText(R.string.obez);
                durum_tv.setBackgroundResource(R.color.orangeRed);
            }else if (34.9 < vki && vki <= 40){
                durum_tv.setText(R.string.obez2);
                durum_tv.setBackgroundResource(R.color.fireBrick);
            }else {
                durum_tv.setText(R.string.obez3);
                durum_tv.setBackgroundResource(R.color.darkRed);
            }
        }

    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        try {
            boy = Double.valueOf(s.toString())/100.0 ;

        }catch (NumberFormatException e ){
            boy = 0.0 ;
        }
        guncelle();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        kilo = 40+ progress;
        guncelle();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.erkek_radio)
            erkekmi=true;
        else if (checkedId==R.id.kadın_radio)
            erkekmi=false;

        guncelle();

    }
}