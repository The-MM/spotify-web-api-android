package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Saved track object model](https://developer.spotify.com/web-api/object-model/#saved-track-object)
 */
@Parcelize
data class SavedTrack(
    val added_at: String? = null,
    val track: Track? = null,
) : Parcelable
