package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class AttrModel implements Parcelable {
    public static final Parcelable.Creator<AttrModel> CREATOR = new Parcelable.Creator<AttrModel>() {
        @Override
        public AttrModel createFromParcel(Parcel source) {
            return new AttrModel(source);
        }

        @Override
        public AttrModel[] newArray(int size) {
            return new AttrModel[size];
        }
    };

    @SerializedName("page")
    private Integer page;
    @SerializedName("perPage")
    private Integer perPage;
    @SerializedName("totalPages")
    private Integer totalPages;
    @SerializedName("total")
    private Integer total;

    /**
     * Parcel implementation
     */
    private AttrModel(Parcel in) {
        this.page = in.readInt();
        this.perPage = in.readInt();
        this.totalPages = in.readInt();
        this.total = in.readInt();
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
        dest.writeInt(page);
        dest.writeInt(perPage);
        dest.writeInt(totalPages);
        dest.writeInt(total);
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotal() {
        return total;
    }
}
