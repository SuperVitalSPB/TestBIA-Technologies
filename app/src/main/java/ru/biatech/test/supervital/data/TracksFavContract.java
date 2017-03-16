package ru.biatech.test.supervital.data;

import android.provider.BaseColumns;

/**
 * Created by Vitaly Oantsa on 16.03.2017.
 */

public class TracksFavContract {
    private TracksFavContract() {
    };

    public static final class TracksFavEntry implements BaseColumns {
        public final static String TABLE_NAME = "tracks_fav";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_NAME_ARTIST = "name_artist";
        public final static String COLUMN_URL = "url";
    }
}