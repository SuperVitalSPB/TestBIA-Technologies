package ru.biatech.test.supervital.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.biatech.test.supervital.model.TrackModel;
import ru.biatech.test.supervital.testbia_tech.R;
import ru.biatech.test.supervital.model.TopTracksModel;
import ru.biatech.test.supervital.retrofit.RetrofitSingleton;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class AllTracksFragment extends BaseTracksFragment {
    private static final String TAG = AllTracksFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutResource = R.layout.all_tracks_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void getModelsList() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RetrofitSingleton.getModelsObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<TopTracksModel>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        if (models.size()==0)
                            models.add(TrackModel.getEmptyTrackModel());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError", e);
                        isLoading = false;
                        if (isAdded()) {
                            showLoadingIndicator(false);
                            Snackbar.make(recyclerView, R.string.connection_error, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.try_again, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RetrofitSingleton.resetModelsObservable();
                                            showLoadingIndicator(true);
                                            getModelsList();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onNext(TopTracksModel newModels) {
                        Log.d(TAG, "onNext: " + newModels.getTracks().getTrack().size());
                        int prevSize = models.size();
                        isLoading = false;
                        if (isAdded()) {
                            recyclerView.getAdapter().notifyItemRangeRemoved(0, prevSize);
                        }
                        if (!RetrofitSingleton.isAddSwipeDown)
                                                    models.clear();
                        models.addAll(newModels.getTracks().getTrack());
                        if (isAdded()) {
                            recyclerView.getAdapter().notifyItemRangeInserted(0, models.size());
                            showLoadingIndicator(false);
                        }
                        RetrofitSingleton.isAddSwipeDown = false;
                    }
                });
    }

}
