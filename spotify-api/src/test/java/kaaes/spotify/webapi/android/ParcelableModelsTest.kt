package kaaes.spotify.webapi.android

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.GsonBuilder
import kaaes.spotify.webapi.android.TestUtils.readTestData
import kaaes.spotify.webapi.android.models.Album
import kaaes.spotify.webapi.android.models.AlbumSimple
import kaaes.spotify.webapi.android.models.Albums
import kaaes.spotify.webapi.android.models.AlbumsPager
import kaaes.spotify.webapi.android.models.Artist
import kaaes.spotify.webapi.android.models.ArtistSimple
import kaaes.spotify.webapi.android.models.Artists
import kaaes.spotify.webapi.android.models.ArtistsPager
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack
import kaaes.spotify.webapi.android.models.AudioFeaturesTracks
import kaaes.spotify.webapi.android.models.CategoriesPager
import kaaes.spotify.webapi.android.models.Category
import kaaes.spotify.webapi.android.models.Copyright
import kaaes.spotify.webapi.android.models.ErrorDetails
import kaaes.spotify.webapi.android.models.FeaturedPlaylists
import kaaes.spotify.webapi.android.models.Followers
import kaaes.spotify.webapi.android.models.Image
import kaaes.spotify.webapi.android.models.LinkedTrack
import kaaes.spotify.webapi.android.models.NewReleases
import kaaes.spotify.webapi.android.models.Playlist
import kaaes.spotify.webapi.android.models.PlaylistFollowPrivacy
import kaaes.spotify.webapi.android.models.PlaylistSimple
import kaaes.spotify.webapi.android.models.PlaylistTrack
import kaaes.spotify.webapi.android.models.PlaylistTracksInformation
import kaaes.spotify.webapi.android.models.PlaylistsPager
import kaaes.spotify.webapi.android.models.Recommendations
import kaaes.spotify.webapi.android.models.SavedTrack
import kaaes.spotify.webapi.android.models.Seed
import kaaes.spotify.webapi.android.models.SeedsGenres
import kaaes.spotify.webapi.android.models.SnapshotId
import kaaes.spotify.webapi.android.models.Track
import kaaes.spotify.webapi.android.models.TrackSimple
import kaaes.spotify.webapi.android.models.TrackToRemove
import kaaes.spotify.webapi.android.models.TrackToRemoveWithPosition
import kaaes.spotify.webapi.android.models.Tracks
import kaaes.spotify.webapi.android.models.TracksPager
import kaaes.spotify.webapi.android.models.TracksToRemove
import kaaes.spotify.webapi.android.models.TracksToRemoveWithPosition
import kaaes.spotify.webapi.android.models.UserPrivate
import kaaes.spotify.webapi.android.models.UserPublic
import org.fest.assertions.api.Assertions
import org.fest.util.Arrays
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ParcelableModelsTest {

    private val modelClasses: List<Class<out Parcelable>> =
        listOf(
            Album::class.java,
            Albums::class.java,
            AlbumSimple::class.java,
            AlbumsPager::class.java,
            Artist::class.java,
            Artists::class.java,
            ArtistSimple::class.java,
            ArtistsPager::class.java,
            AudioFeaturesTrack::class.java,
            AudioFeaturesTracks::class.java,
            CategoriesPager::class.java,
            Category::class.java,
            Copyright::class.java,
            ErrorDetails::class.java,
            FeaturedPlaylists::class.java,
            Followers::class.java,
            Image::class.java,
            LinkedTrack::class.java,
            NewReleases::class.java,
            Playlist::class.java,
            PlaylistFollowPrivacy::class.java,
            PlaylistSimple::class.java,
            PlaylistsPager::class.java,
            PlaylistTrack::class.java,
            PlaylistTracksInformation::class.java,
            Recommendations::class.java,
            Seed::class.java,
            SeedsGenres::class.java,
            SavedTrack::class.java,
            SnapshotId::class.java,
            Track::class.java,
            Tracks::class.java,
            TrackSimple::class.java,
            TracksPager::class.java,
            TrackToRemove::class.java,
            TrackToRemoveWithPosition::class.java,
            TracksToRemove::class.java,
            TracksToRemoveWithPosition::class.java,
            UserPrivate::class.java,
            UserPublic::class.java
        )

    @Test
    @Throws(IllegalAccessException::class, InstantiationException::class, NoSuchFieldException::class)
    fun allParcelables() {
        val populator = ModelPopulator("CREATOR", "\$jacocoData")
        for (modelClass in modelClasses) {
            val instance = populator.populateWithRandomValues(modelClass)
            testSingleParcelable(instance)
            testParcelableArray(instance)

            /* Trick to increase code coverage */
            instance.describeContents()
            (modelClass.getField("CREATOR")[null] as Parcelable.Creator<*>).newArray(13)
        }
    }

    private fun <T : Parcelable> testSingleParcelable(underTest: T) {
        val parcel = Parcel.obtain()
        parcel.writeParcelable(underTest, 0)
        parcel.setDataPosition(0)
        val fromParcel: T? = parcel.readParcelable(underTest::class.java.classLoader)
        ModelAssert.assertThat(fromParcel).isEqualByComparingFields(underTest)
    }

    private fun <T : Parcelable> testParcelableArray(underTest: T) {
        val parcel = Parcel.obtain()
        parcel.writeParcelableArray(Arrays.array(underTest, underTest), 0)
        parcel.setDataPosition(0)
        val fromParcel = parcel.readParcelableArray(underTest.javaClass.classLoader)
        Assertions.assertThat(fromParcel).hasSize(2)
        Assertions.assertThat(fromParcel!![0]).isEqualsToByComparingFields(underTest)
        Assertions.assertThat(fromParcel[1]).isEqualsToByComparingFields(underTest)
    }

    @Test
    fun artistsAreGoodParcelableCitizen() {
        val body = readTestData("artists.json")
        val fixture = GsonBuilder().create().fromJson(body, Artists::class.java)
        testSingleParcelable(fixture)
    }

    @Test
    fun albumsAreGoodParcelableCitizen() {
        val body = readTestData("albums.json")
        val fixture = GsonBuilder().create().fromJson(body, Albums::class.java)
        testSingleParcelable(fixture)
    }

    @Test
    fun tracksAreGoodParcelableCitizen() {
        val body = readTestData("tracks.json")
        val fixture = GsonBuilder().create().fromJson(body, Tracks::class.java)
        testSingleParcelable(fixture)
    }

    @Test
    fun usersAreGoodParcelableCitizens() {
        var body = readTestData("user.json")
        val userPublic = GsonBuilder().create().fromJson(body, UserPublic::class.java)
        testSingleParcelable(userPublic)
        body = readTestData("current-user.json")
        val userPrivate = GsonBuilder().create().fromJson(body, UserPrivate::class.java)
        testSingleParcelable(userPrivate)
    }
}