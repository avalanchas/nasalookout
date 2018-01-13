package com.elliecoding.nasalookout.logics;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.elliecoding.nasalookout.R;
import com.elliecoding.nasalookout.entities.NasaData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List adapter for containers that represent the overview of a month of data. Works with {@link NasaData} to provide
 * a preview of the clickable container
 */
public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ViewHolder> {

    private final List<NasaData> values = new ArrayList<>();

    private final ContainerClickListener mListener;

    public ContainerAdapter(ContainerClickListener listener) {
        mListener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private View mlayout;

        private ImageView mCoverImage;
        private TextView mCoverText;
        private ViewGroup mContainer;

        protected ViewHolder(View layout) {
            super(layout);
            mlayout = layout;

            mContainer = mlayout.findViewById(R.id.container_layout);
            mCoverImage = mlayout.findViewById(R.id.cover_image);
            mCoverText = mlayout.findViewById(R.id.cover_text);
        }
    }

    public void addItems(List<NasaData> items) {
        values.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Expects a new item that will be added to the list that this adapter manages. The item will be sorted according
     * to the comparable implementation of the {@link NasaData}, the adapter takes care of all positioning
     *
     * @param item The item to add
     */
    public void addItem(NasaData item) {
        values.add(item);
        Collections.sort(values, Collections.<NasaData>reverseOrder());
        notifyDataSetChanged();
    }


    @Override
    public ContainerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View layout = inflater.inflate(R.layout.block_cover, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NasaData data = values.get(position);

        String month = holder.mlayout.getResources().getStringArray(R.array.months)[data.getDate()
                .getMonthOfYear() - 1];
        String text = month + " " + data.getDate().getYear();
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

    private Resources getResources(ViewHolder holder) {
        return holder.mlayout.getResources();
    }

    private void openContainer(int position, View view) {

        Log.w("tag", "item clicked");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}