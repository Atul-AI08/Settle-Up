package com.example.settle_up

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChooseAdapter(private val mList: ArrayList<Details>, private var selected: ArrayList<String>) : RecyclerView.Adapter<ChooseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design_1, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        val name = data.name
        val email = data.email
        holder.name.text = name
        holder.email.text = email
        holder.checkbox.setOnClickListener(View.OnClickListener {
            val isChecked: Boolean = holder.checkbox.isChecked
            if (isChecked) {
                Log.d("abc", email)
                selected.add(email)
            }else{
                selected.remove(email)
            }
        })
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView =itemView.findViewById(R.id.name)
        val email: TextView =itemView.findViewById(R.id.email)
        val checkbox : CheckBox = itemView.findViewById(R.id.checkbox)
    }
}