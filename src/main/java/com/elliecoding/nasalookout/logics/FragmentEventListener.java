package com.elliecoding.nasalookout.logics;

import com.elliecoding.nasalookout.entities.NasaData;

/**
 * Callback interface for {@link android.support.v4.app.Fragment} to {@link android.app.Activity} communication via
 * specific callback methods that any activity that wishes to attach to the respective fragment has to implement. Note:
 * the fragment itself is responsible for checking whether a callback is possible
 */
public interface FragmentEventListener {

    void onContainerClicked(NasaData data);
}
