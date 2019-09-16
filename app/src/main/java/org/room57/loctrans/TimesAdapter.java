package org.room57.loctrans;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.MyViewHolder> {
    private List<Times> timesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, line, end;

        public MyViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            line = (TextView) view.findViewById(R.id.line);
            end = (TextView) view.findViewById(R.id.end);
        }
    }

    public TimesAdapter(List<Times> timesList) {
        this.timesList = timesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_times_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Times tm = timesList.get(position);
        holder.time.setText(tm.getTime());
        holder.line.setText(tm.getLine().getName());
        holder.end.setText(tm.getEnd());
    }

    @Override
    public int getItemCount() {
        return timesList.size();
    }

    public void filter(String destinationStation, String currentStation, List<Times> timesListCopy) {

        timesList.clear();

        if (destinationStation.length() == 0) {
            timesList.addAll(timesListCopy);
        }
        else {
            for (Times tm : timesListCopy) {
                boolean passedCurrent = false;
                for (String station: tm.getStations()) {
                    if (station.equals(currentStation))
                        passedCurrent = true;
                    if (passedCurrent && station.equals(destinationStation)) {
                        timesList.add(tm);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}