package khara.karan.netuse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class ShowRateDiffChart extends ActionBarActivity {

    double mRateDiff;
    static double mUserNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rate_diff_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final XYSeries series = new XYSeries("Rating Difference");

        mUserNo =0;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
//        query.whereNotEqualTo();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for(ParseObject obj : list){
                    if(obj.get("rateDiff") != null){
                        mUserNo++;
                        mRateDiff = Float.valueOf(obj.get("rateDiff").toString());
                        Log.e("TAG", "diff>>>> " + mRateDiff);
                        series.add(mUserNo, mRateDiff);

                        //series.setTitle("HA HA HA HA HA");
//                        for(int i=0;i<x.length;i++){
//                            incomeSeries.add(x[i], income[i]);
//                            expenseSeries.add(x[i],expense[i]);
//                        }

                        // Creating a dataset to hold each series
                        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                        // Adding Series to the dataset
                        dataset.addSeries(series);

                        // Now we create the renderer
                        XYSeriesRenderer renderer = new XYSeriesRenderer();
                        renderer.setLineWidth(10);
                        renderer.setColor(Color.RED);
                        //renderer.setChartValuesSpacing();
                        // Include low and max value
                        renderer.setDisplayBoundingPoints(true);
                        // we add point markers
                        renderer.setPointStyle(PointStyle.CIRCLE);
                        renderer.setPointStrokeWidth(3);

                        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
                        mRenderer.addSeriesRenderer(renderer);
                        mRenderer.setChartTitle("SYSTEM-CENTRIC EVALUATION");
                        mRenderer.setXTitle("STUDENTS");
                        mRenderer.setYTitle("RATING-PREDICTION DIFFERENCE");
                        mRenderer.setYLabels(2);

                        /**** You can set the margins between the chart and the margins of the screen. ***/
                        mRenderer.setMargins(new int[] {40,60,100,10});
                        /****  The order of the values in the parameter array is: top, left, bottom, right. ****/
                        /**** So, you will need to increase the left value.****/


                        int getBlack =Color.BLACK;
                        int getBlue = Color.BLUE;
                        mRenderer.setXLabelsColor(getBlack);
                        mRenderer.setYLabelsColor(0, getBlack);
                        mRenderer.setLabelsColor(getBlack);
                        mRenderer.setMarginsColor(getBlue);
                        mRenderer.setAxesColor(getBlack);

                        mRenderer.setLegendTextSize(35);
                        mRenderer.setAxisTitleTextSize(42);
                        mRenderer.setPointSize(16);
                        mRenderer.setChartTitleTextSize(62);
                        mRenderer.setLabelsTextSize(32);
                        //mRenderer.setLegendTextSize(42);
                        mRenderer.setZoomButtonsVisible(true);

//                        mRenderer.addSeriesRenderer(mUserNo);
//                        mRenderer.addSeriesRenderer(mRateDiff);

                        // We want to avoid black border
                        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
                        // Disable Pan on two axis
                        mRenderer.setPanEnabled(false, false);
                        mRenderer.setYAxisMax(35);
                        mRenderer.setYAxisMin(0);
                        mRenderer.setShowGrid(true); // we show the grid

//                        GraphicalView chartView = ChartFactory.getLineChartView(getActivity(), dataset, mRenderer);
//
//                        chartLyt.addView(chartView,0);
                        // Getting a reference to LinearLayout of the MainActivity Layout
                        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_rating_diff);

                        // Creating a Line Chart
                        GraphicalView mChart = ChartFactory.getLineChartView(getBaseContext(), dataset, mRenderer);

                        // Adding the Line Chart to the LinearLayout
                        chartContainer.addView(mChart);

                    }
                }
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ShowRateDiffChart.this, FutureStudent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intent);
        //super.onBackPressed();
        //finish();
    }

}
