package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.gary.spiders.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class used to display charts displaying the user levels over time,
 * as well as the difficulty ratings a user gave each game type over time
 */
public class MetricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.myOverallProgress);
        progressBar.setMax(600);
        progressBar.setProgress(MainMenuActivity.user.getLevel());

        setupRatingsChart();
        setupUserProgressChart();
    }

    private void setupUserProgressChart() {
        BarChart chart = (BarChart) findViewById(R.id.userProgressChart);
        XAxis xAxis = chart.getXAxis();

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Double d = Double.valueOf(value*60000);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date(d.longValue() * 1000);
                return sdf.format(date);
            }
        });

        ArrayList<BarEntry> entries = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Progress", 0);

        // add a channel per activity ? Show line per type of exercise with the ratings for each
        Map<String, String> levels = (Map<String, String>) preferences.getAll();
        if (levels.size() > 0) {
            for (Map.Entry<String, String> level : levels.entrySet()) {

                // Each key in the shared preferences is an epoch timestamp, the with associated value then being the user level at that time
                String timestamp = level.getKey();
                String levelValue = level.getValue();

                entries.add(new BarEntry(Float.parseFloat(timestamp)/60000, Float.parseFloat(levelValue)));
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
            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setDrawValues(false);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            BarData data = new BarData(barDataSet);
            data.setBarWidth(0.4f);
            chart.setData(data);
            chart.setFitBars(true);
            chart.setDragEnabled(false);
            chart.getAxisLeft().setDrawGridLines(false);
            chart.getXAxis().setDrawGridLines(false);
            chart.invalidate(); // refresh
        }
    }

    private void setupRatingsChart() {
        LineChart chart = (LineChart) findViewById(R.id.ratingsChart);
        XAxis xAxis = chart.getXAxis();

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Double d = Double.valueOf(value);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
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
            dataSet.setDrawValues(false);
            dataSet.setDrawCircles(false);

            LineData lineData = new LineData(dataSet);
            lineData.setDrawValues(false);

            chart.setData(lineData);
            chart.setDragEnabled(false);

            chart.invalidate();
        }
    }
}
