package com.example.graph;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GraphView graph = findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        int x1 = 20, y1 = 20;
        int x2 = 30, y2 = 30;
        int x3 = 50, y3 = 50;
        int x4 = 200, y4 = 200;
        try {
            LineGraphSeries <DataPoint> series = new LineGraphSeries< >(new DataPoint[] {
                    new DataPoint(0, 1),
                    new DataPoint(x1, y1),
                    new DataPoint(x2, y2),
                    new DataPoint(x3, y3),
                    new DataPoint(x4, y4)
            });

            // Scaling and scrolling
            //graph.getViewport().setScalable(true);
            //graph.getViewport().setScalableY(true);

            // Horizontal scrolling
            graph.getViewport().setScrollable(true);
            // Vertical scrolling
            graph.getViewport().setScrollableY(true);
            graph.addSeries(series);
        } catch (IllegalArgumentException e) {
            Log.i("Error", "Graph broke");
        }
    }
}
