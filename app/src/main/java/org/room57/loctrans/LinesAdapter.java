package org.room57.loctrans;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.MyViewHolder> {

    private List<Lines> linesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }


    public LinesAdapter(List<Lines> linesList) {
        this.linesList = linesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_stations_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Lines line = linesList.get(position);
        holder.name.setText(line.getName());
    }

    @Override
    public int getItemCount() {
        return linesList.size();
    }
}