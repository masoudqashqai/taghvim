package com.example.taghvim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarApiClient.getDjalaliCalendarData(new CalendarApiClient.Callback() {
                    @Override
                    public void onSuccess(final String calendarData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(calendarData);

                                    // Access nested keys
                                    JSONObject dateObject = jsonObject.getJSONObject("date");
                                    JSONObject fullObject = dateObject.getJSONObject("full");
                                    JSONObject officialObject = fullObject.getJSONObject("official");
                                    JSONObject isoObject = officialObject.getJSONObject("iso");
                                    String faDate = isoObject.getString("fa");


                                    String persianDate = faDate;
                                    textView.setText(persianDate);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    textView.setText("Error parsing data");
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final String errorMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Error fetching data");
                            }
                        });
                    }
                });
            }
        });
    }
}
