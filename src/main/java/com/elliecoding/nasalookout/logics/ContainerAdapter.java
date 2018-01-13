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
import java.util.Collections;
import java.util.List;

/**
 * List adapter for containers that represent the overview of a month of data. Works with {@link NasaData} to provide
 * a preview of the clickable container
 */
public abstract class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ViewHolder> {

    private final List<NasaData> values = new ArrayList<>();

    protected List<NasaData> getValues() {
        return values;
    }

    /**
     * Removes all items of this adapter (and therefore the list) in a decently performance-efficient manner.
     * However, do not call this method too often
     */
    public void removeAll() {
        int size = values.size();
        values.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * Retrieves the last item currently stored/managed by this adapter. Note that this is <b>not</b> the item that
     * was <b>added</b> last, but the item that is the last one in the list according to the current sorting principles
     *
     * @return The last item by current sorting, never null
     */
    public NasaData getLastItem() {
        return values.get(values.size() - 1);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected View mLayout;

        protected ImageView mCoverImage;
        protected TextView mCoverText;
        protected ViewGroup mContainer;

        protected ViewHolder(View layout) {
            super(layout);
            mLayout = layout;

            mContainer = mLayout.findViewById(R.id.container_layout);
            mCoverImage = mLayout.findViewById(R.id.cover_image);
            mCoverText = mLayout.findViewById(R.id.cover_text);
        }
    }

    /**
     * Adds several items to this adapter at once. The items will be sorted according to the current sorting
     * principles (currently: oldest date last) after all items have been added
     *
     * @param items A list of items to add to this adapter to manage
     */
    public void addItems(List<NasaData> items) {
        values.addAll(items);
        Collections.sort(values, Collections.<NasaData>reverseOrder());
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
    public int getItemCount() {
        return values.size();
    }

}