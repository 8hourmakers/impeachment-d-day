package com.impeachmenteightdday.impeachmenteightdday.server

import com.impeachmenteightdday.impeachmenteightdday.server.request.PostCommentBody
import com.impeachmenteightdday.impeachmenteightdday.server.response.CommonResults
import com.impeachmenteightdday.impeachmenteightdday.server.response.GetCommentResponse
import com.impeachmenteightdday.impeachmenteightdday.server.response.GetImpeachmentAndMemberCountResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ServerAPI {

    @POST("/impeachment_d_day/api/comment")
    fun postComment(@Body body: PostCommentBody?): retrofit2.Call<CommonResults>

    @GET("/impeachment_d_day/api/comment")
    fun getComents(@Query("start") commentId: String?): retrofit2.Call<GetCommentResponse>

    @GET("/impeachment_d_day/api/impeachment")
    fun getImpeachmentAndMemberCount(): retrofit2.Call<GetImpeachmentAndMemberCountResponse>
}