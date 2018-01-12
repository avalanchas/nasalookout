package com.elliecoding.nasalookout.logics;

/**
 * Callback interface for {@link android.support.v4.app.Fragment} to {@link android.app.Activity} communication via
 * specific callback methods that any activity that wishes to attach to the respective fragment has to implement. Note:
 * the fragment itself is responsible for checking whether a callback is possible
 */
public interface FragmentCallable {

    void onFavouriteClicked(String id);
}
