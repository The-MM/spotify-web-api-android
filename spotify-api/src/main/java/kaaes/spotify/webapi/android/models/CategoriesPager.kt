package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesPager(
    val categories: Pager<Category>? = null,
) : Parcelable
