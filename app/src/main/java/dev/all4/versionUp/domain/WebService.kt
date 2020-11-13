package dev.all4.versionUp.domain

import dev.all4.versionUp.data.model.MealCategoryList
import dev.all4.versionUp.data.model.MealsList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Livio Lopez on 11/12/20.
 */
interface WebService {
    @GET("categories.php")
    suspend fun getCategories(): MealCategoryList

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query(value = "c") category:String): MealsList

    @GET("search.php")
    suspend fun getMealsByName(@Query(value = "s") mealNameQuery:String): MealsList

    @GET("lookup.php")
    suspend fun getMealById(@Query(value = "i") mealId:String): MealsList
}