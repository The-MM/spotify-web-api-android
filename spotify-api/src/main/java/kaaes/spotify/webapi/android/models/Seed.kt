package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seed(
    val afterFilteringSize: Int = 0,
    val afterRelinkingSize: Int = 0,
    val href: String? = null,
    val id: String? = null,
    val initialPoolSize: Int = 0,
    val type: String? = null,
) : Parcelable
