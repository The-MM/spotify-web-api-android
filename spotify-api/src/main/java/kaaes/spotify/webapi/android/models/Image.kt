package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Image object model](https://developer.spotify.com/web-api/object-model/#image-object)
 */
@Parcelize
data class Image(
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null,
) : Parcelable
