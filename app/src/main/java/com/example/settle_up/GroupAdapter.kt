package com.example.settle_up

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(private val mList: ArrayList<String>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design_3, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        val name = "Group: $data"
        holder.name.text = name
        holder.itemView.setOnClickListener(View.OnClickListener {
            Intent(holder.itemView.context, GroupDetails::class.java).also {
                it.putExtra("name", data)
                holder.itemView.context.startActivity(it)
            }
        })
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView =itemView.findViewById(R.id.name)
    }
}