package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [Playlist object model (simplified)](https://developer.spotify.com/web-api/object-model/#playlist-object-simplified)
 */
@Parcelize
data class PlaylistSimple(
    val tracks: PlaylistTracksInformation? = null,
) : PlaylistBase()
