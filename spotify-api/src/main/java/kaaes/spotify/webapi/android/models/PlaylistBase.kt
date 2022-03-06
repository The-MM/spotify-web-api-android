package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Base class for [kaaes.spotify.webapi.android.models.Playlist] and
 * [kaaes.spotify.webapi.android.models.PlaylistSimple]
 */
abstract class PlaylistBase(
    val collaborative: Boolean? = null,
    val external_urls: Map<String?, String?>? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val owner: UserPublic? = null,
    @SerializedName("public") val is_public: Boolean? = null,
    val snapshot_id: String? = null,
    val type: String? = null,
    val uri: String? = null,
) : Parcelable
