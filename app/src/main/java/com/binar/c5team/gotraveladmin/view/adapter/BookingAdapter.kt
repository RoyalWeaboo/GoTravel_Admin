package com.binar.c5team.gotraveladmin.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemBookingBinding
import com.binar.c5team.gotraveladmin.model.booking.Booking

class BookingAdapter(private var listBooking: List<Booking>): RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    var onCardClick : ((Booking)->Unit)? = null

    class ViewHolder(var binding: ItemBookingBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = listBooking[position].name
        holder.binding.tvBookingDate.text = listBooking[position].bookingDate
        holder.binding.tvMobilePhone.text = listBooking[position].mobilephone
        holder.binding.tvCountPeople.text = "Count : ${listBooking[position].user.whislists.size} People"
        holder.binding.tvTotalPrice.text = "Rp. ${listBooking[position].totalprice}"
        holder.binding.tvTripType.text = listBooking[position].tripType

        if (listBooking[position].approved == true) {
            holder.binding.btnApproved.text = "Approved"
            holder.binding.btnApproved.setBackgroundColor(Color.parseColor("#48FD07"))
        } else {
            holder.binding.btnApproved.text = "UnApproved"
            holder.binding.btnApproved.setBackgroundColor(Color.parseColor("#FD0707"))
        }

        holder.binding.cvBooking.setOnClickListener {
            onCardClick?.invoke(listBooking[position])
        }

    }

    override fun getItemCount(): Int {
        return listBooking.size
    }
}