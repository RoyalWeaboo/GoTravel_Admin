package com.binar.c5team.gotraveladmin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemWhislistBinding
import com.binar.c5team.gotraveladmin.model.bookingid.Whislists

class WhislistAdapter(private var listWishlist: List<Whislists>): RecyclerView.Adapter<WhislistAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemWhislistBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWhislistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUserCount.text = "People-${listWishlist[position].id}"
        holder.binding.tvClass.text = listWishlist[position].flight.plane.name
        holder.binding.tvClass.text = listWishlist[position].flight.kelas
        holder.binding.tvSeat.text = "${listWishlist[position].flight.availableSeats}"
        holder.binding.tvPrice.text = "Rp. ${listWishlist[position].flight.price}"
        holder.binding.tvFromAirport.text = listWishlist[position].flight.fromAirport.city
        holder.binding.tvToAirport.text = listWishlist[position].flight.toAirport.city
        holder.binding.tvArrival.text = listWishlist[position].flight.arrivalTime
        holder.binding.tvDeparture.text = listWishlist[position].flight.departureTime
    }

    override fun getItemCount(): Int {
        return listWishlist.size
    }
}