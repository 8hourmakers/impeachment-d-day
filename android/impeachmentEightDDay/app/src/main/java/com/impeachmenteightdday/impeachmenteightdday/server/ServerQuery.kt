package com.impeachmenteightdday.impeachmenteightdday.server

import com.impeachmenteightdday.impeachmenteightdday.server.request.PostCommentBody
import com.impeachmenteightdday.impeachmenteightdday.server.response.CommonResults
import com.impeachmenteightdday.impeachmenteightdday.server.response.GetCommentResponse
import com.impeachmenteightdday.impeachmenteightdday.server.response.GetImpeachmentAndMemberCountResponse
import retrofit2.Call
import retrofit2.Callback


class ServerQuery {
    companion object {
        fun postComment(senderName: String?, content: String?, callback: Callback<CommonResults>) {

            ServiceGenerator.createService(ServerAPI::class.java, false)
                    .postComment(PostCommentBody(senderName = senderName, content = content))
                    .enqueue(callback)
        }

        fun getComments(commentId: Long?, callback: Callback<GetCommentResponse>) {

            ServiceGenerator.createService(ServerAPI::class.java, false)
                    .getComents(commentId = commentId?.toString())
                    .enqueue(callback)
        }

        fun getImpeachmentAndMemberCount(callback: Callback<GetImpeachmentAndMemberCountResponse>) {

            ServiceGenerator.createService(ServerAPI::class.java, false)
                    .getImpeachmentAndMemberCount()
                    .enqueue(callback)
        }
    }
}