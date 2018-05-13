package com.n26.project.util;

import java.util.concurrent.TimeUnit;

public class Constants {

    public static Long currentTimeInMins(){
      return TimeUnit.MINUTES.convert(System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    public static Long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

}
