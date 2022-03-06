package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val href: String? = null,
    val icons: List<Image>? = null,
    val id: String? = null,
    val name: String? = null,
) : Parcelable
