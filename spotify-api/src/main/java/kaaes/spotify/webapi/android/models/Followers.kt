package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Followers](https://developer.spotify.com/web-api/object-model/#followers-object)
 */
@Parcelize
data class Followers(
    val href: String? = null,
    val total: Int? = null,
) : Parcelable
