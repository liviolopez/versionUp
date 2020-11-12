package dev.all4.versionUp.data

import dev.all4.versionUp.data.model.Anything
import dev.all4.versionUp.vo.Resource
import dev.all4.versionUp.vo.RetrofitClient

/**
 * Created by Livio Lopez on 11/11/20.
 */

class DataSource {
    // Local Data
    val fetchAnythingList = Resource.Success(listOf(
                                Anything(
                                    "https://upload.wikimedia.org/wikipedia/commons/d/d9/Collage_of_Nine_Dogs.jpg",
                                    "Dog",
                                    "The dog (Canis familiaris when considered a distinct species or Canis lupus familiaris when considered a subspecies of the wolf) is a domesticated carnivore of the family Canidae. It is part of the wolf-like canids, and is the most widely abundant terrestrial carnivore."),
                                Anything(
                                    "https://static.sciencelearn.org.nz/images/images/000/003/586/embed/TREES_ART_03_What_is_a_tree_PartsOfTree.jpeg?1530567716",
                                    "Tree",
                                    "Trees are all plants and carry out the life processes that all plants share. However, trees are not actually a scientific group of their own. Trees may be cone-bearing plants (gymnosperms), flowering plants (angiosperms) or ferns.")
                            ))


    // Retrofit - Food API
    suspend fun fetchCategories() = Resource.Success(RetrofitClient.webService.getCategories().categories)
}