package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistsPager(
    val artists: Pager<Artist>? = null,
) : Parcelable
