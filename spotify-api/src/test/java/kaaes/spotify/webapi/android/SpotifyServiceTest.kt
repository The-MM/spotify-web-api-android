package kaaes.spotify.webapi.android

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase
import kaaes.spotify.webapi.android.models.Album
import kaaes.spotify.webapi.android.models.Albums
import kaaes.spotify.webapi.android.models.AlbumsPager
import kaaes.spotify.webapi.android.models.Artist
import kaaes.spotify.webapi.android.models.Artists
import kaaes.spotify.webapi.android.models.ArtistsCursorPager
import kaaes.spotify.webapi.android.models.ArtistsPager
import kaaes.spotify.webapi.android.models.CategoriesPager
import kaaes.spotify.webapi.android.models.Category
import kaaes.spotify.webapi.android.models.FeaturedPlaylists
import kaaes.spotify.webapi.android.models.NewReleases
import kaaes.spotify.webapi.android.models.Pager
import kaaes.spotify.webapi.android.models.Playlist
import kaaes.spotify.webapi.android.models.PlaylistFollowPrivacy
import kaaes.spotify.webapi.android.models.PlaylistSimple
import kaaes.spotify.webapi.android.models.PlaylistTrack
import kaaes.spotify.webapi.android.models.PlaylistsPager
import kaaes.spotify.webapi.android.models.SnapshotId
import kaaes.spotify.webapi.android.models.Track
import kaaes.spotify.webapi.android.models.TrackToRemove
import kaaes.spotify.webapi.android.models.TrackToRemoveWithPosition
import kaaes.spotify.webapi.android.models.Tracks
import kaaes.spotify.webapi.android.models.TracksPager
import kaaes.spotify.webapi.android.models.TracksToRemove
import kaaes.spotify.webapi.android.models.TracksToRemoveWithPosition
import kaaes.spotify.webapi.android.models.UserPrivate
import kaaes.spotify.webapi.android.models.UserPublic
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.Matchers
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import retrofit.RestAdapter
import retrofit.client.Client
import retrofit.client.Request
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

@RunWith(RobolectricTestRunner::class)
class SpotifyServiceTest {
    private lateinit var spotifyService: SpotifyService
    private lateinit var mockClient: Client
    private lateinit var gson: Gson

    private inner class MatchesId(private val mId: String?) : ArgumentMatcher<Request?>() {
        override fun matches(request: Any): Boolean = try {
            (request as Request).url.contains(URLEncoder.encode(mId, "UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            false
        }
    }

    @Before
    fun setUp() {
        mockClient = Mockito.mock(Client::class.java)
        val restAdapter = RestAdapter.Builder()
            .setClient(mockClient)
            .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
            .build()
        spotifyService = restAdapter.create(SpotifyService::class.java)
        gson = GsonBuilder().create()
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetTrackData() {
        val body = TestUtils.readTestData("track.json")
        val fixture = gson.fromJson(body, Track::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Track::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(fixture.id)))).thenReturn(response)

        val track = spotifyService.getTrack(fixture.id)
        compareJSONWithoutNulls(body, track)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetMultipleTrackData() {
        val body = TestUtils.readTestData("tracks.json")
        val fixture = gson.fromJson(body, Tracks::class.java)
        var ids: String? = ""
        for (i in fixture.tracks!!.indices) {
            if (i > 0) {
                ids += ","
            }
            ids += fixture.tracks!![i].id
        }

        val response = TestUtils.getResponseFromModel(fixture, Tracks::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(ids)))).thenReturn(response)

        val tracks = spotifyService.getTracks(ids)
        compareJSONWithoutNulls(body, tracks)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetAlbumData() {
        val body = TestUtils.readTestData("album.json")
        val fixture = gson.fromJson(body, Album::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Album::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(fixture.id)))).thenReturn(response)

        val album = spotifyService.getAlbum(fixture.id)
        compareJSONWithoutNulls(body, album)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetMultipleAlbumData() {
        val body = TestUtils.readTestData("albums.json")
        val fixture = gson.fromJson(body, Albums::class.java)
        var ids: String? = ""
        for (i in fixture.albums!!.indices) {
            if (i > 0) {
                ids += ","
            }
            ids += fixture.albums!![i].id
        }

        val response = TestUtils.getResponseFromModel(fixture, Albums::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(ids)))).thenReturn(response)

        val albums = spotifyService.getAlbums(ids)
        compareJSONWithoutNulls(body, albums)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetArtistData() {
        val body = TestUtils.readTestData("artist.json")
        val fixture = gson.fromJson(body, Artist::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Artist::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(fixture.id)))).thenReturn(response)

        val artist = spotifyService.getArtist(fixture.id)
        compareJSONWithoutNulls(body, artist)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetMultipleArtistData() {
        val body = TestUtils.readTestData("artists.json")
        val fixture = gson.fromJson(body, Artists::class.java)
        var ids: String? = ""
        for (i in fixture.artists!!.indices) {
            if (i > 0) {
                ids += ","
            }
            ids += fixture.artists!![i].id
        }

        val response = TestUtils.getResponseFromModel(fixture, Artists::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(ids)))).thenReturn(response)

        val artists = spotifyService.getArtists(ids)
        compareJSONWithoutNulls(body, artists)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetArtistsAlbumsData() {
        val modelType = object : TypeToken<Pager<Album?>?>() {}.type
        val artistId = "1vCWHaC5f2uS3yhpwWbIA6"
        val body = TestUtils.readTestData("artist-album.json")
        val fixture = gson.fromJson<Pager<Album>>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(artistId)))).thenReturn(response)

        val albums = spotifyService.getArtistAlbums(artistId)
        compareJSONWithoutNulls<Pager<Album>?>(body, albums)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetPlaylistData() {
        val body = TestUtils.readTestData("playlist-response.json")
        val fixture = gson.fromJson(body, Playlist::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Playlist::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val playlist = spotifyService.getPlaylist(fixture.owner!!.id, fixture.id)
        compareJSONWithoutNulls(body, playlist)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetPlaylistTracks() {
        val modelType = object : TypeToken<Pager<PlaylistTrack?>?>() {}.type
        val body = TestUtils.readTestData("playlist-tracks.json")
        val fixture = gson.fromJson<Pager<PlaylistTrack>>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val playlistTracks = spotifyService.getPlaylistTracks("test", "test")
        compareJSONWithoutNulls<Pager<PlaylistTrack>?>(body, playlistTracks)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetNewReleases() {
        val countryId = "SE"
        val limit = 5
        val body = TestUtils.readTestData("new-releases.json")
        val fixture = gson.fromJson(body, NewReleases::class.java)
        val response = TestUtils.getResponseFromModel(fixture, NewReleases::class.java)

        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return try {
                    (argument as Request).url.contains("limit=$limit") &&
                            argument.url.contains("country=" + URLEncoder.encode(countryId, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    false
                }
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options[SpotifyService.COUNTRY] = countryId
        options[SpotifyService.OFFSET] = 0
        options[SpotifyService.LIMIT] = limit
        val newReleases = spotifyService.getNewReleases(options)
        compareJSONWithoutNulls(body, newReleases)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetFeaturedPlaylists() {
        val countryId = "SE"
        val locale = "sv_SE"
        val limit = 5
        val body = TestUtils.readTestData("featured-playlists.json")
        val fixture = gson.fromJson(body, FeaturedPlaylists::class.java)

        val response = TestUtils.getResponseFromModel(fixture, FeaturedPlaylists::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return try {
                    (argument as Request).url.contains("limit=$limit") &&
                            argument.url.contains("country=" + URLEncoder.encode(countryId, "UTF-8")) &&
                            argument.url.contains("locale=" + URLEncoder.encode(locale, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    false
                }
            }
        }))).thenReturn(response)

        val map = HashMap<String?, Any?>()
        map[SpotifyService.COUNTRY] = countryId
        map[SpotifyService.LOCALE] = locale
        map[SpotifyService.OFFSET] = 0
        map[SpotifyService.LIMIT] = limit
        val featuredPlaylists = spotifyService.getFeaturedPlaylists(map)
        compareJSONWithoutNulls(body, featuredPlaylists)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetUserData() {
        val body = TestUtils.readTestData("user.json")
        val fixture = gson.fromJson(body, UserPublic::class.java)

        val response = TestUtils.getResponseFromModel(fixture, UserPublic::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(MatchesId(fixture.id)))).thenReturn(response)

        val userSimple = spotifyService.getUser(fixture.id)
        compareJSONWithoutNulls(body, userSimple)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetCurrentUserData() {
        val body = TestUtils.readTestData("current-user.json")
        val fixture = gson.fromJson(body, UserPrivate::class.java)

        val response = TestUtils.getResponseFromModel(fixture, UserPrivate::class.java)
        Mockito.`when`(mockClient.execute(Matchers.any())).thenReturn(response)

        val userPrivate = spotifyService.me
        compareJSONWithoutNulls(body, userPrivate)
    }

    @Test
    @Throws(IOException::class)
    fun shouldCheckFollowingUsers() {
        val modelType = object : TypeToken<List<Boolean?>?>() {}.type
        val body = TestUtils.readTestData("follow_is_following_users.json")
        val fixture = gson.fromJson<List<Boolean>>(body, modelType)
        val userIds = "thelinmichael,wizzler"
        val response = TestUtils.getResponseFromModel(fixture, modelType)

        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return try {
                    (argument as Request).url.contains("type=user") &&
                            argument.url.contains("ids=" + URLEncoder.encode(userIds, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    false
                }
            }
        }))).thenReturn(response)

        val result = spotifyService.isFollowingUsers(userIds)
        compareJSONWithoutNulls<Array<Boolean>?>(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldCheckFollowingArtists() {
        val modelType = object : TypeToken<List<Boolean?>?>() {}.type
        val body = TestUtils.readTestData("follow_is_following_artists.json")
        val fixture = gson.fromJson<List<Boolean>>(body, modelType)
        val artistIds = "3mOsjj1MhocRVwOejIZlTi"
        val response = TestUtils.getResponseFromModel(fixture, modelType)

        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return try {
                    (argument as Request).url.contains("type=artist") &&
                            argument.url.contains("ids=" + URLEncoder.encode(artistIds, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    false
                }
            }
        }))).thenReturn(response)

        val result = spotifyService.isFollowingArtists(artistIds)
        compareJSONWithoutNulls<Array<Boolean>?>(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldCheckFollowedArtists() {
        val body = TestUtils.readTestData("followed-artists.json")
        val fixture = gson.fromJson(body, ArtistsCursorPager::class.java)

        val response = TestUtils.getResponseFromModel(fixture, ArtistsCursorPager::class.java)
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return (argument as Request).url.contains("type=artist")
            }
        }))).thenReturn(response)

        val result = spotifyService.followedArtists
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetSearchedTracks() {
        val body = TestUtils.readTestData("search-track.json")
        val fixture = gson.fromJson(body, TracksPager::class.java)

        val response = TestUtils.getResponseFromModel(fixture, TracksPager::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val tracks = spotifyService.searchTracks("Christmas")
        compareJSONWithoutNulls(body, tracks)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetSearchedAlbums() {
        val body = TestUtils.readTestData("search-album.json")
        val fixture = gson.fromJson(body, AlbumsPager::class.java)

        val response = TestUtils.getResponseFromModel(fixture, AlbumsPager::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val result = spotifyService.searchAlbums("Christmas")
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetSearchedArtists() {
        val body = TestUtils.readTestData("search-artist.json")
        val fixture = gson.fromJson(body, ArtistsPager::class.java)

        val response = TestUtils.getResponseFromModel(fixture, ArtistsPager::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val result = spotifyService.searchArtists("Christmas")
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetSearchedPlaylists() {
        val body = TestUtils.readTestData("search-playlist.json")
        val fixture = gson.fromJson(body, PlaylistsPager::class.java)

        val response = TestUtils.getResponseFromModel(fixture, PlaylistsPager::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val result = spotifyService.searchPlaylists("Christmas")
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(IOException::class)
    fun shouldGetPlaylistFollowersContains() {
        val modelType = object : TypeToken<List<Boolean?>?>() {}.type
        val body = TestUtils.readTestData("playlist-followers-contains.json")
        val fixture = gson.fromJson<List<Boolean>>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val userIds = "thelinmichael,jmperezperez,kaees"
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                return try {
                    (argument as Request).url
                        .contains("ids=" + URLEncoder.encode(userIds, "UTF-8"))
                } catch (e: UnsupportedEncodingException) {
                    false
                }
            }
        }))).thenReturn(response)

        val requestPlaylist = TestUtils.readTestData("playlist-response.json")
        val requestFixture = gson.fromJson(requestPlaylist, Playlist::class.java)
        val result = spotifyService.areFollowingPlaylist(requestFixture.owner!!.id, requestFixture.id, userIds)
        compareJSONWithoutNulls<Array<Boolean>?>(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetCategories() {
        val modelType = object : TypeToken<CategoriesPager?>() {}.type
        val body = TestUtils.readTestData("get-categories.json")
        val fixture = gson.fromJson<CategoriesPager>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val country = "SE"
        val locale = "sv_SE"
        val offset = 1
        val limit = 2
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val requestUrl = (argument as Request).url
                return requestUrl.contains(String.format("limit=%d", limit)) &&
                        requestUrl.contains(String.format("offset=%d", offset)) &&
                        requestUrl.contains(String.format("country=%s", country)) &&
                        requestUrl.contains(String.format("locale=%s", locale))
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["offset"] = offset
        options["limit"] = limit
        options["country"] = country
        options["locale"] = locale
        val result = spotifyService.getCategories(options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetCategory() {
        val modelType = object : TypeToken<Category?>() {}.type
        val body = TestUtils.readTestData("category.json")
        val fixture = gson.fromJson<Category>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val categoryId = "mood"
        val country = "SE"
        val locale = "sv_SE"
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val requestUrl = (argument as Request).url
                return requestUrl.contains(String.format("locale=%s", locale)) &&
                        requestUrl.contains(String.format("country=%s", country))
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["country"] = country
        options["locale"] = locale
        val result = spotifyService.getCategory(categoryId, options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetPlaylistsForCategory() {
        val modelType = object : TypeToken<PlaylistsPager?>() {}.type
        val body = TestUtils.readTestData("category-playlist.json")
        val fixture = gson.fromJson<PlaylistsPager>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val categoryId = "mood"
        val country = "SE"
        val offset = 1
        val limit = 2
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val requestUrl = (argument as Request).url
                return requestUrl.contains(String.format("limit=%d", limit)) &&
                        requestUrl.contains(String.format("offset=%d", offset)) &&
                        requestUrl.contains(String.format("country=%s", country))
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["country"] = country
        options["offset"] = offset
        options["limit"] = limit
        val result = spotifyService.getPlaylistsForCategory(categoryId, options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldCreatePlaylistUsingBodyMap() {
        val modelType = object : TypeToken<Playlist?>() {}.type
        val body = TestUtils.readTestData("created-playlist.json")
        val fixture = gson.fromJson<Playlist>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val name = "Coolest Playlist"
        val isPublic = true
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"name\":\"%s\",\"public\":%b}",
                    name, isPublic)
                return request.url.endsWith(String.format("/users/%s/playlists", owner)) &&
                        JSONsContainSameData(expectedBody, body) && "POST" == request.method
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["name"] = name
        options["public"] = isPublic
        val result = spotifyService.createPlaylist(owner, options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldAddTracksToPlaylist() {
        val modelType = object : TypeToken<SnapshotId?>() {}.type
        val body = TestUtils.readTestData("snapshot-response.json")
        val fixture = gson.fromJson<SnapshotId>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        val trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb"
        val trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5"
        val position = 1
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"uris\":[\"%s\",\"%s\"]}",
                    trackUri1, trackUri2)
                return request.url.endsWith(String.format("/users/%s/playlists/%s/tracks?position=%d",
                    owner, playlistId, position)) &&
                        JSONsContainSameData(expectedBody, body) && "POST" == request.method
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        val trackUris = listOf(trackUri1, trackUri2)
        options["uris"] = trackUris
        val queryParameters: MutableMap<String?, Any?> = HashMap()
        queryParameters["position"] = position.toString()
        val result = spotifyService.addTracksToPlaylist(owner, playlistId, queryParameters, options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldRemoveTracksFromPlaylist() {
        val modelType = object : TypeToken<SnapshotId?>() {}.type
        val body = TestUtils.readTestData("snapshot-response.json")
        val fixture = gson.fromJson<SnapshotId>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        val trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb"
        val trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5"
        val trackObject1 = TrackToRemove(trackUri1)
        val trackObject2 = TrackToRemove(trackUri2)
        val ttr = TracksToRemove(listOf(trackObject1, trackObject2))
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"tracks\":[{\"uri\":\"%s\"},{\"uri\":\"%s\"}]}",
                    trackUri1, trackUri2)
                return request.url.endsWith(String.format("/users/%s/playlists/%s/tracks",
                    owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) && "DELETE" == request.method
            }
        }))).thenReturn(response)

        val result = spotifyService.removeTracksFromPlaylist(owner, playlistId, ttr)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldRemoveTracksFromPlaylistSpecifyingPositions() {
        val modelType = object : TypeToken<SnapshotId?>() {}.type
        val body = TestUtils.readTestData("snapshot-response.json")
        val fixture = gson.fromJson<SnapshotId>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        val trackUri1 = "spotify:track:76lT30VRv09h5MQp5snmsb"
        val trackUri2 = "spotify:track:2KCmalBTv3SiYxvpKrXmr5"
        val trackObject1 = TrackToRemoveWithPosition(trackUri1, listOf(0, 3))
        val trackObject2 = TrackToRemoveWithPosition(trackUri2,  listOf(1))
        val ttrwp = TracksToRemoveWithPosition(listOf(trackObject1, trackObject2))
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"tracks\":[{\"uri\":\"%s\",\"positions\":[0,3]},{\"uri\":\"%s\",\"positions\":[1]}]}",
                    trackUri1, trackUri2)
                return request.url.endsWith(String.format("/users/%s/playlists/%s/tracks",
                    owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) && "DELETE" == request.method
            }
        }))).thenReturn(response)

        val result = spotifyService.removeTracksFromPlaylist(owner, playlistId, ttrwp)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldChangePlaylistDetails() {
        val modelType = object : TypeToken<Unit?>() {}.type
        val body = "" // Returns empty body
        val fixture = gson.fromJson<Unit>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        val name = "Changed name"
        val isPublic = false
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"name\":\"%s\",\"public\":%b}", name, isPublic)
                return request.url.endsWith(String.format("/users/%s/playlists/%s",
                    owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) && "PUT" == request.method
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["name"] = name
        options["public"] = isPublic
        val result = spotifyService.changePlaylistDetails(owner, playlistId, options)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldFollowAPlaylist() {
        val modelType = object : TypeToken<Result?>() {}.type
        val body = "" // Returns empty body
        val fixture = gson.fromJson<Result>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                return request.url.endsWith(String.format("/users/%s/playlists/%s/followers",
                    owner, playlistId)) && "PUT" == request.method
            }
        }))).thenReturn(response)

        val result = spotifyService.followPlaylist(owner, playlistId)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldFollowAPlaylistPrivately() {
        val modelType = object : TypeToken<Result?>() {}.type
        val body = "" // Returns empty body
        val fixture = gson.fromJson<Result>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val isPublic = false
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"public\":%b}", isPublic)
                return request.url.endsWith(String.format("/users/%s/playlists/%s/followers",
                    owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) && "PUT" == request.method
            }
        }))).thenReturn(response)

        val pfp = PlaylistFollowPrivacy(isPublic)
        val result = spotifyService.followPlaylist(owner, playlistId, pfp)
        compareJSONWithoutNulls(body, result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReorderPlaylistsTracks() {
        val modelType = object : TypeToken<SnapshotId?>() {}.type
        val body = TestUtils.readTestData("snapshot-response.json")
        val fixture = gson.fromJson<SnapshotId>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        val owner = "thelinmichael"
        val playlistId = "4JPlPnLULieb2WPFKlLiRq"
        val rangeStart = 2
        val rangeLength = 2
        val insertBefore = 0
        Mockito.`when`(mockClient.execute(Matchers.argThat(object : ArgumentMatcher<Request?>() {
            override fun matches(argument: Any): Boolean {
                val request = argument as Request
                val outputStream: OutputStream = ByteArrayOutputStream()
                val output = request.body
                var body: String? = null
                try {
                    output.writeTo(outputStream)
                    body = outputStream.toString()
                } catch (e: IOException) {
                    TestCase.fail("Could not read body")
                }
                val expectedBody = String.format("{\"range_start\":%d,\"range_length\":%d,\"insert_before\":%d}",
                    rangeStart, rangeLength, insertBefore)
                return request.url.endsWith(String.format("/users/%s/playlists/%s/tracks",
                    owner, playlistId)) &&
                        JSONsContainSameData(expectedBody, body) && "PUT" == request.method
            }
        }))).thenReturn(response)

        val options: MutableMap<String?, Any?> = HashMap()
        options["range_start"] = rangeStart
        options["range_length"] = rangeLength
        options["insert_before"] = insertBefore
        val result = spotifyService.reorderPlaylistTracks(owner, playlistId, options)
        compareJSONWithoutNulls(body, result)
    }

    // TODO add interceptor that unpacks Spotify errors (if they still need to be unpacked)
//    @Test
//    @Throws(Exception::class)
//    fun shouldParseErrorResponse() {
//        val body = TestUtils.readTestData("error-unauthorized.json")
//        val fixture = gson.fromJson(body, ErrorResponse::class.java)
//
//        val response = TestUtils.getResponseFromModel(403, fixture, ErrorResponse::class.java)
//        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)
//
//        var errorReached = false
//        try {
//            spotifyService.mySavedTracks
//        } catch (error: RetrofitError) {
//            errorReached = true
//            val spotifyError = SpotifyError.fromRetrofitError(error)
//            Assert.assertEquals(fixture.error!!.status.toLong(), spotifyError.errorDetails.status.toLong())
//            Assert.assertEquals(fixture.error!!.message, spotifyError.errorDetails.message)
//            Assert.assertEquals(403, spotifyError.retrofitError.response.status.toLong())
//        }
//        Assert.assertTrue(errorReached)
//    }

    @Test
    @Throws(Exception::class)
    fun shouldGetArtistTopTracksTracks() {
        val body = TestUtils.readTestData("tracks-for-artist.json")
        val fixture = gson.fromJson(body, Tracks::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Tracks::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val tracks = spotifyService.getArtistTopTrack("test", "SE")
        compareJSONWithoutNulls(body, tracks)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetArtistRelatedArtists() {
        val body = TestUtils.readTestData("artist-related-artists.json")
        val fixture = gson.fromJson(body, Artists::class.java)

        val response = TestUtils.getResponseFromModel(fixture, Artists::class.java)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val tracks = spotifyService.getRelatedArtists("test")
        compareJSONWithoutNulls(body, tracks)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetUserPlaylists() {
        val modelType = object : TypeToken<Pager<PlaylistSimple?>?>() {}.type
        val body = TestUtils.readTestData("user-playlists.json")
        val fixture = gson.fromJson<Pager<PlaylistSimple>>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val playlists = spotifyService.getPlaylists("test")
        compareJSONWithoutNulls<Pager<PlaylistSimple>?>(body, playlists)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetCurrentUserPlaylists() {
        val modelType = object : TypeToken<Pager<PlaylistSimple?>?>() {}.type
        val body = TestUtils.readTestData("user-playlists.json")
        val fixture = gson.fromJson<Pager<PlaylistSimple>>(body, modelType)

        val response = TestUtils.getResponseFromModel(fixture, modelType)
        Mockito.`when`(mockClient.execute(Matchers.isA(Request::class.java))).thenReturn(response)

        val playlists = spotifyService.myPlaylists
        compareJSONWithoutNulls<Pager<PlaylistSimple>?>(body, playlists)
    }

    /**
     * Compares the mapping fixture <-> object, ignoring NULL fields
     * This is useful to prevent issues with entities such as "Image" in
     * which width and height are not always present, and they result in
     * null values in the Image object
     *
     * @param fixture The JSON to test against
     * @param model   The object to be serialized
     */
    private fun <T> compareJSONWithoutNulls(fixture: String, model: T) {
        val parser = JsonParser()

        // Parsing fixture twice gets rid of nulls
        var fixtureJsonElement = parser.parse(fixture)
        val fixtureWithoutNulls = gson.toJson(fixtureJsonElement)
        fixtureJsonElement = parser.parse(fixtureWithoutNulls)
        val modelJsonElement = gson.toJsonTree(model)

        // We compare JsonElements from fixture
        // with the one created from model. If they're different
        // it means the model is borked
        Assert.assertEquals(fixtureJsonElement, modelJsonElement)
    }

    /**
     * Compares two JSON strings if they contain the same data even if the order
     * of the keys differs.
     *
     * @param expected The JSON to test against
     * @param actual   The tested JSON
     * @return true if JSONs contain the same data, false otherwise.
     */
    private fun JSONsContainSameData(expected: String, actual: String?): Boolean {
        val parser = JsonParser()
        val expectedJsonElement = parser.parse(expected)
        val actualJsonElement = parser.parse(actual)
        return expectedJsonElement == actualJsonElement
    }
}