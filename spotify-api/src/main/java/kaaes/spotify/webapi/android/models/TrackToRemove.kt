package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackToRemove(
    val uri: String? = null,
) : Parcelable
