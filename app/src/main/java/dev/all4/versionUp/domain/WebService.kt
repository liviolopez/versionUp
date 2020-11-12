package dev.all4.versionUp.domain

import dev.all4.versionUp.data.model.MealCategoryList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Livio Lopez on 11/12/20.
 */
interface WebService {
    @GET("categories.php")
    suspend fun getCategories(): MealCategoryList

    @GET("filter.php?c=Seafood")
    suspend fun getMealOfCategory(@Query("category") category:String)
}