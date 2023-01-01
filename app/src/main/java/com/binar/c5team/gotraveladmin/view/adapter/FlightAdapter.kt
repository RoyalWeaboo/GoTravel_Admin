package com.binar.c5team.gotraveladmin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemFlightBinding
import com.binar.c5team.gotraveladmin.model.PlaneList
import com.binar.c5team.gotraveladmin.model.flight.Flight

class FlightAdapter(private var listFlight: List<Flight>): RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    var onCardClick : ((Flight)->Unit)? = null
    var onEditClick : ((Flight)->Unit)? = null
    var onDeleteClick : ((Int)->Unit)? = null

    class ViewHolder(var binding: ItemFlightBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFlightBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvArrivalTime.text = listFlight[position].arrivalTime
        holder.binding.tvDepartureTime.text = listFlight[position].departureTime
        holder.binding.tvPrice.text = listFlight[position].price.toString()
        holder.binding.tvNamePlane.text = listFlight[position].plane.name
        holder.binding.tvFromAirport.text = listFlight[position].fromAirport.city
        holder.binding.tvToAirport.text = listFlight[position].toAirport.city

        holder.binding.cvFlight.setOnClickListener {
            onCardClick?.invoke(listFlight[position])
        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(listFlight[position])

        }

        holder.binding.btnRemove.setOnClickListener {
            onDeleteClick?.invoke(listFlight[position].id)
        }
    }

    override fun getItemCount(): Int {
        return listFlight.size
    }
}