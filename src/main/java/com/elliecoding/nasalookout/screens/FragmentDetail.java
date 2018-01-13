package com.elliecoding.nasalookout.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;

public class FragmentDetail extends Fragment {

    private NasaData data;
    private ImageView image;
    private TextView text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);
        image = layout.findViewById(R.id.full_image);
        image.setImageBitmap(data.getImage());
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullText();
            }
        });
        text = layout.findViewById(R.id.full_text);
        text.setVisibility(View.GONE);
        text.setText(data.getExplanation());
        return layout;
    }

    private void toggleFullText() {
        if (text.getVisibility() == View.GONE) {
            text.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.GONE);
        }
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
