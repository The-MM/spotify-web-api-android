package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Audio Features Object](https://developer.spotify.com/web-api/object-model/#audio-features-object)
 */
@Parcelize
data class AudioFeaturesTrack(
    val acousticness: Float = 0f,
    val analysis_url: String? = null,
    val danceability: Float = 0f,
    val duration_ms: Int = 0,
    val energy: Float = 0f,
    val id: String? = null,
    val instrumentalness: Float = 0f,
    val key: Int = 0,
    val liveness: Float = 0f,
    val loudness: Float = 0f,
    val mode: Int = 0,
    val speechiness: Float = 0f,
    val tempo: Float = 0f,
    val time_signature: Int = 0,
    val track_href: String? = null,
    val type: String? = null,
    val uri: String? = null,
    val valence: Float = 0f,
) : Parcelable
