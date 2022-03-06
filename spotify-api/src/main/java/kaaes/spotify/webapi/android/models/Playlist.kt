package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [Playlist object model](https://developer.spotify.com/web-api/object-model/#playlist-object-full)
 */
@Parcelize
data class Playlist(
    val description: String? = null,
    val followers: Followers? = null,
    val tracks: Pager<PlaylistTrack>? = null,
) : PlaylistBase()
