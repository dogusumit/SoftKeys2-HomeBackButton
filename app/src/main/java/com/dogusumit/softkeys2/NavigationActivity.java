package com.dogusumit.softkeys2;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;
import android.os.PowerManager;
import android.widget.CheckBox;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class NavigationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 101;

    ImageButton toolbarMenu;
    TextView toolbarTitle;
    CheckBox checkAccessibility, checkOverlay, checkNotifications, checkBattery, checkDnd;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        try {

            // Özel toolbar'ı ayarla
            toolbarTitle = findViewById(R.id.toolbar_title);
            toolbarMenu = findViewById(R.id.toolbar_menu);
            
            toolbarMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v);
                }
            });

            final ScrollView homelayout = findViewById(R.id.homelayout);
            final ScrollView settinglayout = findViewById(R.id.settingslayout);
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_home) {
                        settinglayout.setVisibility(View.GONE);
                        homelayout.setVisibility(View.VISIBLE);
                        return true;
                    } else if (itemId == R.id.navigation_settings) {
                        homelayout.setVisibility(View.GONE);
                        settinglayout.setVisibility(View.VISIBLE);
                        return true;
                    }
                    return false;
                }
            });


            checkAccessibility = findViewById(R.id.check_accessibility);
            checkOverlay = findViewById(R.id.check_overlay);
            checkNotifications = findViewById(R.id.check_notifications);
            checkBattery = findViewById(R.id.check_battery);
            checkDnd = findViewById(R.id.check_dnd);

            checkAccessibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAccessibilityDisclosure();
                }
            });
            checkOverlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        showPermissionExplanation(R.string.perm_overlay, R.string.desc_overlay, intent, null);
                    }
                }
            });
            checkNotifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        showPermissionExplanation(R.string.perm_notifications, R.string.desc_notifications, null, android.Manifest.permission.POST_NOTIFICATIONS);
                    }
                }
            });
            checkBattery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        showPermissionExplanation(R.string.perm_battery, R.string.desc_battery, intent, null);
                    }
                }
            });
            checkDnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        showPermissionExplanation(R.string.perm_dnd, R.string.desc_dnd, intent, null);
                    }
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

            seekbar1.setMax(100);
            seekbar2.setMax(100);
            seekbar3.setMax(255);

            final SharedPreferences settings = getApplicationContext().getSharedPreferences("com.dogusumit.softkeys2", 0);
            final SharedPreferences.Editor editor = settings.edit();
            if (settings.getInt("version", 1) != 2) {
                settings.edit().clear().apply();
                settings.edit().putInt("version", 2)
                        .putInt("genislik", 100)
                        .putInt("yukseklik", 25)
                        .putInt("seffaflik", 0)
                        .putInt("konum", 0)
                        .putInt("ikon", 1)
                        .putInt("titresim", 0)
                        .putInt("uzunbas", 0)
                        .putInt("sol", 3)
                        .putInt("orta", 1)
                        .putInt("sag", 2)
                        .apply();
            }
            int genislik = settings.getInt("genislik", 100);
            int yukseklik = settings.getInt("yukseklik", 25);
            int seffaflik = settings.getInt("seffaflik", 0);
            int konum = settings.getInt("konum", 0);
            int ikon = settings.getInt("ikon", 1);
            int titresim = settings.getInt("titresim", 0);
            int uzunbas = settings.getInt("uzunbas", 0);
            int sol = settings.getInt("sol", 3);
            int orta = settings.getInt("orta", 1);
            int sag = settings.getInt("sag", 2);


            seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    settings.edit().putInt("genislik", progress).apply();
                    if (isAccessibilityEnabled())
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
                    settings.edit().putInt("yukseklik", progress).apply();
                    if (isAccessibilityEnabled())
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
                    settings.edit().putInt("seffaflik", progress).apply();
                    if (isAccessibilityEnabled())
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
                    settings.edit().putInt("konum", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    settings.edit().putInt("ikon", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    settings.edit().putInt("titresim", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 1) {
                        if (Build.VERSION.SDK_INT >= 33) {
                            if (ContextCompat.checkSelfPermission(NavigationActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                                new AlertDialog.Builder(NavigationActivity.this)
                                        .setTitle(R.string.perm_notifications)
                                        .setMessage(R.string.desc_notifications)
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                                spinner4.setSelection(settings.getInt("uzunbas", 0));
                                return;
                            }
                        }
                    }
                    settings.edit().putInt("uzunbas", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    settings.edit().putInt("sol", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    settings.edit().putInt("orta", position).apply();
                    if (isAccessibilityEnabled())
                        servisGuncelle();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    settings.edit().putInt("sag", position).apply();
                    if (isAccessibilityEnabled())
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

            updatePermissionStatus();

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        updatePermissionStatus();
        if (isAccessibilityEnabled()) {
            servisGuncelle();
        }
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void updatePermissionStatus() {
        // Accessibility
        boolean accEnabled = isAccessibilityEnabled();
        checkAccessibility.setChecked(accEnabled);

        // Overlay
        boolean overlayEnabled = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            overlayEnabled = Settings.canDrawOverlays(this);
        }
        checkOverlay.setChecked(overlayEnabled);

        // Notifications
        if (Build.VERSION.SDK_INT >= 33) {
            checkNotifications.setVisibility(View.VISIBLE);
            boolean notifEnabled = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
            checkNotifications.setChecked(notifEnabled);
        } else {
            checkNotifications.setVisibility(View.GONE);
        }

        // Battery
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            boolean batteryIgnored = pm.isIgnoringBatteryOptimizations(getPackageName());
            checkBattery.setChecked(batteryIgnored);
        } else {
            checkBattery.setVisibility(View.GONE);
        }

        // DND / Notification Policy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            boolean dndEnabled = nm.isNotificationPolicyAccessGranted();
            checkDnd.setChecked(dndEnabled);
        } else {
            checkDnd.setVisibility(View.GONE);
        }
    }

    private void showPermissionExplanation(int titleRes, int messageRes, final Intent intent, final String permission) {
        new AlertDialog.Builder(this)
                .setTitle(titleRes)
                .setMessage(messageRes)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (intent != null) {
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                toastla(e.getLocalizedMessage());
                            }
                        } else if (permission != null) {
                            ActivityCompat.requestPermissions(NavigationActivity.this, new String[]{permission}, REQUEST_CODE_POST_NOTIFICATIONS);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updatePermissionStatus();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        updatePermissionStatus();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        updatePermissionStatus();
    }

    private void showAccessibilityDisclosure() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.accessibility_disclosure_title)
                .setMessage(R.string.accessibility_disclosure_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updatePermissionStatus();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        updatePermissionStatus();
                    }
                })
                .show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        
        popup.show();
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
        // Artık popup menü kullandığımız için bu metod gerekli değil
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_activate) {
            ((BottomNavigationView) findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_home);
            return true;
        } else if (itemId == R.id.menu_settings) {
            ((BottomNavigationView) findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_settings);
            return true;
        } else if (itemId == R.id.oyla) {
            uygulamayiOyla();
            return true;
        } else if (itemId == R.id.market) {
            marketiAc();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    void toastla(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    boolean isAccessibilityEnabled() {
        int accessibilityEnabled = 0;
        final String ACCESSIBILITY_SERVICE_NAME = String.format("%s/%s", 
                getPackageName(), 
                AccesService.class.getCanonicalName());
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
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    boolean izinleriKontrolEt() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    return false;
                }
            }

            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null && !notificationManager.isNotificationPolicyAccessGranted()) {
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}