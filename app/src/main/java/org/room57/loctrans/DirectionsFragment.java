package org.room57.loctrans;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DirectionsFragment extends Fragment {
    Fragment frag;
    String stationCode = null;
    String destinationCode = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_directions, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button btnStart = (Button) view.findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationSearchDialog st = new stationSearchDialog(getContext());
                st.show();
                st.setDialogResult(new stationSearchDialog.OnMyDialogResult(){
                    public void finish(String result){
                        if (result != null) {
                            btnStart.setText(result);
                            stationCode = result;

                            if (stationCode != null && destinationCode != null)
                                updateFragment();
                        }
                    }
                });
            }
        });

        final Button btnEnd = (Button) view.findViewById(R.id.buttonEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationSearchDialog st = new stationSearchDialog(getContext());
                st.show();
                st.setDialogResult(new stationSearchDialog.OnMyDialogResult(){
                    public void finish(String result){
                        if (result != null) {
                            btnEnd.setText(result);
                            destinationCode = result;

                            if (stationCode != null && destinationCode != null)
                                updateFragment();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void updateFragment() {
        Bundle args = new Bundle();
        args.putString("StationCode", stationCode);
        args.putString("IsDirections", "true");
        args.putString("DestinationCode", destinationCode);

        frag = new StationFragment();
        frag.setArguments(args);

        getChildFragmentManager().beginTransaction().replace(R.id.fragmentTimes, frag, "fragmentTimes").commit();
    }

}
