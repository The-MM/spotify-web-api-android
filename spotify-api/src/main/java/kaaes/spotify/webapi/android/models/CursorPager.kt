package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [Cursor-based paging object model](https://developer.spotify.com/web-api/object-model/#cursor-based-paging-object)
 *
 * @param T expected object that is paged
 */
@Parcelize
data class CursorPager<T>(
    val href: String? = null,
    val items: List<T?>? = null,
    val limit: Int = 0,
    val next: String? = null,
    val cursors: Cursor? = null,
    val total: Int = 0,
) : Parcelable
