package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Artists(
    val artists: List<Artist>? = null,
) : Parcelable
