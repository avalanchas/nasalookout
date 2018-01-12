package com.elliecoding.nasalookout.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.elliecoding.nasalookout.R;

public class FragmentOverview extends Fragment {

    public static FragmentOverview newInstance() {
        return new FragmentOverview();
    }

    private TextView holder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_overview, container, false);
        holder = layout.findViewById(R.id.text);

        return layout;
    }

    public void displayImage(String response) {
        holder.setText(response);
    }
}
