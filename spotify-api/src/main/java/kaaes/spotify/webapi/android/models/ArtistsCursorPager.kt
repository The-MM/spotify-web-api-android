package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistsCursorPager(
    val artists: CursorPager<Artist>? = null,
) : Parcelable
