package dev.all4.versionUp.ui.vmodel

import androidx.lifecycle.*
import dev.all4.versionUp.domain.Repository
import dev.all4.versionUp.vo.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by Livio Lopez on 11/11/20.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {
    val liveDataAnything = liveData(Dispatchers.IO) {
        emit(Resource.Loading(true))
        try{
            emit(repository.getAnythingList())
        } catch (e: Throwable) {
            emit(Resource.Failure<Nothing>(e))
        }
    }

    val liveDataMealCategory = liveData(Dispatchers.IO) {
        emit(Resource.Loading(true))
        try{
            emit(repository.getMealCategoryList())
        } catch (e: Throwable) {
            emit(Resource.Failure<Nothing>(e))
        }
    }

    fun liveDataMealsByCategory(mealCategoryName: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(true))
        try{
            emit(repository.getMealsByCategory(mealCategoryName))
        } catch (e: Throwable) {
            emit(Resource.Failure<Nothing>(e))
        }
    }

    fun liveDataMeal(mealId: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(true))
        try{
            emit(repository.getMeal(mealId))
        } catch (e: Throwable) {
            emit(Resource.Failure<Nothing>(e))
        }
    }

    // Search Live Data
    private var mealNameQuery = MutableLiveData<String>()
    fun searchMeal(query: String) { mealNameQuery.value = query }

    val liveDataMealsByName = mealNameQuery.distinctUntilChanged().switchMap { query ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading(true))
            try {
                emit(repository.getMealsByName(query))
            } catch (e: Throwable) {
                emit(Resource.Failure<Nothing>(e))
            }
        }
    }
}