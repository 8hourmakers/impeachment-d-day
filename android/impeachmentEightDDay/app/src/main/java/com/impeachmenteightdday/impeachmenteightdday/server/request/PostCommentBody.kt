package com.impeachmenteightdday.impeachmenteightdday.server.request

import com.google.gson.annotations.SerializedName

data class PostCommentBody(@SerializedName("sender_name")
                           var senderName: String? = null,
                           @SerializedName("content")
                           var content: String? = null)
