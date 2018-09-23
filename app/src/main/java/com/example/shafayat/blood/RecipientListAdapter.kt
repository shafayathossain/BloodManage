package com.example.shafayat.blood

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class RecipientListAdapter(var recipients : List<Recipient>, callButtonOnClickListener: CallButtonOnClickListener) : RecyclerView.Adapter<RecipientListAdapter.RecipientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipientViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipient, parent, false)
        return RecipientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipients.size
    }

    override fun onBindViewHolder(holder: RecipientViewHolder, position: Int) {
        holder.nameTextView.text = recipients.get(position).name
        holder.contactNoTextView.text = recipients.get(position).contact_no
        holder.groupTextView.text = recipients.get(position).group
        holder.areaTextView.text = recipients.get(position).area
        holder.hospitalNameTextView.text = recipients.get(position).hospital
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = recipients.get(position).time

        holder.dateTextView.text = calendar.get(Calendar.DAY_OF_MONTH).toString() + "-"+ calendar.get(Calendar.MONTH).toString()+ "-"+ calendar.get(Calendar.YEAR).toString()
    }

    class RecipientViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        var contactNoTextView = itemView.findViewById<TextView>(R.id.contactNoTextView)
        var groupTextView = itemView.findViewById<TextView>(R.id.groupTextView)
        var areaTextView = itemView.findViewById<TextView>(R.id.areaTextView)
        var hospitalNameTextView = itemView.findViewById<TextView>(R.id.hospitalNameTextView)
        var dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
    }
}