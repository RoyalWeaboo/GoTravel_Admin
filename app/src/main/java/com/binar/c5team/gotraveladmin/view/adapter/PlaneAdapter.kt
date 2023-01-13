package com.binar.c5team.gotraveladmin.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemPlaneBinding
import com.binar.c5team.gotraveladmin.model.PlaneList

class PlaneAdapter(private var listPlane: List<PlaneList>) : RecyclerView.Adapter<PlaneAdapter.ViewHolder>() {
    var onEditClick : ((PlaneList)->Unit)? = null
    var onDeleteClick : ((Int)->Unit)? = null

    class ViewHolder(var binding: ItemPlaneBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPlaneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvPlane.text = listPlane[position].name
        holder.binding.tvCountPlane.text = listPlane[position].id.toString()


        if (listPlane[position].status == "Off") {
            holder.binding.btnActive.text = "Inactive"
            holder.binding.btnActive.setBackgroundColor(Color.parseColor("#E40A0A"))
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick?.invoke(listPlane[position].id)

        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(listPlane[position])
        }


    }

    override fun getItemCount(): Int {
        return listPlane.size
    }
} 