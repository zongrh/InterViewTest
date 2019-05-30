package cn.my.rxjavatest.net;

import cn.my.rxjavatest.model.CategoryResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * FileName: GankApi
 * Author: nanzong
 * Date: 2019/5/28 5:17 PM
 * Description:
 * History:
 */
public interface GankApi {
    /**
     * 根据category获取Android、iOS等干货数据
     * @param category  类别
     * @param count     条目数目
     * @param page      页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(
            @Path("category")String category, @Path("count")int count, @Path("page")int page);
}

