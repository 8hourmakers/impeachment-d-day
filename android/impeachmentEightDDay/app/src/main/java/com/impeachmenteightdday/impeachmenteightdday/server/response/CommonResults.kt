package com.impeachmenteightdday.impeachmenteightdday.server.response

import com.google.gson.annotations.SerializedName

class CommonResults {

    @SerializedName("results")
    private var results: Response? = null

    private class Response {
        var success: Boolean? = null
        var fail: Boolean? = null
    }
}
