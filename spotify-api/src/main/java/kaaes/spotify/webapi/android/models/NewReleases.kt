package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewReleases(
    val albums: Pager<AlbumSimple>? = null,
) : Parcelable
