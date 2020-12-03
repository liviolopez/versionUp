package dev.all4.versionUp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Livio Lopez on 11/11/20.
 */

@Parcelize
data class Anything(
    val thumbnail: String = "",
    val name: String = "",
    val description: String = "",
):Parcelable