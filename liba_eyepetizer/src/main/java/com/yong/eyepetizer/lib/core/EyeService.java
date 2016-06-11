package com.yong.eyepetizer.lib.core;

import com.yong.eyepetizer.lib.data.CategoryData;
import com.yong.eyepetizer.lib.data.DisMore;
import com.yong.eyepetizer.lib.data.RankList;
import com.yong.eyepetizer.lib.data.TodayFeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <b>Project:</b> com.yong.eyepetizer.lib.core <br>
 * <b>Create Date:</b> 2016/6/10 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 请求返回数据 <br>
 */
public interface EyeService {

    public static final String API_URL = "http://baobab.wandoujia.com/api/v2/";

    /**
     * 每日精选
     *
     * @param date 时间戳，13位
     * @param num  数量，默认7
     */
    @GET("feed")
    Call<TodayFeed> getFeedData(@Query("date") long date, @Query("num") int num);

    /**
     * 发现更多，分类
     */
    @GET("categories")
    Call<List<DisMore>> getDisMoreData();

    /**
     * @param categoryId 分类id，上个请求拿到
     * @param strategy   按什么分类，date按时间，shareCount按分享次数
     */
    @GET("videos")
    Call<CategoryData> getCategoryData(@Query("categoryId") String categoryId, @Query("strategy") String strategy);

    /**
     * 排行数据
     *
     * @param strategy weekly周排行，monthly月排行，historical总排行
     */
    @GET("ranklist")
    Call<RankList> getRankData(@Query("strategy") String strategy);
}
