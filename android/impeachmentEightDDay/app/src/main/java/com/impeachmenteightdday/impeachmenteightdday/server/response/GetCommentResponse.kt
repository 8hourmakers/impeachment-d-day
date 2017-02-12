package com.impeachmenteightdday.impeachmenteightdday.server.response

import com.google.gson.annotations.SerializedName

class GetCommentResponse {


    @SerializedName("results")
    private var comments: List<Response>? = null


    private class Response {


        var id: Long? = null

        @SerializedName("sender_name")
        var senderName: String? = null

        var content: String? = null

        @SerializedName("register_timestamp")
        var registerTimestap: Long? = null
    }

}