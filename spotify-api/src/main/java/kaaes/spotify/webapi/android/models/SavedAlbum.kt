package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Saved album object model](https://developer.spotify.com/web-api/object-model/#saved-album-object)
 */
@Parcelize
data class SavedAlbum(
    val added_at: String? = null,
    val album: Album? = null,
) : Parcelable
