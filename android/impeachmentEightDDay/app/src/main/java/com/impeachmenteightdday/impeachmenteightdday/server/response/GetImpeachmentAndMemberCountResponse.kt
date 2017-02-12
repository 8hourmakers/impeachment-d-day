package com.impeachmenteightdday.impeachmenteightdday.server.response

import com.google.gson.annotations.SerializedName

class GetImpeachmentAndMemberCountResponse {

    private var results: Response? = null

    val impeachmentDatetime: Long?
        get() = results?.impeachmentDatetime

    val memberNum: Long?
        get() = results?.memberNum

    private class Response {

        @SerializedName("impeachment_datetime")
        var impeachmentDatetime: Long? = null

        @SerializedName("member_num")
        var memberNum: Long? = null
    }

}