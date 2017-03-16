package ru.biatech.test.supervital.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.biatech.test.supervital.Const;
import ru.biatech.test.supervital.model.TopTracksModel;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class RetrofitSingleton {
    private static final String TAG = RetrofitSingleton.class.getSimpleName();

    private static Observable<TopTracksModel> observableRetrofit;
    private static BehaviorSubject<TopTracksModel> observableModelsList;
    private static Subscription subscription;

    public static Integer NumPage = Const.rpSTART_PAGE;
    public static Integer totalPages = -1;
    public static Boolean isAddSwipeDown = false;
    static GetModels apiService;

    private RetrofitSingleton() {
    }

    public static void initRetro() {
        Log.d(TAG, "initRetro");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.rpBASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(GetModels.class);

        observableRetrofit = apiService.getTopTracksList(Const.rpAPI_KEY, NumPage, Const.rpLIMIT);
    }

    public static void resetModelsObservable() {
        observableModelsList = BehaviorSubject.create();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = observableRetrofit.subscribe(new Subscriber<TopTracksModel>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
                observableModelsList.onError(e);
            }

            @Override
            public void onNext(TopTracksModel TopTracksModel) {
                totalPages = TopTracksModel.getTracks().getAttr().getTotalPages();
                observableModelsList.onNext(TopTracksModel);
            }
        });
    }


    public static Observable<TopTracksModel> getModelsObservable() {
        if (observableModelsList == null) {
            resetModelsObservable();
        }
        return observableModelsList;
    }
}
