package com.example.taphoaapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;


public class Util {
    Context context;



    public static ContextWrapper changeLang(Context context, String lang_code,float fontco){
        Locale sysLocale;

        Resources rs = context.getResources();
        Configuration config = rs.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }
        if ( config.fontScale != fontco)
            config.fontScale = fontco;
        if (!lang_code.equals("") && !sysLocale.getLanguage().equals(lang_code)) {
            Locale locale = new Locale(lang_code);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config);
            } else {
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
        }

        return new ContextWrapper(context);
    }




    public static Context adjustFontSize(Context context, float fontco){
        Configuration configuration = context.getResources().getConfiguration();
        // This will apply to all text like -> Your given text size * fontScale
        if ( configuration.fontScale != fontco)
            configuration.fontScale = fontco;//1.0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(configuration);
        } else {
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }

        return context; //context.createConfigurationContext(configuration);
    }


//    private static OkHttpClient httpClient;
//
//    // this method is used to fetch svg and load it into target imageview.
//    public static void fetchSvg(Context context, String url, final ImageView target) {
//        if (httpClient == null) {
//            httpClient = new OkHttpClient.Builder()
//                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
//                    .build();
//        }
//
//        // here we are making HTTP call to fetch data from URL.
//        Request request = new Request.Builder().url(url).build();
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // we are adding a default image if we gets any error.
//                target.setImageResource(R.drawable.temp);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                // sharp is a library which will load stream which we generated
//                // from url in our target imageview.
//                InputStream stream = response.body().byteStream();
//                Sharp.loadInputStream(stream).into(target);
//                stream.close();
//            }
//        });
//    }



}

