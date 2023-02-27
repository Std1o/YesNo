package com.stdio.yesno.common

import okhttp3.ResponseBody
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

object Utils {

    @Throws(JSONException::class)
    fun encodeErrorCode(errorBody: ResponseBody?): String {
        if (errorBody == null) {
            return "Unknown error"
        }
        val source = errorBody.source()
        val buffer: Buffer = source.buffer()
        val charset: Charset = Charset.forName("UTF-8")
        val errorJson = JSONObject(buffer.readString(charset))
        return errorJson.getString("message")
    }
}