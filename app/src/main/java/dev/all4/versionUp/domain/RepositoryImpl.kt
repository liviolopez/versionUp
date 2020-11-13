package dev.all4.versionUp.domain

import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.data.model.Meal
import dev.all4.versionUp.data.model.MealCategory
import dev.all4.versionUp.vo.Resource

/**
 * Created by Livio Lopez on 11/11/20.
 */

class RepositoryImpl(private val dataSource: DataSource): Repository {
    override fun getAnythingList(): Resource<List<Anything>> = dataSource.fetchAnythingList

    override suspend fun getMealCategoryList(): Resource<List<MealCategory>> = dataSource.fetchCategories()

    override suspend fun getMealsByCategory(mealCategoryName: String): Resource<List<Meal>> = dataSource.fetchMealsByCategory(mealCategoryName)
    override suspend fun getMealsByName(mealNameQuery: String): Resource<List<Meal>> = dataSource.fetchMealsByName(mealNameQuery)
    override suspend fun getMeal(mealId: String): Resource<Meal> = dataSource.fetchMealById(mealId)
}