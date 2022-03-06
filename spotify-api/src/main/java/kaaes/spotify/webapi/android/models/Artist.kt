package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [Artist object model](https://developer.spotify.com/web-api/object-model/#artist-object-full)
 */
@Parcelize
data class Artist(
    val followers: Followers? = null,
    val popularity: Int? = null,
    val images: List<Image>? = null,
    val genres: List<String>? = null,
) : ArtistSimple()
