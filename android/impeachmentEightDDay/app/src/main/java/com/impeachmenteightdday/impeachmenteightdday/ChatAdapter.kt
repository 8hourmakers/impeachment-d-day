package com.impeachmenteightdday.impeachmenteightdday

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.viewholder_comment.view.*

class ChateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas: MutableList<Comment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_comment, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as? ViewHolder)?.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0 -> 0
                else -> 1
            }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(comment: Comment) {
            itemView.textView_content?.text = comment.content
            itemView.textView_senderName?.text = comment.senderName
        }
    }

    fun clear() {
        datas.clear()
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment) {
        datas.add(comment)
        notifyItemInserted(datas.size - 1)
    }
}

data class Comment(val senderName: String, var content: String)