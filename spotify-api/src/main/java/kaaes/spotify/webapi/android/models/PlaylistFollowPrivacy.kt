package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistFollowPrivacy(
    @SerializedName("public") val is_public: Boolean? = null,
) : Parcelable
