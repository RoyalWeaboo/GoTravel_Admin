package com.binar.c5team.gotraveladmin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemAirportBinding
import com.binar.c5team.gotraveladmin.model.AirportList

class AirportAdapter(private var listAirport: List<AirportList>): RecyclerView.Adapter<AirportAdapter.ViewHolder>() {

    var onCardClick : ((AirportList)->Unit)? = null
    var onEditClick : ((AirportList)->Unit)? = null
    var onDeleteClick : ((Int)->Unit)? = null

    class ViewHolder(var binding: ItemAirportBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAirportBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNameAirport.text = listAirport[position].name
        holder.binding.tvLocation.text = "${listAirport[position].city},${listAirport[position].province}, ${listAirport[position].country}"
        holder.binding.tvCountPlane.text = listAirport[position].id.toString()

        holder.binding.cvAirport.setOnClickListener {
            onCardClick?.invoke(listAirport[position])
        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(listAirport[position])

        }

        holder.binding.btnRemove.setOnClickListener {
            onDeleteClick?.invoke(listAirport[position].id)
        }
    }

    override fun getItemCount(): Int {
        return listAirport.size
    }
}