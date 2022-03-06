package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Paging object model](https://developer.spotify.com/web-api/object-model/#paging-object)
 *
 * @param T expected object that is paged
 */
@Parcelize
data class Pager<T>(
    val href: String? = null,
    val items: List<T?>? = null,
    val limit: Int = 0,
    val next: String? = null,
    val offset: Int = 0,
    val previous: String? = null,
    val total: Int = 0,
) : Parcelable
