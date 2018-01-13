package com.elliecoding.nasalookout.logics;


import android.view.View;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;

import java.util.Locale;

public class ContainerAdapterDays extends ContainerAdapter {

    private final ContainerClickListener mListener;

    public ContainerAdapterDays(ContainerClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NasaData data = getValues().get(position);

        String day = String.format(Locale.getDefault(), "%02d", data.getDate().getDayOfMonth());
        String month = holder.mLayout.getResources().getStringArray(R.array.months)[data.getDate().getMonthOfYear() -
                1];
        String text = day + ". " + month + " " + data.getDate().getYear();
        holder.mCoverText.setText(text);
        holder.mCoverImage.setImageBitmap(data.getImage());
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onContainerClicked(data);
                }
            }
        });
    }
}
