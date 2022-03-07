package kaaes.spotify.webapi.android

import com.google.gson.Gson
import org.robolectric.Robolectric
import retrofit2.Response
import retrofit2.http.Header
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.Type
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.util.ArrayList

object TestUtils {
    private const val TEST_DATA_DIR = "/fixtures/"
    private const val MAX_TEST_DATA_FILE_SIZE = 131072
    private val gson: Gson = Gson()
    fun <T> getResponseFromModel(statusCode: Int, model: T, modelClass: Class<T>?): Response<*> {
        val responseBody = ResponseBody(gson.toJson(model, modelClass))
        return createResponse(statusCode, responseBody)
    }

    fun <T> getResponseFromModel(model: T, modelClass: Class<T>?): Response<*> {
        val responseBody = ResponseBody(gson.toJson(model, modelClass))
        return createResponse(200, responseBody)
    }

    fun <T> getResponseFromModel(model: T, modelType: Type?): Response<*> {
        val responseBody = ResponseBody(gson.toJson(model, modelType))
        return createResponse(200, responseBody)
    }

    private fun createResponse(statusCode: Int, responseBody: ResponseBody): Response<*> {
        return Response("", statusCode, "", ArrayList<Header>(), responseBody)
    }

    @JvmStatic
    fun readTestData(fileName: String): String {
        return try {
            val path: String = Robolectric::class.java.getResource("/fixtures/$fileName").toURI().getPath()
            readFromFile(File(path))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    private fun readFromFile(file: File): String {
        val reader: Reader = FileReader(file)
        val charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE)
        reader.read(charBuffer)
        charBuffer.position(0)
        return charBuffer.toString().trim { it <= ' ' }
    }

    private class ResponseBody private constructor(private val mJson: String) : TypedInput {
        fun mimeType(): String {
            return "application/json"
        }

        fun length(): Long {
            return mJson.length.toLong()
        }

        @Throws(IOException::class)
        fun `in`(): InputStream {
            return ByteArrayInputStream(mJson.toByteArray(StandardCharsets.UTF_8))
        }
    }
}