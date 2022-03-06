package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [Album object model](https://developer.spotify.com/web-api/object-model/#album-object-full)
 */
@Parcelize
data class Album(
    val artists: List<ArtistSimple>? = null,
    val copyrights: List<Copyright>? = null,
    val external_ids: Map<String?, String?>? = null,
    val genres: List<String>? = null,
    val popularity: Int? = null,
    val release_date: String? = null,
    val release_date_precision: String? = null,
    val tracks: Pager<TrackSimple>? = null,
) : AlbumSimple()
