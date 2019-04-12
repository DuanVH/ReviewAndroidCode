package com.example.gem.reviewandroidcode.webservice.ar;

import com.example.gem.reviewandroidcode.pojo.dto.ArContentDTO;
import com.example.gem.reviewandroidcode.webservice.base.BaseArContentResponse;
import com.example.gem.reviewandroidcode.webservice.base.BaseResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ArService {
  @GET("ar/search")
  Single<BaseArContentResponse<List<ArContentDTO>>> getArContentList(@Query("ime") String ime,
                                                                     @Query("latitude") double lat,
                                                                     @Query("longitude") double lng);

  @FormUrlEncoded
  @POST("ar/object-statics")
  Single<BaseResponse> postStatistics(@Field("type") String type,
                                      @Field("data") String data);

}