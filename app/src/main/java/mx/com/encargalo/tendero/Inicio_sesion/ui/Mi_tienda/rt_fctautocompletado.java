package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;

import mx.com.encargalo.tendero.Util.Util;

public class rt_fctautocompletado {

    public static void rt_fctseleccionspinner(int i, Context context){
        SharedPreferences preferencias = context.getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        switch (i){
            case 0: editor.putString("latTemp", "19.356498");editor.putString("longTemp", "-99.236183"); editor.commit(); break;
            case 1: editor.putString("latTemp", "19.482778");editor.putString("longTemp", "-99.183333"); editor.commit(); break;
            case 2: editor.putString("latTemp", "19.398120");editor.putString("longTemp", "-99.157100"); editor.commit(); break;
            case 3: editor.putString("latTemp", "19.350000");editor.putString("longTemp", "-99.161667"); editor.commit(); break;
            case 4: editor.putString("latTemp", "19.374444");editor.putString("longTemp", "-99.284722"); editor.commit(); break;
            case 5: editor.putString("latTemp", "19.443056");editor.putString("longTemp", "-99.144722"); editor.commit(); break;
            case 6: editor.putString("latTemp", "19.482222");editor.putString("longTemp", "-99.112500"); editor.commit(); break;
            case 7: editor.putString("latTemp", "19.394918");editor.putString("longTemp", "-99.098568"); editor.commit(); break;
            case 8: editor.putString("latTemp", "19.358333");editor.putString("longTemp", "-99.093056"); editor.commit(); break;
            case 9: editor.putString("latTemp", "19.333333");editor.putString("longTemp", "-99.213889"); editor.commit(); break;
            case 10: editor.putString("latTemp", "19.406667");editor.putString("longTemp", "-99.191111"); editor.commit(); break;
            case 11: editor.putString("latTemp", "19.192222");editor.putString("longTemp", "-99.023056"); editor.commit(); break;
            case 12: editor.putString("latTemp", "19.274361");editor.putString("longTemp", "-99.002778"); editor.commit(); break;
            case 13: editor.putString("latTemp", "19.308333");editor.putString("longTemp", "-99.225000"); editor.commit(); break;
            case 14: editor.putString("latTemp", "19.416667");editor.putString("longTemp", "-99.113889"); editor.commit(); break;
            case 15: editor.putString("latTemp", "19.275000");editor.putString("longTemp", "-99.138889"); editor.commit(); break;
            default: editor.putString("latTemp", "19.356498");editor.putString("longTemp", "-99.236183"); editor.commit(); break;
        }
    }//+}@OHC08.11.2022

    public static void rt_fctautorrellenadoporcampo(String n_campo, TextInputEditText tiedtcampo, Context context){
        SharedPreferences preferencias = context.getSharedPreferences(Util.ARCHIVO_PREFRENCIAS, Context.MODE_PRIVATE);
        if (preferencias.contains(n_campo)){
            tiedtcampo.setText(preferencias.getString(n_campo, ""));
        }
    }//+}@OHC08.11.2022
}
