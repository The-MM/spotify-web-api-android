package kaaes.spotify.webapi.android

import retrofit.RequestInterceptor
import retrofit.RequestInterceptor.RequestFacade
import retrofit.RestAdapter
import retrofit.android.MainThreadExecutor
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Creates and configures a REST adapter for Spotify Web API.
 *
 * Basic usage:
 * SpotifyApi wrapper = new SpotifyApi();
 *
 * Setting access token is optional for certain endpoints
 * so if you know you'll only use the ones that don't require authorisation
 * you can skip this step:
 * wrapper.setAccessToken(authenticationResponse.getAccessToken());
 *
 * SpotifyService spotify = wrapper.getService();
 *
 * Album album = spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
 */
class SpotifyApi {
    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    private inner class WebApiAuthenticator : RequestInterceptor {
        override fun intercept(request: RequestFacade) {
            if (accessToken != null) {
                request.addHeader("Authorization", "Bearer $accessToken")
            }
        }
    }

    /**
     * @return The SpotifyApi instance
     */
    val service: SpotifyService
    private var accessToken: String? = null

    /**
     * Create instance of SpotifyApi with given executors.
     *
     * @param httpExecutor executor for http request. Cannot be null.
     * @param callbackExecutor executor for callbacks. If null is passed than the same
     * thread that created the instance is used.
     */
    constructor(httpExecutor: Executor, callbackExecutor: Executor) {
        service = init(httpExecutor, callbackExecutor)
    }

    private fun init(httpExecutor: Executor, callbackExecutor: Executor): SpotifyService {
        val restAdapter = RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.BASIC)
            .setExecutors(httpExecutor, callbackExecutor)
            .setEndpoint(SPOTIFY_WEB_API_ENDPOINT)
            .setRequestInterceptor(WebApiAuthenticator())
            .build()
        return restAdapter.create(SpotifyService::class.java)
    }

    /**
     * New instance of SpotifyApi,
     * with single thread executor both for http and callbacks.
     */
    constructor() {
        val httpExecutor: Executor = Executors.newSingleThreadExecutor()
        val callbackExecutor = MainThreadExecutor()
        service = init(httpExecutor, callbackExecutor)
    }

    /**
     * Sets access token on the wrapper.
     * Use to set or update token with the new value.
     * If you want to remove token set it to null.
     *
     * @param accessToken The token to set on the wrapper.
     * @return The instance of the wrapper.
     */
    fun setAccessToken(accessToken: String?): SpotifyApi {
        this.accessToken = accessToken
        return this
    }

    companion object {
        /**
         * Main Spotify Web API endpoint
         */
        const val SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1"
    }
}