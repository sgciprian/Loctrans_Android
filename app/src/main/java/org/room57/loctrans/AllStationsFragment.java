package org.room57.loctrans;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllStationsFragment extends Fragment {
    private List<Stations> stationsList;
    private RecyclerView recyclerView;
    private StationsAdapter sAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_all_lines, null);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Stations station = stationsList.get(position);

                Intent i = new Intent(getActivity().getBaseContext(), StationActivity.class);
                i.putExtra("StationCode", station.getCode());
                i.putExtra("StationName", station.getName());
                i.putExtra("IsDirections", "false");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        stationsList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("data/stations.csv")));
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    Stations nw = new Stations();
                    nw.setCode(next[0]);
                    nw.setName(next[1]);
                    stationsList.add(nw);
                } else {
                    break;
                }
            }
            stationsList.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(stationsList, new Comparator<Stations>() {
            @Override
            public int compare(Stations o1, Stations o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Values.setStationsList(stationsList);

        sAdapter = new StationsAdapter(stationsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_all_stations, menu);

        MenuItem search_item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint(getString(R.string.home_searchHint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sAdapter.filter(s);

                return false;
            }
        });
    }
}
