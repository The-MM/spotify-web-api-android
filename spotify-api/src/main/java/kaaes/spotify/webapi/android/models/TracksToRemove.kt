package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TracksToRemove(
    val tracks: List<TrackToRemove?>? = null,
) : Parcelable
