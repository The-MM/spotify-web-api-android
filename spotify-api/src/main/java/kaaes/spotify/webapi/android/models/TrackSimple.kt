package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class TrackSimple(
    val artists: List<ArtistSimple>? = null,
    val available_markets: List<String>? = null,
    val is_playable: Boolean? = null,
    val linked_from: LinkedTrack? = null,
    val disc_number: Int = 0,
    val duration_ms: Long = 0,
    val explicit: Boolean? = null,
    val external_urls: Map<String?, String?>? = null,
    val href: String? = null,
    val id: String? = null,
    val name: String? = null,
    val preview_url: String? = null,
    val track_number: Int = 0,
    val type: String? = null,
    val uri: String? = null,
) : Parcelable
