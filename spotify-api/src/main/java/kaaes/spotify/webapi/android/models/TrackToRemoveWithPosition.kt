package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackToRemoveWithPosition(
    val uri: String? = null,
    val positions: List<Int?>? = null,
) : Parcelable
