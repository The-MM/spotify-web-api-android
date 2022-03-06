package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [Track object model](https://developer.spotify.com/web-api/object-model/#track-object-full)
 */
@Parcelize
data class Track(
    val album: AlbumSimple? = null,
    val external_ids: Map<String?, String?>? = null,
    val popularity: Int? = null,
) : TrackSimple()
