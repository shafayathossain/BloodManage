package com.example.shafayat.blood

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

public class DonorListAdapter(val donors : List<Donor>, val callButtonOnClickListener: CallButtonOnClickListener ) : RecyclerView.Adapter<DonorListAdapter.DonorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_donor, parent, false)
        return DonorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return donors.size
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {
        holder.nameTextView?.text = donors.get(position).name
        holder.contactNoTextView?.text = donors.get(position).contact_no
        holder.callButton?.setOnClickListener(View.OnClickListener {
            callButtonOnClickListener.onClick(donors.get(position).contact_no)
        })
    }

    class DonorViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val nameTextView = itemView?.findViewById<TextView>(R.id.nameTextView)
        val contactNoTextView = itemView?.findViewById<TextView>(R.id.contactNoTextView)
        val callButton = itemView?.findViewById<Button>(R.id.callButton)
    }

}
