package com.dogusumit.softkeys2;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        try {

            izinleriKontrolEt();


            final ScrollView homelayout = findViewById(R.id.homelayout);
            final ScrollView settinglayout = findViewById(R.id.settingslayout);
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            settinglayout.setVisibility(View.GONE);
                            homelayout.setVisibility(View.VISIBLE);
                            return true;
                        case R.id.navigation_settings:
                            homelayout.setVisibility(View.GONE);
                            settinglayout.setVisibility(View.VISIBLE);
                            return true;
                    }
                    return false;
                }
            });


            (findViewById(R.id.buton1)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            });
            (findViewById(R.id.buton2)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uygulamayiOyla();
                }
            });
            (findViewById(R.id.buton3)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marketiAc();
                }
            });


            final SeekBar seekbar1 = findViewById(R.id.seekbar1);
            final SeekBar seekbar2 = findViewById(R.id.seekbar2);
            final SeekBar seekbar3 = findViewById(R.id.seekbar3);
            final Spinner spinner1 = findViewById(R.id.spinner1);
            final Spinner spinner2 = findViewById(R.id.spinner2);
            final Spinner spinner3 = findViewById(R.id.spinner3);
            final Spinner spinner4 = findViewById(R.id.spinner4);
            final Spinner spinner5 = findViewById(R.id.spinner5);
            final Spinner spinner6 = findViewById(R.id.spinner6);
            final Spinner spinner7 = findViewById(R.id.spinner7);

            //seekbar1.setMax(100);
            //seekbar2.setMax(20);
            seekbar3.setMax(255);

            final SharedPreferences settings = getApplicationContext().getSharedPreferences("com.dogusumit.softkeys2", 0);
            final SharedPreferences.Editor editor = settings.edit();
            int genislik = settings.getInt("genislik", 0);
            int yukseklik = settings.getInt("yukseklik", 0);
            int seffaflik = settings.getInt("seffaflik", 0);
            int konum = settings.getInt("konum", 0);
            int ikon = settings.getInt("ikon", 0);
            int titresim = settings.getInt("titresim", 0);
            int uzunbas = settings.getInt("uzunbas", 0);
            int sol = settings.getInt("sol", 3);
            int orta = settings.getInt("orta", 1);
            int sag = settings.getInt("sag", 2);


            seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    editor.putInt("genislik", seekBar.getProgress()).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    editor.putInt("yukseklik", seekBar.getProgress()).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    editor.putInt("seffaflik", seekBar.getProgress()).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });


            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if ( position == 0 || position == 3 ) {
                        seekbar1.setMax(100);
                        seekbar2.setMax(20);
                    }
                    else if ( position == 1 || position == 2) {
                        seekbar1.setMax(20);
                        seekbar2.setMax(100);
                    }

                    editor.putInt("konum", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("ikon", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("titresim", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("uzunbas", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("sol", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("orta", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editor.putInt("sag", position).apply();
                    if (isAccessibilityEnabled() && izinleriKontrolEt())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            seekbar1.setProgress(genislik);
            seekbar2.setProgress(yukseklik);
            seekbar3.setProgress(seffaflik);
            spinner1.setSelection(konum);
            spinner2.setSelection(ikon);
            spinner3.setSelection(titresim);
            spinner4.setSelection(uzunbas);
            spinner5.setSelection(sol);
            spinner6.setSelection(orta);
            spinner7.setSelection(sag);


            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    private void uygulamayiOyla() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            } catch (Exception ane) {
                toastla(e.getLocalizedMessage());
            }
        }
    }

    private void marketiAc() {
        try {
            Uri uri = Uri.parse("market://developer?id=dogusumit");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=dogusumit")));
            } catch (Exception ane) {
                toastla(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.oyla:
                uygulamayiOyla();
                return true;
            case R.id.market:
                marketiAc();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void toastla(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    boolean isAccessibilityEnabled() {
        int accessibilityEnabled = 0;
        final String ACCESSIBILITY_SERVICE_NAME = getPackageName() + "/" + AccesService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            toastla(e.getLocalizedMessage());
        }

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {

            String settingValue = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    if (accessabilityService.equalsIgnoreCase(ACCESSIBILITY_SERVICE_NAME)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void servisGuncelle() {
        try {
            Intent intent = new Intent(getApplicationContext(), AccesService.class);
            intent.setAction("guncelle");
            startService(intent);
            stopService(intent);
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    boolean izinleriKontrolEt() {
        try {
            boolean rtrn = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    rtrn = false;
                } else {
                    rtrn = true;
                }
            }
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                    rtrn = false;
                } else {
                    rtrn = true;
                }
            }
            return rtrn;

        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
            return false;
        }
    }
}
