package com.example.mugsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlotFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private  GraphView graphView;
    private Spinner spinner;
    private HashMap<String , Integer > map;
    private int  dayInMonth = 31;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlotFragment newInstance(String param1, String param2) {
        PlotFragment fragment = new PlotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        DataPoint[] dataPoints = new DataPoint[31];
        for(int i=0; i<31; i++) {
            dataPoints[i] = new DataPoint(i+1 , Math.sin((double)i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plot, container, false);
        map = new HashMap<>();
        map.put(getString(R.string.january) , 31);
        map.put(getString(R.string.february) , 28);
        map.put(getString(R.string.march) , 31);
        map.put(getString(R.string.april) , 30);
        map.put(getString(R.string.may) , 31);
        map.put(getString(R.string.june) , 30);
        map.put(getString(R.string.july) , 31);
        map.put(getString(R.string.august) , 31);
        map.put(getString(R.string.septemer) , 30);
        map.put(getString(R.string.october) , 31);
        map.put(getString(R.string.november) , 30);
        map.put(getString(R.string.december) , 31);

        graphView = view.findViewById(R.id.graph);
        graphView.getViewport().setMaxY(32);
        graphView.getViewport().setYAxisBoundsManual(true);

        setSpinner(view);

        return view;
    }

    private void setSpinner(View view ) {

        spinner = view.findViewById(R.id.monthspinner);
        ArrayAdapter<CharSequence>  arrayAdapter = ArrayAdapter.createFromResource(getContext() ,
                R.array.Month , android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedMonth = (String) adapterView.getItemAtPosition(i);
                if(!TextUtils.isEmpty(selectedMonth)) {
                    dayInMonth = map.get(selectedMonth);
                    setGraph();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setGraph() {
        DataPoint[] dataPoints = new DataPoint[dayInMonth];
        for(int i=0; i<dayInMonth; i++) {
            dataPoints[i] = new DataPoint(i+1 , (i+1)*Math.sin((double)i+1));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graphView.addSeries(series);
        graphView.getViewport().setMaxX(dayInMonth);
        graphView.getViewport().setXAxisBoundsManual(true);
    }
}