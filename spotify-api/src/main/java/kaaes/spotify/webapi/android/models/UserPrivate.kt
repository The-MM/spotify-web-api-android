package kaaes.spotify.webapi.android.models

import kotlinx.parcelize.Parcelize

/**
 * [User object (private) model](https://developer.spotify.com/web-api/object-model/#user-object-private)
 */
@Parcelize
data class UserPrivate(
    val birthdate: String? = null,
    val country: String? = null,
    val email: String? = null,
    val product: String? = null,
) : UserPublic()
