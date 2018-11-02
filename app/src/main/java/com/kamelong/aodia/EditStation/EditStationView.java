package com.kamelong.aodia.EditStation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kamelong.OuDia.DiaFile;
import com.kamelong.OuDia.Station;
import com.kamelong.aodia.R;
import com.kamelong.aodia.SdLog;
import com.kamelong.aodia.StationTimeTable.StationTimetableIndexDia;

import java.util.ArrayList;

public class EditStationView extends LinearLayout {
    StationEditInterface stationEditInterface=null;
    public int stationIndex=0;
    public EditStationView(final Context context, final Station station, int sIndex){
        super(context);
        this.stationIndex=sIndex;
        try{
            LayoutInflater.from(context).inflate(R.layout.edit_station_view, this);
            ((TextView)findViewById(R.id.stationName)).setText(station.name);
            ((TextView)findViewById(R.id.stationIndex)).setText(stationIndex+"");
            findViewById(R.id.expandButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.expandButton).setVisibility(GONE);
                    findViewById(R.id.closeButton).setVisibility(VISIBLE);
                    ((LinearLayout)findViewById(R.id.stationLinear)).addView(new EditStationInfoView(context,station));
                }
            });
            findViewById(R.id.closeButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.expandButton).setVisibility(VISIBLE);
                    findViewById(R.id.closeButton).setVisibility(GONE);
                    ((LinearLayout)findViewById(R.id.stationLinear)).removeViewAt(1);
                }
            });
            findViewById(R.id.addButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(stationEditInterface!=null){
                        stationEditInterface.addNewStation(stationIndex+1);
                    }
                }
            });
            findViewById(R.id.deleteButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(stationEditInterface!=null){
                        stationEditInterface.removeStation(stationIndex);
                    }

                }
            });

        }catch (Exception e){
            SdLog.log(e);
        }
    }
    public void setStationEditInterface(StationEditInterface listener){
        stationEditInterface=listener;
    }
    public void renewStationIndex(int stationIndex){
        this.stationIndex=stationIndex;
        ((TextView)findViewById(R.id.stationIndex)).setText(stationIndex+"");

    }


}
