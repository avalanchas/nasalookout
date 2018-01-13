package com.elliecoding.nasalookout.logics;

import com.elliecoding.nasalookout.entities.NasaData;

/**
 * Callback interface for {@link android.support.v4.app.Fragment} to {@link android.app.Activity} communication via
 * specific callback methods that any activity that wishes to attach to the respective fragment has to implement. Note:
 * the fragment itself is responsible for checking whether a callback is possible
 */
public interface FragmentEventListener {


    /**
     * Invoked whenever an overview container for a specific month is clicked. The app should then continue on to
     * show ALL the items for that month once this callback is invoked
     *
     * @param data The data of the container that was clicked
     */
    void onContainerClicked(NasaData data);

    /**
     * Invoked whenever a specific item of a month was clicked. The app should then show this item in full size to
     * the user
     *
     * @param data The whole NasaData needed to show all details of this Nasa item
     */
    void onNasaItemClicked(NasaData data);

    /**
     * Invoked when the recycler view of any grid showing on a fragment cannot scroll further down
     *
     * @param lastItem The last item that is managed and after which more items are required
     */
    void onMoreItemsRequired(NasaData lastItem);
}
