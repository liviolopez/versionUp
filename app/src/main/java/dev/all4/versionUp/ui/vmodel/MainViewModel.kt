package dev.all4.versionUp.ui.vmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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
            emit(Resource.Exception<Nothing>(e))
        }
    }
}