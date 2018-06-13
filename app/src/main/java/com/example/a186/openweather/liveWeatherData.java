package com.example.a186.openweather;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class liveWeatherData {
    private List<weatherResults> weatherResultsList = null;

    public static Observable<liveWeatherData> requestweatherdata() {
        return new internetService().getWeatherData()
                .subscribeOn(Schedulers.io());
    }



    public List<weatherResults> getResults() {
        return weatherResultsList;
    }
}
