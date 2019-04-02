package com.manifest.fomo.Overview;

/**
 * Created by Kevin on 09/03/18.
 */

// This enum defines the type of data to be displayed in the home screen
public enum OverviewEnum {

    MOST_USED_APP(1), MOST_CONTACTED_PERSON(2), MOST_CALLED_PERSON(3), MOST_DATA_USAGE(4);

    public int getType() {
        return type;
    }

    int type;

    OverviewEnum(int type) {
        this.type = type;
    }
}
