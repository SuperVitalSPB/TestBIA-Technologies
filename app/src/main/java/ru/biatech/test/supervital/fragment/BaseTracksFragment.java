package ru.biatech.test.supervital.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;

import ru.biatech.test.supervital.ApplicationBIATech;
import ru.biatech.test.supervital.Const;
import ru.biatech.test.supervital.testbia_tech.R;
import ru.biatech.test.supervital.adapter.ModelsListRecyclerAdapter;
import ru.biatech.test.supervital.model.TrackModel;
import ru.biatech.test.supervital.retrofit.RetrofitSingleton;
import rx.Subscription;

import static ru.biatech.test.supervital.retrofit.RetrofitSingleton.totalPages;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class BaseTracksFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = BaseTracksFragment.class.getSimpleName();

    protected ApplicationBIATech myAppl;
    protected ImageView loadingIndicator;
    protected RecyclerView recyclerView;
    protected ArrayList<TrackModel> models = new ArrayList<>();
    protected Subscription subscription;
    protected boolean isLoading;

    protected SwipeRefreshLayout mSwipeLayout;
    protected int layoutResource;

    @Override
    public void onRefresh() {
        Log.d(TAG, "refresh swiped");
        if (subscription!=null)
                RetrofitSingleton.resetModelsObservable();
        showLoadingIndicator(true);
        getModelsList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myAppl = (ApplicationBIATech) getActivity().getApplication();
        View rootView = inflater.inflate(layoutResource, container, false);

        if (savedInstanceState != null) {
            models = savedInstanceState.getParcelableArrayList(Const.KEY_MODELS);
            isLoading = savedInstanceState.getBoolean(Const.KEY_IS_LOADING);
        }

        InitView(rootView);

        if (models.size() == 0 || isLoading) {
            mSwipeLayout.setRefreshing(true);
            getModelsList();
        }
        return rootView;
    }

    private void InitView(View rootView){
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.blue_swipe, R.color.green_swipe,
                R.color.orange_swipe, R.color.red_swipe);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        loadingIndicator = (ImageView) rootView.findViewById(R.id.loading_indicator);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ModelsListRecyclerAdapter(models, getActivity()));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    Log.d(TAG, "Scrolling  UP");
                    if (subscription != null) {
                        if (((ModelsListRecyclerAdapter) recyclerView.getAdapter()).isBottomList) {
                            if (RetrofitSingleton.NumPage > totalPages)
                                return;
                            RetrofitSingleton.NumPage++;
                            RetrofitSingleton.isAddSwipeDown = true;
                            showLoadingIndicator(true);
                            getModelsList();
                        }
                    }


                } else {
                    Log.d(TAG, "Scrolling  DOWN");
                }
            }
        });
    }

    protected void showLoadingIndicator(boolean show) {
        isLoading = show;
        if (isLoading) {
            loadingIndicator.setVisibility(View.VISIBLE);
            loadingIndicator.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                    .rotationBy(360)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingIndicator.animate()
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .rotationBy(360).setDuration(500).setListener(this);
                }
            });
        }
        else {
            loadingIndicator.animate().cancel();
            loadingIndicator.setVisibility(View.GONE);
        }
        mSwipeLayout.setRefreshing(isLoading);
    }

    protected void getModelsList() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Const.KEY_MODELS, models);
        outState.putBoolean(Const.KEY_IS_LOADING, isLoading);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}
