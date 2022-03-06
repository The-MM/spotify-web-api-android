package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeaturedPlaylists(
    val message: String? = null,
    val playlists: Pager<PlaylistSimple>? = null,
) : Parcelable
