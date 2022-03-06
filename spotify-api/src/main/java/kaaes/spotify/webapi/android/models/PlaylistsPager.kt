package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistsPager(
    val playlists: Pager<PlaylistSimple>? = null,
) : Parcelable
