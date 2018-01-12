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
import com.elliecoding.nasalookout.logics.FragmentCallable;

public class FragmentDetail extends Fragment {

    private FragmentCallable listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentCallable) {
            listener = (FragmentCallable) context;
        } else {
            throw new IllegalStateException("A context that wishes to attach to this fragment must implement " +
                    FragmentCallable.class.getName());
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
