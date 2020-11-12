package dev.all4.versionUp.domain

import dev.all4.versionUp.data.DataSource
import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.vo.Resource

/**
 * Created by Livio Lopez on 11/11/20.
 */

class RepositoryImpl(private val dataSource: DataSource): Repository {
    override fun getAnythingList(): Resource<List<Anything>> = dataSource.fetchAnythingList
}