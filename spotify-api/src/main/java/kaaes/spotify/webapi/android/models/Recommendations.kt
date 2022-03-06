package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recommendations(
    val seeds: List<Seed>? = null,
    val tracks: List<Track>? = null,
) : Parcelable
