package dev.all4.versionUp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Livio Lopez on 11/12/20.
 */

data class MealCategoryList(
    @SerializedName("categories")
    val categories: List<MealCategory>
)

@Parcelize
data class MealCategory(
    @SerializedName("idCategory")
    val id: String = "",

    @SerializedName("strCategory")
    val name: String = "",

    @SerializedName("strCategoryDescription")
    val description: String = "",

    @SerializedName("strCategoryThumb")
    val thumbnail: String = ""
): Parcelable