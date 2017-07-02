package com.example.tzadmin.nfc_reader_writer.Fonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by velor on 7/2/17.
 */

public class SingletonFonts {

    private SingletonFonts(){}

    private static Typeface karlson;

    public Typeface getKarlson(){
        return karlson;
    }

    public static void SetKarlson(Typeface karlson){
        SingletonFonts.karlson = karlson;
    }

    private static volatile SingletonFonts instanse;

    public static SingletonFonts getInstanse(Context activity) {

        SingletonFonts localInstanse = instanse;

        if (localInstanse == null) {
            synchronized (SingletonFonts.class){
                localInstanse = instanse;
                if (localInstanse==null){
                    instanse = localInstanse = new SingletonFonts();
                }
            }
            //SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/8365.otf"));
            //SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/Candara.ttf"));
            SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/Candarab.ttf"));
            //SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/Candarai.ttf"));
            //SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/Candaraz.ttf"));
            //SetKarlson(Typeface.createFromAsset(activity.getAssets(), "fonts/karlson.ttf"));
        }
        return localInstanse;
    }
}
