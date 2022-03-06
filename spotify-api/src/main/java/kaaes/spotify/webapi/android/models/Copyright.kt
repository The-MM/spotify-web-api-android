package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Copyright object model](https://developer.spotify.com/web-api/object-model/#copyright-object)
 */
@Parcelize
data class Copyright(
    val text: String? = null,
    val type: String? = null,
) : Parcelable
