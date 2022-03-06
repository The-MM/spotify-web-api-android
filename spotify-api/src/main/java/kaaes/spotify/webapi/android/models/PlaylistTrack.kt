package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Playlist track object model](https://developer.spotify.com/web-api/object-model/#playlist-track-object)
 */
@Parcelize
data class PlaylistTrack(
    val added_at: String? = null,
    val added_by: UserPublic? = null,
    val track: Track? = null,
    val is_local: Boolean? = null,
) : Parcelable
