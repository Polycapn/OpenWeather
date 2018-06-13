package com.example.a186.openweather;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Backbone of all network Requests
 */
public class internetService {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "70ccd983b8857d2df83e8a5a3d95f75d";
    public NetworkService networkService;


    private OkHttpClient getAuthClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        Interceptor interceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        httpBuilder.addInterceptor(loggingInterceptor);
        httpBuilder.addInterceptor(interceptor);
    return httpBuilder.build();
    }


    public internetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getAuthClient()).build();
        networkService = retrofit.create(NetworkService.class);

}


    private interface NetworkService{
        @GET("forecast/{GetLocation}&{api_key}.json")
        Observable<liveWeatherData> getWeatherData(
                @Path("GetLocation") String location,
                @Path("api_key")String apiKey
        );

}
    public Observable<liveWeatherData> getWeatherData(){
        return networkService.getWeatherData(String.valueOf(R.string.location),API_KEY);
    }



}

