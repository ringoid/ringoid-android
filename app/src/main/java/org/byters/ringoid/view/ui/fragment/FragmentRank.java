package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.adapter.AdapterBase;
import org.byters.ringoid.view.ui.adapter.AdapterRankSexOther;
import org.byters.ringoid.view.ui.adapter.AdapterRankSexSame;

public class FragmentRank extends FragmentBase
        implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        initList(view);
        view.findViewById(R.id.ivFemale).setOnClickListener(this);
        view.findViewById(R.id.ivMale).setOnClickListener(this);
        return view;
    }

    private void initList(View view) {
        initList(view, new AdapterRankSexOther()); //todo implement get sex from cache
    }

    private void initList(View view, AdapterBase adapterBase) {
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvItems.setAdapter(adapterBase);
    }

    @Override
    public void onClick(View view) {
        //todo implement pass to presenter
        if (view.getId() == R.id.ivFemale)
            initList(getView(), new AdapterRankSexOther());

        if (view.getId() == R.id.ivMale)
            initList(getView(), new AdapterRankSexSame());
    }
}
