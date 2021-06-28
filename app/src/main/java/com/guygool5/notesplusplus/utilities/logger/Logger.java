package com.guygool5.notesplusplus.utilities.logger;

import android.util.Log;

public class Logger {

    public static void log(LogType type, String ...msg){
        StringBuilder finalMessage= new StringBuilder();
        for(String i: msg){
            finalMessage.append(" ").append(i);
        }
        Log.d(type.toString(),finalMessage.toString());
    }
}
