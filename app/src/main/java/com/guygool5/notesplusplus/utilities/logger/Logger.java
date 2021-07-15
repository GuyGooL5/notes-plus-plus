package com.guygool5.notesplusplus.utilities.logger;

import android.util.Log;

public class Logger {

    public static void log(LogType type, Object... msg){
        StringBuilder finalMessage= new StringBuilder();
        for(Object i: msg){
            try{
                finalMessage.append(" ").append(i.toString());
            }catch (NullPointerException e){
                finalMessage.append("[NULL] ");
            }catch (Exception e){
                finalMessage.append("[ERROR: ").append(e.toString()).append(" ] ");
            }
        }
        Log.d(type.toString(),finalMessage.toString());
    }
}
