package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TracksPager(
    val tracks: Pager<Track>? = null,
) : Parcelable
