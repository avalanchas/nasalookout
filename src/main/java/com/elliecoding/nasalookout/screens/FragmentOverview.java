package com.elliecoding.nasalookout.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.ContainerAdapter;

public class FragmentOverview extends Fragment {

    TextView mPlaceholder;

    public static FragmentOverview newInstance() {
        return new FragmentOverview();
    }

    private ContainerAdapter mAdapter = new ContainerAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_overview, container, false);

        RecyclerView blockGrid = layout.findViewById(R.id.block_container);
        mPlaceholder = layout.findViewById(R.id.placeholder);

        // We know the layout will not change, supposed to increase performance
        blockGrid.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        blockGrid.setLayoutManager(manager);
        blockGrid.setAdapter(mAdapter);

        return layout;
    }

    public void addDataToOverview(NasaData data) {
        if (mPlaceholder.getVisibility() == View.VISIBLE) {
            mPlaceholder.setVisibility(View.GONE);
        }
        mAdapter.addItem(data);
    }
}
