package com.example.stepappv4.ui.Day;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.stepappv4.R;
import com.example.stepappv4.StepAppOpenHelper;
import com.example.stepappv4.databinding.FragmentDayBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DayFragment extends Fragment {

    private DayViewModel mViewModel;

    public static DayFragment newInstance() {
        return new DayFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public Cartesian createColumnChart(){
        //***** Read data from SQLiteDatabase *********/
        // TODO 1 (YOUR TURN): Get the map with hours and number of steps for today
        //  from the database and assign it to variable stepsByHour
        stepsByDay = StepAppOpenHelper.loadStepsByDay(this.getContext(), current_time);

        // TODO 2 (YOUR TURN): Creating a new map that contains hours of the day from 0 to 23 and
        //  number of steps during each hour set to 0

        // TODO 3 (YOUR TURN): Replace the number of steps for each hour in graph_map
        //  with the number of steps read from the database
        Map<String, Integer> graph_map = new TreeMap<>(stepsByDay);

        //***** Create column chart using AnyChart library *********/
        // TODO 4: Create and get the cartesian coordinate system for column chart
        Cartesian cartesian = AnyChart.column();

        // TODO 5: Create data entries for x and y axis of the graph
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<String,Integer> entry : graph_map.entrySet())
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));

        // TODO 6: Add the data to column chart and get the columns
        Column column = cartesian.column(data);

        //***** Modify the UI of the chart *********/
        // TODO 7 (YOUR TURN): Change the color of column chart and its border
        column.fill("#1EB980");
        column.stroke("#1EB980");

        // TODO 8: Modifying properties of tooltip
        column.tooltip()
                .titleFormat("On day: {%X}")
                .format("{%Value} Steps")
                .anchor(Anchor.RIGHT_BOTTOM);

        // TODO 9 (YOUR TURN): Modify column chart tooltip properties
        cartesian.tooltip().anchor(Anchor.RIGHT_TOP).offsetX(0d).offsetY(5d);

        // Modifying properties of cartesian
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.yScale().minimum(0);


        // TODO 10 (YOUR TURN): Modify the UI of the cartesian
        cartesian.background().fill("#111111");
        cartesian.xAxis(0).title("Day");
        cartesian.yAxis(0).title("No. of Steps");
        cartesian.animation(true);

        return cartesian;
    }

    public int todaySteps = 0;
    TextView numStepsTextView;
    AnyChartView anyChartView;

    Date cDate = new Date();
    String current_time = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

    public Map<String, Integer> stepsByDay = null;

    private FragmentDayBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Create column chart
        anyChartView = root.findViewById(R.id.dayBarChart);
        anyChartView.setProgressBar(root.findViewById(R.id.loadingBarChart));

        Cartesian cartesian = createColumnChart();
        anyChartView.setBackgroundColor("#00000000");
        anyChartView.setChart(cartesian);


        return root;
    }

}