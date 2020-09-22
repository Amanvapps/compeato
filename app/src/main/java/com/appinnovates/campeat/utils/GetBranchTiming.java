package com.appinnovates.campeat.utils;

import android.content.Context;
import android.util.Log;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.getAllDealsModel.Operational;

import java.util.ArrayList;
import java.util.List;

public class GetBranchTiming {
    private Context context;


    public GetBranchTiming(Context context) {
        this.context = context;
    }


    public String getBranchTime(Operational data) {
        String getAll = "";
        if (data != null) {
            String am = "am";
            List<List<String>> allDays;
            String[] days = {context.getResources().getString(R.string.mon), context.getString(R.string.tue), context.getString(R.string.wed), context.getString(R.string.thu), context.getString(R.string.fri), context.getString(R.string.sat), context.getResources().getString(R.string.sun)};
            List<String> sun, mon, tue, wed, thu, fri, sat;
            int i1;
            List<String> time;
            time = new ArrayList<>();
            allDays = new ArrayList<>();
            sun = new ArrayList<>(data.getSUN()!=null?data.getSUN():new ArrayList<>());
            mon = new ArrayList<>(data.getMON()!=null?data.getMON():new ArrayList<>());
            tue = new ArrayList<>(data.getTUE()!=null?data.getTUE():new ArrayList<>());
            wed = new ArrayList<>(data.getWED()!=null?data.getWED():new ArrayList<>());
            thu = new ArrayList<>(data.getTHU()!=null?data.getTHU():new ArrayList<>());
            fri = new ArrayList<>(data.getFRI()!=null?data.getFRI():new ArrayList<>());
            sat = new ArrayList<>(data.getSAT()!=null?data.getSAT():new ArrayList<>());
            allDays.add(mon);
            allDays.add(tue);
            allDays.add(wed);
            allDays.add(thu);
            allDays.add(fri);
            allDays.add(sat);
            allDays.add(sun);
            for (int i = 0; i < allDays.size(); i++) {
                time.clear();
                time.addAll(allDays.get(i));
                if (time.size() != 0)
                    getAll = getAll.concat(days[i]);
                for (int j = 0; j < time.size(); j++) {
                    if (j == 1) {
                        i1 = Integer.parseInt(time.get(j).substring(0, 2));
                        if (i1 > 12) {
                            am = "pm";
                            i1 = i1 - 12;
                        }
                        getAll = getAll.concat(i1 + ":" + time.get(j).substring(2, 4) + am + "  ");
                    } else {
                        am = "am";
                        getAll = getAll.concat(time.get(j).substring(0, 2) + ":" + time.get(j).substring(2, 4) + am + " - ");
                    }
                }
                if (time.size() != 0)
                    getAll = getAll.concat("\n");
            }
            Log.i("string", getAll);
        }
        return getAll;
    }
}
