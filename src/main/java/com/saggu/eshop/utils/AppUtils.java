package com.saggu.eshop.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class AppUtils {

    private static final String memoryFormat = "[%s] Memory - Max: %s, Total: %s, Free: %s\n";

    public static void printMemory(String prefix) {
        Runtime runtime = Runtime.getRuntime();
        System.out.printf(memoryFormat, prefix, inMB(runtime.maxMemory()), inMB(runtime.totalMemory()), inMB(runtime.freeMemory()));
    }

    public static String inMB(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + "B";
        }

        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f%cB", bytes / 1000.0, ci.current());
    }
}
