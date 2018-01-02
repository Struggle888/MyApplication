package com.example.administrator.demo1;

/**
 * Created by Administrator on 2017/12/23.
 */

public class NdkJniUtils {

    static {
        System.loadLibrary("Scanner");
    }
    public native String getCLanguageString();
}
