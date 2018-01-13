package com.elliecoding.nasalookout.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;
import com.elliecoding.nasalookout.logics.FragmentEventListener;

public class FragmentDetail extends Fragment {

    private FragmentEventListener listener;
    private NasaData data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentEventListener) {
            listener = (FragmentEventListener) context;
        } else {
            throw new IllegalStateException("A context that wishes to attach to this fragment must implement " +
                    FragmentEventListener.class.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);

        return layout;
    }

    /**
     * You must invoke this static factory method instead of the constructor of the fragment itself
     * since some initial preparation is necessary
     *
     * @param data The data package that this fragment is tasked to display
     * @return A fully instantiated new {@link FragmentDetail}, not a singleton, ready for use
     */
    public static FragmentDetail newInstance(NasaData data) {
        // Save the data for the instance then return
        FragmentDetail result = new FragmentDetail();
        result.data = data;
        return result;
    }
}
