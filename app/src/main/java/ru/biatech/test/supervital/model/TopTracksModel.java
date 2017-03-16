package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class TopTracksModel  implements Parcelable {
    public static final Parcelable.Creator<TopTracksModel> CREATOR = new Parcelable.Creator<TopTracksModel>() {
        @Override
        public TopTracksModel createFromParcel(Parcel source) {
            return new TopTracksModel(source);
        }

        @Override
        public TopTracksModel[] newArray(int size) {
            return new TopTracksModel[size];
        }
    };

    @SerializedName("tracks")
    private TracksModel tracks;

    /**
     * Parcel implementation
     */
    private TopTracksModel(Parcel in) {
        if (ParcelUtils.readBooleanFromParcel(in)) this.tracks = in.readParcelable(TracksModel.class.getClassLoader());
    }

    public TracksModel getTracks() {
        return tracks;
    }

    public void setTracks(TracksModel tracks) {
        this.tracks = tracks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(tracks, 0);
    }
}
