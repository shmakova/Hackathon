package ru.shmakova.hackathon.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import ru.shmakova.hackathon.network.models.Lookup;


public interface DictionaryService {
    @GET("lookup")
    Call<Lookup> lookup(@QueryMap Map<String, String> options);
}
