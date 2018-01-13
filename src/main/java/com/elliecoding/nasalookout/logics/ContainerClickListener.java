package com.elliecoding.nasalookout.logics;

import com.elliecoding.nasalookout.entities.NasaData;

public interface ContainerClickListener {

    /**
     * Invoked when a container handled by a container list adapter was clicked. The container knows its data and can
     * therefore simply transfer the full DTO
     *
     * @param data The data that belongs to the container that was clicked
     */
    void onContainerClicked(NasaData data);
}
