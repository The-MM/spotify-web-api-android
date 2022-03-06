package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class AlbumSimple(
    val album_type: String? = null,
    val available_markets: List<String>? = null,
    val external_urls: Map<String, String>? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val type: String? = null,
    val uri: String? = null,
) : Parcelable
