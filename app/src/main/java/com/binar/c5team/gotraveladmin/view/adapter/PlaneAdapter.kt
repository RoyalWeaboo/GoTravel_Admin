package com.binar.c5team.gotraveladmin.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemAdminBinding
import com.binar.c5team.gotraveladmin.databinding.ItemPlaneBinding
import com.binar.c5team.gotraveladmin.model.PlaneList
import com.binar.c5team.gotraveladmin.model.UserAdminResponse

class PlaneAdapter(private var listPlane: List<PlaneList>) : RecyclerView.Adapter<PlaneAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPlaneBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPlaneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvPlane.text = listPlane[position].name
        holder.binding.tvCountPlane.text = listPlane[position].code.toString()

        holder.binding.cvPlane.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("datadetail",listPlane[position])
        }

        holder.binding.btnEdit.setOnClickListener {

        }

        holder.binding.btnDelete.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listPlane.size
    }
} 