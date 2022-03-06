package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistTracksInformation(
    val href: String? = null,
    val total: Int = 0,
) : Parcelable
