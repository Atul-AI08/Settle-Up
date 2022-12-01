package com.example.settle_up

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class AmountAdapter(private val mList: ArrayList<Details>, private val amount: HashMap<String, Any>) : RecyclerView.Adapter<AmountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design_2, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        val name = data.name
        val email = data.email
        holder.name.text = name
        holder.email.text = email
        holder.share.addTextChangedListener {
            if(isNumeric(holder.share.text.toString())){
                amount[email] = holder.share.text.toString()
            }else{
                Toast.makeText(holder.itemView.context, "Enter numerical value", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView =itemView.findViewById(R.id.name)
        val email: TextView =itemView.findViewById(R.id.email)
        val share: EditText = itemView.findViewById(R.id.amount)
    }
}