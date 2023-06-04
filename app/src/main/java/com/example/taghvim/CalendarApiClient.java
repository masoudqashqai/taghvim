package com.example.taghvim;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalendarApiClient {

    public static final String API_URL = "https://api.keybit.ir/time/";

    public static void getDjalaliCalendarData(Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    // Parse the responseData and extract the Djalali calendar data
                    // Call the callback with the calendar data
                    callback.onSuccess(responseData);
                } else {
                    callback.onError("API request failed");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }
        });
    }

    public interface Callback {
        void onSuccess(String calendarData);
        void onError(String errorMessage);
    }
}
