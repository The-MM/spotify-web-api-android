package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Albums(
    val albums: List<Album>? = null,
) : Parcelable
