package ru.biatech.test.supervital.retrofit;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.biatech.test.supervital.model.TopTracksModel;
import rx.Observable;

public interface GetModels {
    // http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=4ea996fd1ca3ce423d5b6c96c629d21a&format=json
    @GET("?method=chart.gettoptracks&format=json")
    Observable<TopTracksModel> getTopTracksList(@Query("api_key") String api_key, @Query("page") Integer page, @Query("limit") Integer limit);


}

/*
    @GET("rx-retrofit-and-android-screen-orientation.php")
    Observable<ArrayList<TracksModel>> getModelsList();

    @Get("/barcodes/{purpose}?preset={preset}&contractId={contractId}")
    ResponseEntity<String> getBarcode(@Path String contractId, @Path String purpose, @Path String preset);

*/