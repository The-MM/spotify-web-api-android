package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [User object (public) model](https://developer.spotify.com/web-api/object-model/#user-object-public)
 */
@Parcelize
open class UserPublic(
    val uri: String? = null,
    val id: String? = null,
    val display_name: String? = null,
    val external_urls: Map<String?, String?>? = null,
    val followers: Followers? = null,
    val href: String? = null,
    val images: List<Image>? = null,
    val type: String? = null,
) : Parcelable
