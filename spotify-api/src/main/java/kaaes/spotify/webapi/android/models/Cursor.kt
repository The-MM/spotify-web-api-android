package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Cursor](https://developer.spotify.com/web-api/object-model/#cursor-object)
 */
@Parcelize
data class Cursor(
    val after: String? = null,
) : Parcelable
