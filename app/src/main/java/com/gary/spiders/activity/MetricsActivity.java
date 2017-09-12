package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gary.spiders.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MetricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);


        LineChart chart = (LineChart) findViewById(R.id.chart);
        XAxis xAxis = chart.getXAxis();

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Double d = Double.valueOf(value);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY HH:mm");
                Date date = new Date(d.longValue() * 1000);
                return sdf.format(date);
            }
        });

        List<Entry> entries = new ArrayList<Entry>();

        SharedPreferences preferences = getSharedPreferences("Ratings", 0);

        // add a channel per activity ? Show line per type of exercise with the ratings for each
        Map<String, String> ratings = (Map<String, String>) preferences.getAll();
        if(ratings.size() > 0) {
            for (Map.Entry<String, String> rating : ratings.entrySet()) {

                // Each key in the shared preferences is of the form 'classname_timestamp', the with associated rating then as the value
                // want to filter by each classname and then get the associated timestamps for all the values stored in the preferences
                String ratingKey = rating.getKey();
                String[] compositeKey = ratingKey.split("_");
                String exercise = compositeKey[0];
                String timestamp = compositeKey[1];

                String ratingValue = rating.getValue();
                entries.add(new Entry(Float.parseFloat(timestamp), Float.parseFloat(ratingValue)));
            }

            // Sort based on the timestamp (x-value) earliest timestamp to latest
            Collections.sort(entries, new Comparator<Entry>() {
                @Override
                public int compare(Entry lhs, Entry rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.getX() < rhs.getX() ? -1 : (lhs.getX() > rhs.getX()) ? 1 : 0;
                }
            });

            // add entries to dataset
            LineDataSet dataSet = new LineDataSet(entries, "Ratings");
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate(); // refresh
        }
    }
}
