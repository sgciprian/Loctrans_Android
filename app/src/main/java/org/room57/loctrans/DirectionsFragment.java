package org.room57.loctrans;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

public class DirectionsFragment extends Fragment {
    Fragment frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_directions, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = new Bundle();
        args.putString("StationCode", "CATEDRALA");
        args.putString("StationName", "Catedrala");
        args.putString("IsDirections", "trueEmbedded");
        args.putString("DestinationCode", "VALCEA");

        frag = new StationFragment();
        frag.setArguments(args);

        getChildFragmentManager().beginTransaction().replace(R.id.fragmentTimes, frag, "fragmentTimes").commit();
    }

}
