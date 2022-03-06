package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Error object model](https://developer.spotify.com/web-api/object-model/#error-object)
 */
@Parcelize
data class ErrorDetails(
    val status: Int = 0,
    val message: String? = null,
) : Parcelable
