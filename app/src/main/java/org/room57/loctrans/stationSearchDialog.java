package org.room57.loctrans;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class stationSearchDialog extends Dialog implements View.OnClickListener {
    private StationsAdapter sAdapter;
    private RecyclerView recyclerView;
    private EditText filterText;
    private List<Stations> stationsList;
    private OnMyDialogResult mDialogResult;

    public stationSearchDialog(@NonNull Context context) {
        super(context);
        this.stationsList = new ArrayList<>();
        this.stationsList.addAll(Values.stationsList);

        setContentView(R.layout.dialog_station_search);
        this.setTitle(R.string.stationSearchDialog_title);

        filterText = (EditText) findViewById(R.id.box);
        filterText.addTextChangedListener(filterTextWatcher);
        if(filterText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        recyclerView = findViewById(R.id.recycler_view);
        sAdapter = new StationsAdapter(stationsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if( mDialogResult != null ){
                    mDialogResult.finish(stationsList.get(position).getCode());
                }
                dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {

    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            sAdapter.filter(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onStop(){
        filterText.removeTextChangedListener(filterTextWatcher);
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }
}
