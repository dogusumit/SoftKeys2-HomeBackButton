package com.dogusumit.softkeys2;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class AccesService extends AccessibilityService {

    private final static int ATOMIC_ID = new AtomicInteger(0).incrementAndGet();
    WindowManager windowManager;
    LinearLayout linearLayout;
    ImageButton back, home, recent;
    WindowManager.LayoutParams params;
    boolean isEnabled = false;
    boolean isAdded = false;
    boolean isVibrationON = false;
    SharedPreferences settings;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String s = intent.getAction();
            if (s != null && s.equals("guncelle") && isEnabled)
                konumAyarla();
        } catch (Exception e) {
            Log.e("onStartCommand : ", e.getLocalizedMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onServiceConnected() {
        try {

            isEnabled = true;
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            linearLayout = (LinearLayout) inflater.inflate(R.layout.servis_layout, null);
            settings = getApplicationContext().getSharedPreferences("com.dogusumit.softkeys2", 0);

            if (settings.getInt("version", 1) != 2) {
                settings.edit().clear().apply();
                settings.edit().putInt("version", 2).apply();
            }

            back = linearLayout.findViewById(R.id.back);
            home = linearLayout.findViewById(R.id.home);
            recent = linearLayout.findViewById(R.id.recent);


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                        if (isVibrationON)
                            titrestir();
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                        if (isVibrationON)
                            titrestir();
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });
            recent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
                        if (isVibrationON)
                            titrestir();
                    } catch (Exception e) {
                        toastla(e.getLocalizedMessage());
                    }
                }
            });


            konumAyarla();

        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
        super.onServiceConnected();
    }

    void toastla(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    void konumAyarla() {
        try {

            if (isAdded)
                windowManager.removeViewImmediate(linearLayout);


            double telefon_genislik = Resources.getSystem().getDisplayMetrics().widthPixels / 100.0;
            double telefon_yukseklik = Resources.getSystem().getDisplayMetrics().heightPixels / 100.0;

            final int genislik = settings.getInt("genislik", 100);
            int yukseklik = settings.getInt("yukseklik", 25);
            final int seffaflik = settings.getInt("seffaflik", 0);
            int konum = settings.getInt("konum", 0);
            int ikon = settings.getInt("ikon", 0);
            int titresim = settings.getInt("titresim", 0);
            int uzunbas = settings.getInt("uzunbas", 0);
            int sol = settings.getInt("sol", 3);
            int orta = settings.getInt("orta", 1);
            int sag = settings.getInt("sag", 2);


            isVibrationON = (titresim == 1);


            switch (ikon) {
                case 0:
                    back.setImageResource(R.mipmap.ic_back1);
                    home.setImageResource(R.mipmap.ic_home1);
                    recent.setImageResource(R.mipmap.ic_recent1);
                    break;
                case 1:
                    back.setImageResource(R.mipmap.ic_back2);
                    home.setImageResource(R.mipmap.ic_home2);
                    recent.setImageResource(R.mipmap.ic_recent2);
                    break;
                case 2:
                    back.setImageResource(R.mipmap.ic_back3);
                    home.setImageResource(R.mipmap.ic_home3);
                    recent.setImageResource(R.mipmap.ic_recent3);
                    break;
                case 3:
                    back.setImageResource(R.mipmap.ic_back4);
                    home.setImageResource(R.mipmap.ic_home4);
                    recent.setImageResource(R.mipmap.ic_recent4);
                    break;
                case 4:
                    back.setImageResource(R.mipmap.ic_back5);
                    home.setImageResource(R.mipmap.ic_home5);
                    recent.setImageResource(R.mipmap.ic_recent5);
                    break;
                case 5:
                    back.setImageResource(R.mipmap.ic_back6);
                    home.setImageResource(R.mipmap.ic_home6);
                    recent.setImageResource(R.mipmap.ic_recent6);
                    break;
            }


            ImageButton btn_sol = null, btn_orta = null, btn_sag = null;
            switch (sol) {
                case 0:
                    btn_sol = null;
                    break;
                case 1:
                    btn_sol = home;
                    break;
                case 2:
                    btn_sol = back;
                    break;
                case 3:
                    btn_sol = recent;
                    break;
            }
            switch (orta) {
                case 0:
                    btn_orta = null;
                    break;
                case 1:
                    if (btn_sol != home)
                        btn_orta = home;
                    else
                        btn_orta = null;
                    break;
                case 2:
                    if (btn_sol != back)
                        btn_orta = back;
                    else
                        btn_orta = null;
                    break;
                case 3:
                    if (btn_sol != recent)
                        btn_orta = recent;
                    else
                        btn_orta = null;
                    break;
            }
            switch (sag) {
                case 0:
                    btn_sag = null;
                    break;
                case 1:
                    if (btn_sol != home && btn_orta != home)
                        btn_sag = home;
                    else
                        btn_sag = null;
                    break;
                case 2:
                    if (btn_sol != back && btn_orta != back)
                        btn_sag = back;
                    else
                        btn_sag = null;
                    break;
                case 3:
                    if (btn_sol != recent && btn_orta != recent)
                        btn_sag = recent;
                    else
                        btn_sag = null;
                    break;
            }


            switch (uzunbas) {
                case 0:
                    if (btn_sol != null)
                        btn_sol.setOnLongClickListener(null);
                    if (btn_orta != null)
                        btn_orta.setOnLongClickListener(null);
                    if (btn_sag != null)
                        btn_sag.setOnLongClickListener(null);
                    break;
                case 1:
                    if (btn_sol != null)
                        btn_sol.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                gizle();
                                return true;
                            }
                        });
                    if (btn_orta != null)
                        btn_orta.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                gizle();
                                return true;
                            }
                        });
                    if (btn_sag != null)
                        btn_sag.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                gizle();
                                return true;
                            }
                        });
                    break;
                case 2:
                    if (btn_sol != null)
                        btn_sol.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                SharedPreferences.Editor editor = settings.edit();
                                int tmp_knm = settings.getInt("konum", 0);
                                if (tmp_knm == 0 || tmp_knm == 3) {
                                    int tmp_gen = settings.getInt("genislik",100);
                                    int tmp_yuk = settings.getInt("yukseklik",25);
                                    editor.putInt("genislik",tmp_yuk).apply();
                                    editor.putInt("yukseklik",tmp_gen).apply();
                                }
                                editor.putInt("konum", 2).apply();
                                konumAyarla();
                                return true;
                            }
                        });
                    if (btn_orta != null)
                        btn_orta.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                SharedPreferences.Editor editor = settings.edit();
                                int tmp_knm = settings.getInt("konum", 0);
                                if (tmp_knm == 0)
                                    editor.putInt("konum", 3).apply();
                                else if (tmp_knm == 3)
                                    editor.putInt("konum", 0).apply();
                                else {
                                    int tmp_gen = settings.getInt("genislik",100);
                                    int tmp_yuk = settings.getInt("yukseklik",25);
                                    editor.putInt("genislik",tmp_yuk).apply();
                                    editor.putInt("yukseklik",tmp_gen).apply();
                                    editor.putInt("konum", 0).apply();
                                }
                                konumAyarla();
                                return true;
                            }
                        });
                    if (btn_sag != null)
                        btn_sag.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                SharedPreferences.Editor editor = settings.edit();
                                int tmp_knm = settings.getInt("konum", 0);
                                if (tmp_knm == 0 || tmp_knm == 3) {
                                    int tmp_gen = settings.getInt("genislik",100);
                                    int tmp_yuk = settings.getInt("yukseklik",25);
                                    editor.putInt("genislik",tmp_yuk).apply();
                                    editor.putInt("yukseklik",tmp_gen).apply();
                                }
                                editor.putInt("konum", 1).apply();
                                konumAyarla();
                                return true;
                            }
                        });
                    break;
            }


            linearLayout.removeAllViews();
            if (btn_sol != null)
                linearLayout.addView(btn_sol);
            if (btn_orta != null)
                linearLayout.addView(btn_orta);
            if (btn_sag != null)
                linearLayout.addView(btn_sag);


            int layout_type = WindowManager.LayoutParams.TYPE_PHONE;
            if (Build.VERSION.SDK_INT >= 22)
                layout_type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
            switch (konum) {
                case 0:
                    params = new WindowManager.LayoutParams(
                            (int) (genislik * telefon_genislik),
                            (int) (yukseklik * telefon_yukseklik / 5.0), layout_type,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT);
                    params.gravity = Gravity.BOTTOM;
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 1:
                    params = new WindowManager.LayoutParams(
                            (int) (genislik * telefon_genislik / 5.0),
                            (int) (yukseklik * telefon_yukseklik), layout_type,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT);
                    params.gravity = Gravity.END;
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    break;
                case 2:
                    params = new WindowManager.LayoutParams(
                            (int) (genislik * telefon_genislik / 5.0),
                            (int) (yukseklik * telefon_yukseklik), layout_type,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT);
                    params.gravity = Gravity.START;
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    break;
                case 3:
                    params = new WindowManager.LayoutParams(
                            (int) (genislik * telefon_genislik),
                            (int) (yukseklik * telefon_yukseklik / 5.0), layout_type,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT);
                    params.gravity = Gravity.TOP;
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    break;
            }

            linearLayout.getBackground().setAlpha(255 - seffaflik);
            windowManager.addView(linearLayout, params);
            isAdded = true;

        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    void gizle() {
        try {
            if (isAdded)
                windowManager.removeViewImmediate(linearLayout);
            isAdded = false;
            Intent notificationIntent = new Intent(this, AccesService.class);
            notificationIntent.setAction("guncelle");
            PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String CHANNEL_ID = getString(R.string.app_name);
                Notification notification = new Notification.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.str14))
                        .setContentIntent(pendingIntent).build();
                notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_AUTO_CANCEL;
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(ATOMIC_ID, notification);
                CharSequence name = getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
            } else {
                Notification notification = new NotificationCompat.Builder(this, getString(R.string.app_name))
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.str14))
                        .setContentIntent(pendingIntent).build();
                notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_AUTO_CANCEL;
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(ATOMIC_ID, notification);
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    void titrestir() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (vibrator != null)
                    vibrator.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (vibrator != null)
                    vibrator.vibrate(70);
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }


    @Override
    public void onInterrupt() {
        try {
            if (isAdded) {
                windowManager.removeViewImmediate(linearLayout);
                isAdded = false;
                isEnabled = false;
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (isAdded) {
                windowManager.removeViewImmediate(linearLayout);
                isAdded = false;
                isEnabled = false;
            }
        } catch (Exception e) {
            toastla(e.getLocalizedMessage());
        }
        super.onDestroy();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        konumAyarla();
    }
}