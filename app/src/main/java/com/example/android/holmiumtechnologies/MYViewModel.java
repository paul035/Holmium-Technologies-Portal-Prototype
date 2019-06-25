package com.example.android.holmiumtechnologies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MYViewModel extends ViewModel {

    private MutableLiveData<String> stringMutableLiveData1;
    private MutableLiveData<String> stringMutableLiveData2;
    private MutableLiveData<String> stringMutableLiveData3;
    private MutableLiveData<String> stringMutableLiveData4;

    public void init(){
        stringMutableLiveData1 = new MutableLiveData <>();
        stringMutableLiveData2 = new MutableLiveData<>();
        stringMutableLiveData3 = new MutableLiveData<>();
        stringMutableLiveData4 = new MutableLiveData<>();
    }

    public LiveData<String> getData(){
        return stringMutableLiveData1;
    }

    public void sendData1(String data1){
        stringMutableLiveData1.setValue(data1);
    }

    public void sendData2(String data2){
        stringMutableLiveData2.setValue(data2);
    }

    public void sendData3(String data3){
        stringMutableLiveData3.setValue(data3);
    }

    public void sendData4(String data4){
        stringMutableLiveData4.setValue(data4);
    }

    public LiveData<String> getData1(){return stringMutableLiveData1;}
    public LiveData<String> getData2(){return stringMutableLiveData2;}
    public LiveData<String> getData3(){return stringMutableLiveData3;}
    public LiveData<String> getData4(){return stringMutableLiveData4;}

}
