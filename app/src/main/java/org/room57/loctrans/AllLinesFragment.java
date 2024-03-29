package org.room57.loctrans;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AllLinesFragment extends Fragment {
    private List<Lines> linesList;
    private RecyclerView recyclerView;
    private LinesAdapter lAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_lines, null);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Lines line = linesList.get(position);

                Intent i = new Intent(getActivity().getBaseContext(), LineActivity.class);
                i.putExtra("LineCode", line.getCode());
                i.putExtra("LineName", line.getName());
                i.putExtra("LineColor", line.getColor());
                i.putExtra("LineReverse", "false");
                i.putExtra("SourceStation", "BOGUS");
                i.putExtra("CanReverseView", "true");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        linesList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("data/lines.csv")));
            reader.skip(1);
            for(;;) {
                String[] next = reader.readNext();
                if(next != null) {
                    Lines nw = new Lines();
                    nw.setCode(next[0]);
                    nw.setName(next[1]);
                    nw.setColor(next[2]);
                    linesList.add(nw);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lAdapter = new LinesAdapter(linesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(lAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
