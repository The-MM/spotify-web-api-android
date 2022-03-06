package kaaes.spotify.webapi.android.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    val error: ErrorDetails? = null,
) : Parcelable
