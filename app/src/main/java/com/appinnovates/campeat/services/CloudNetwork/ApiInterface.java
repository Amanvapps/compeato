package com.appinnovates.campeat.services.CloudNetwork;

import com.appinnovates.campeat.model.Banner.BannerData;
import com.appinnovates.campeat.model.CharityModelData;
import com.appinnovates.campeat.model.MenuResult;
import com.appinnovates.campeat.model.RedeemCoupon.CouponList;
import com.appinnovates.campeat.model.charity.getCharities.Charity;
import com.appinnovates.campeat.model.charity.submitCharity.SubmitCharity;
import com.appinnovates.campeat.model.receiptModel.ReceiptData;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("functions/getDealMenus")
    Call<MenuResult> getMenus(@Query("dealId") String dealId,@Query("branchId") String branchId
            , @HeaderMap Map<String, String> map);

    @POST("functions/getAllMenusOfCoupons")
    Call<MenuResult> getCouponsMenus(@Query("couponId") String couponId
            , @HeaderMap Map<String, String> map);

/*    @POST("ethereum/campeat/api/v1/generate/token")
    Call<GenerateToken> getHit(@HeaderMap Map<String, String> map, @Body Map<String, Object> map2);

    @POST("v1/user/wallet/transaction")
    Call<Object> send(@HeaderMap Map<String, String> map,@Body Map<String, Object> map2);

    @POST("v1/user/wallet/transaction")
    Call<RestaurantDeals> receive(@HeaderMap Map<String, String> map,@Body Map<String, Object> map2);

    @POST("v1/user/wallet/transaction/convert ")
    Call<RestaurantDeals> convert(@HeaderMap Map<String, String> map,@Body Map<String, Object> map2);*/

/*    @POST("TADPEntries")
/*    @POST("TADPEntries")
    Call<RestaurantDeals> getHit(*//*@HeaderMap Map<String, Object> map*//*);*/

    @GET("classes/Charity")
    Call<Charity> getCharity(/*@Query("customer_id") String userID*/@HeaderMap Map<String, String> map);
    @FormUrlEncoded
    @POST("/functions/saveCharity")
    Call<CharityModelData> saveCharity(@Field("customer_id") String userID
            , @Field("charities")ArrayList<String> charityIds
            , @HeaderMap Map<String, String> map);

    @FormUrlEncoded
    @POST("functions/subscribeToCharity ")
    Call<SubmitCharity> submitCharity(@Field("charities")Map<String,Object> charityIds
            , @HeaderMap Map<String, String> map);

    @FormUrlEncoded
    @POST("https://api.campeat.io/api/verificationEmailRequest")
    Call<Object> resendVerificationLink(@Field("email") String emailId
            , @HeaderMap Map<String, String> map);

    @GET("classes/Promos")
    Call<BannerData> getPromos(@HeaderMap Map<String, String> map);

/*
    @POST("functions/restaurantSubscriptionData")
    Call<RestSubscribers> getSubscribers(@HeaderMap Map<String, String> map, @Query("restaurant_id") String id);
*/

    @GET("classes/FavoriteRestaurant")
    Call<Object> getSubscribers(@HeaderMap Map<String, String> map, @Query("restaurant_id") ParseObject parseObject,@Query("count") int count);

    @POST("functions/redeemCoupon")
    Call<CouponList> verifyUserPromo(@Query("code") String code,@Body Map<String,Object> latlong);

    @POST("functions/useCoupon")
    Call<Object> scanCoupons(@Query("coupon_id") String id);

    @POST("functions/getAllCoupons")
    Call<CouponList> getCoupons(@Body Map<String,Object> latlong);

    @POST("functions/getUserPayments")
    Call<ReceiptData> getUserPayment();
}
