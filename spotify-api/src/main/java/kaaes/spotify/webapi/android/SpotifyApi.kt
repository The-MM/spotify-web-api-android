package kaaes.spotify.webapi.android

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
 *
 * @constructor Create instance of SpotifyApi with given executors.
 * @param httpExecutor executor for http request. If null is passed then a new single thread executor is used.
 * @param callbackExecutor executor for callbacks. If null is passed then the same thread that created the instance is used.
 */
class SpotifyApi(httpExecutor: Executor? = null, callbackExecutor: Executor? = null) {

    /**
     * The SpotifyApi instance
     */
    val service: SpotifyService
    private var accessToken: String? = null

    init {
        val httpExec = httpExecutor ?: Executors.newSingleThreadExecutor()
        val callbackExec = callbackExecutor ?: MainThreadExecutor()
        service = init(httpExec, callbackExec)
    }

    private fun initOkHttp(httpExecutor: Executor) =
        OkHttpClient.Builder()
            .addInterceptor(WebApiAuthenticator())
            .dispatcher(httpExecutor)
            .build()

    private fun init(httpExecutor: Executor, callbackExecutor: Executor): SpotifyService =
        Retrofit.Builder()
            .client(initOkHttp(httpExecutor))
            .callbackExecutor(callbackExecutor)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SPOTIFY_WEB_API_ENDPOINT)
            .build()
            .create(SpotifyService::class.java)

    /**
     * Sets access token on the wrapper.
     * Use to set or update token with the new value.
     * If you want to remove token set it to null.
     *
     * @param accessToken The token to set on the wrapper.
     * @return The instance of the wrapper.
     */
    fun setAccessToken(accessToken: String?): SpotifyApi = also { this.accessToken = accessToken }

    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    private inner class WebApiAuthenticator : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .apply {
                    if (accessToken != null) {
                        addHeader("Authorization", "Bearer $accessToken")
                    }
                }
                .build()
            return chain.proceed(request)
        }
    }

    companion object {
        /**
         * Main Spotify Web API endpoint
         */
        const val SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1/"
    }
}