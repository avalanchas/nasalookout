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
import com.elliecoding.nasalookout.logics.FragmentEventListener;

public class FragmentDetail extends Fragment {

    private FragmentEventListener listener;

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
}
