package com.elliecoding.nasalookout.logics;

import android.support.v7.widget.RecyclerView;
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
        private TextView coverImage;
        private ImageView coverText;

        protected ViewHolder(View layout) {
            super(layout);
            this.layout = layout;
            coverImage = layout.findViewById(R.id.cover_image);
            coverText = layout.findViewById(R.id.cover_text);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContainerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.coverImage.setText(name);
        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.coverText.setText("Footer: " + name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}