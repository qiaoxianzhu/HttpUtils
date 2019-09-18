package com.test.httputils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author Joe
 * @date 2019/9/18.
 * description：
 */
public interface FileService {
    /*
     * 上传图片
     * */

    @Multipart
    @POST("xxx")
    Observable<CommonResult<List<String>>> updateImg(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part... file);


}
