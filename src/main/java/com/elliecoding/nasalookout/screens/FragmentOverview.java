package com.elliecoding.nasalookout.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.*;

public class FragmentOverview extends Fragment {

    private TextView mPlaceholder;
    private RecyclerView mBlockGrid;

    private FragmentEventListener mListener;
    private boolean mHidden;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mHidden = hidden;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentEventListener) {
            mListener = (FragmentEventListener) context;
        } else {
            throw new IllegalStateException("A context that wishes to attach to this fragment must implement " +
                    FragmentEventListener.class.getName());
        }
    }


    public static FragmentOverview newInstance() {
        return new FragmentOverview();
    }

    private ContainerAdapter mDayAdapter = new ContainerAdapterDays(new ContainerClickListener() {
        @Override
        public void onContainerClicked(NasaData data) {
            mListener.onContainerClicked(data);
        }
    });

    private ContainerAdapter mMonthAdapter = new ContainerAdapterMonths(new ContainerClickListener() {
        @Override
        public void onContainerClicked(NasaData data) {
            mListener.onContainerClicked(data);
        }
    });

    // The current adapter defaults to months, because that is what we need first
    private ContainerAdapter mCurrentAdapter = mMonthAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_overview, container, false);

        mBlockGrid = layout.findViewById(R.id.block_container);
        mPlaceholder = layout.findViewById(R.id.placeholder);


        // We know the layout will not change, supposed to increase performance
        mBlockGrid.setHasFixedSize(true);

        // We need a trigger for when the user has scrolled all the way to the bottom
        mBlockGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(ItemTouchHelper.DOWN)) {
                    mListener.onMoreItemsRequired(mCurrentAdapter.getLastItem());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        mBlockGrid.setLayoutManager(manager);
        mBlockGrid.setAdapter(mCurrentAdapter);

        return layout;
    }

    public void addDataToOverview(NasaData data) {
        if (mPlaceholder.getVisibility() == View.VISIBLE) {
            mPlaceholder.setVisibility(View.GONE);
        }
        mCurrentAdapter.addItem(data);
    }

    /**
     * Triggers this fragment to instructs its {@link android.support.v7.widget.RecyclerView.Adapter} to remove all
     * items currently visible on display
     */
    public void setToOverviewType(ActivityMain.ViewState viewState) {
        mCurrentAdapter.removeAll();
        mPlaceholder.setVisibility(View.VISIBLE);

        switch (viewState) {
            case MONTH:
                mCurrentAdapter = mDayAdapter;
                mBlockGrid.setAdapter(mCurrentAdapter);
                break;
            case YEAR:
                mCurrentAdapter = mMonthAdapter;
                mBlockGrid.setAdapter(mCurrentAdapter);
        }
    }
}
