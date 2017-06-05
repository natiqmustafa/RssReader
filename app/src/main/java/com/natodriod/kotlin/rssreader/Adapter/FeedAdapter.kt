package com.natodriod.kotlin.rssreader.Adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.natodriod.kotlin.rssreader.Interfaces.ItemClickListener
import com.natodriod.kotlin.rssreader.R
import com.natodriod.kotlin.rssreader.model.RSSObject

/**
 * Created by natiqmustafa on 05.06.2017.
 */
class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener
{
    var etTitle: TextView
    var etDate : TextView
    var etContent : TextView

    private var itemClickListener : ItemClickListener?=null

    init {
        etTitle = itemView.findViewById(R.id.etTitle) as TextView
        etDate = itemView.findViewById(R.id.etDate) as TextView
        etContent = itemView.findViewById(R.id.etContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }


    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }

}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context): RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.custom_row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.etTitle.text = rssObject.items[position].title
        holder.etDate.text = rssObject.items[position].pubDate
        holder.etContent.text = rssObject.items[position].content

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->
            if(!isLongClick)
            {
                Log.d("LINK", rssObject.items[position].link)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                        .addFlags(FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(browserIntent)
            }
        })
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

}