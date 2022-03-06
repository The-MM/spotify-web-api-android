package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AlbumsPager(
    val albums: Pager<AlbumSimple>? = null,
) : Parcelable
