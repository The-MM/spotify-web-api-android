package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TracksToRemoveWithPosition(
    val tracks: List<TrackToRemoveWithPosition?>? = null,
) : Parcelable
