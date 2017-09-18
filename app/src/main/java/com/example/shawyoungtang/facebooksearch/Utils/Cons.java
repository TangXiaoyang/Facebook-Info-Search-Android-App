package com.example.shawyoungtang.facebooksearch.Utils;

/**
 * Created by ShawYoungTang on 4/11/2017.
 */

public class Cons {
    public static final String FAVORITES = "Favorites";
    public static final String NOID = "NOT_INSIDE";
    public static final String PHOTO_URL = "PHOTO_URL";
    public static final String NAME = "NAME";
    public static final String USER = "user";
    public static final String PAGE = "page";
    public static final String EVENT = "event";
    public static final String PLACE = "place";
    public static final String GROUP = "group";
    public static final String TIMESTAMP = "timestamp";

    public static String extractID(String unitID){
        int i = 0;
        while(!Character.isDigit(unitID.charAt(i)))
            i++;
        return unitID.substring(i);
    }
}
