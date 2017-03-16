package ru.biatech.test.supervital.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.biatech.test.supervital.testbia_tech.R;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class FavoriteTracksFragment extends BaseTracksFragment {
    private static final String TAG = FavoriteTracksFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutResource = R.layout.favorite_tracks_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container, savedInstanceState);
    }

    @Override
    protected void getModelsList() {
        models.clear();
        models.addAll(myAppl.getmDbHelper().getTracksFavoriteList());
        recyclerView.getAdapter().notifyDataSetChanged();
        showLoadingIndicator(false);
    }
}
