package com.elliecoding.nasalookout.logics;

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
import java.util.List;

public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ViewHolder> {

    private List<NasaData> values = new ArrayList<>();

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private ImageView coverImage;
        private TextView coverText;

        protected ViewHolder(View layout) {
            super(layout);
            this.layout = layout;
            coverImage = layout.findViewById(R.id.cover_image);
            coverText = layout.findViewById(R.id.cover_text);
        }
    }

    public void addItems(List<NasaData> items) {
        values.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(NasaData item) {
        addItem(getItemCount(), item);
    }

    public void addItem(int position, NasaData item) {
        values.add(position, item);
        notifyItemInserted(position);
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

        holder.coverText.setText(data.getDate());
        holder.coverImage.setImageBitmap(data.getImage());
        holder.coverImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openItem(holder.getAdapterPosition(), view);
            }
        });
    }

    private void openItem(int position, View view) {
        Log.w("tag", "item clicked");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}