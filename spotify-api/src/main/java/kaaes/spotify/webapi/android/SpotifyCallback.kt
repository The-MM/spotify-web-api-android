package kaaes.spotify.webapi.android

import kaaes.spotify.webapi.android.SpotifyError
import retrofit.Callback
import retrofit.RetrofitError

/**
 * A convenience object converting [retrofit.RetrofitError]s to [SpotifyError]s
 * in the error callbacks.
 *
 * <pre>`spotify.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>() {
 * public void success(Pager&lt;SavedTrack&gt; savedTrackPager, Response response) {
 * // handle successful response
 * }
 *
 * public void failure(SpotifyError error) {
 * // handle error
 * }
 * });
`* </pre>
 *
 * @param T expected response type
 * @see retrofit.Callback
 */
abstract class SpotifyCallback<T> : Callback<T> {
    abstract fun failure(error: SpotifyError?)
    override fun failure(error: RetrofitError) {
        failure(SpotifyError.fromRetrofitError(error))
    }
}