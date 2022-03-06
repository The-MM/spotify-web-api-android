package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SnapshotId(
    val snapshot_id: String? = null,
) : Parcelable
