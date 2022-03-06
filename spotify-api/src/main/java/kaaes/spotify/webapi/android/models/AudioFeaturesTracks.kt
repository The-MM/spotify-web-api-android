package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioFeaturesTracks(
    val audio_features: List<AudioFeaturesTrack>? = null,
) : Parcelable
