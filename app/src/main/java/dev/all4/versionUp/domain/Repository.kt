package dev.all4.versionUp.domain

import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.vo.Resource

/**
 * Created by Livio Lopez on 11/11/20.
 */
interface Repository {
    fun getAnythingList(): Resource<List<Anything>>

    suspend fun getMealCategoryList(): Resource<List<MealCategory>>
    suspend fun getMealsByCategory(mealCategoryName: String): Resource<List<Meal>>
    suspend fun getMealsByName(mealNameQuery: String): Resource<List<Meal>>
    suspend fun getMeal(mealId: String): Resource<Meal>
}