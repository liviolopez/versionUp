package dev.all4.versionUp.ui.vmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.all4.versionUp.domain.Repository

/**
 * Created by Livio Lopez on 11/11/20.
 */
class VMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}