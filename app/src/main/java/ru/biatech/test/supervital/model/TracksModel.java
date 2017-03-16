package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class TracksModel implements Parcelable {
    public static final Parcelable.Creator<TracksModel> CREATOR = new Parcelable.Creator<TracksModel>() {
        @Override
        public TracksModel createFromParcel(Parcel source) {
            return new TracksModel(source);
        }

        @Override
        public TracksModel[] newArray(int size) {
            return new TracksModel[size];
        }
    };

    @SerializedName("track")
    private List<TrackModel> track;
    @SerializedName("@attr")
    private AttrModel attr;

    /**
     * Parcel implementation
     */
    private TracksModel(Parcel in) {
        if (ParcelUtils.readBooleanFromParcel(in)) this.track = in.createTypedArrayList(TrackModel.CREATOR);
        if (ParcelUtils.readBooleanFromParcel(in)) this.attr = in.readParcelable(AttrModel.class.getClassLoader());

    }

    /**
     * Parcel implementation
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcel implementation
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(track);
        dest.writeParcelable(attr, 0);
    }

    public List<TrackModel> getTrack() {
        return track;
    }

    public AttrModel getAttr() {
        return attr;
    }

    public void setTrack(List<TrackModel> track) {
        this.track = track;
    }

    public void setAttr(AttrModel attr) {
        this.attr = attr;
    }
}
