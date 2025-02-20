package com.example.settle_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: ArrayList<Details>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design_4, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        val name = "Name: " + data.name
        val email = "Email: " +  data.email
        val amount = "Amount: " + data.amount
        holder.name.text = name
        holder.email.text = email
        holder.amount.text = amount
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView =itemView.findViewById(R.id.name)
        val email: TextView =itemView.findViewById(R.id.email)
        val amount: TextView =itemView.findViewById(R.id.amount)
    }
}