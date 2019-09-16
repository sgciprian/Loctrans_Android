package org.room57.loctrans;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.MyViewHolder> {
    private List<Stations> stationsList;
    private List<Stations> stationsListCopy;

    @Override
    public int getItemViewType(int position) {
        return stationsList.get(position).getType();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public StationsAdapter(List<Stations> stationsList) {
        this.stationsList = stationsList;

        stationsListCopy = new ArrayList<>(stationsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.list_stations_row, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_stations_row_parent, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Stations station = stationsList.get(position);
        holder.name.setText(station.getName());
        holder.time.setText(station.getTime());
    }

    @Override
    public int getItemCount() {
        return stationsList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        charText = StringUtils.stripAccents(charText);

        stationsList.clear();
        if (charText.length() == 0) {
            stationsList.addAll(stationsListCopy);
        }
        else {
            for (Stations st : stationsListCopy) {
                if (StringUtils.stripAccents(st.getName().toLowerCase(Locale.getDefault())).contains(charText)) {
                    stationsList.add(st);
                }
            }
        }
        notifyDataSetChanged();
    }
}