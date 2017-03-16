package ru.biatech.test.supervital.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.biatech.test.supervital.testbia_tech.R;
import ru.biatech.test.supervital.activity.MainActivity;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class StartFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = StartFragment.class.getSimpleName();

    protected SwipeRefreshLayout mSwipeLayout;
    public TextView txtNotInternet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_main_layout, container, false);
        txtNotInternet = (TextView) rootView.findViewById(R.id.txtNotInternet);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.blue_swipe, R.color.green_swipe,
                R.color.orange_swipe, R.color.red_swipe);

        return rootView;
    }

    @Override
    public void onRefresh() {
        ((MainActivity)getActivity()).RestoreTracksFrame();
        mSwipeLayout.setRefreshing(false);
    }
}
