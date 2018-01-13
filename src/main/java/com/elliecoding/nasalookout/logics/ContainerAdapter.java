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

    private List<NasaData> values = new ArrayList<>();

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView coverImage;
        private TextView coverText;
        private View mLayout;

        protected ViewHolder(View layout) {
            super(layout);
            mLayout = layout;
            coverImage = mLayout.findViewById(R.id.cover_image);
            coverText = mLayout.findViewById(R.id.cover_text);
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

        String month = holder.mLayout.getResources().getStringArray(R.array.months)[data.getDate()
                .getMonthOfYear() - 1];
        String text = month + " " + data.getDate().getYear();
        holder.coverText.setText(text);
        holder.coverImage.setImageBitmap(data.getImage());
        holder.coverImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openItem(holder.getAdapterPosition(), view);
            }
        });
    }

    private Resources getResources(ViewHolder holder) {
        return holder.mLayout.getResources();
    }

    private void openItem(int position, View view) {
        Log.w("tag", "item clicked");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}