package com.binar.c5team.gotraveladmin.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemAdminBinding
import com.binar.c5team.gotraveladmin.model.UserAdminResponse

class AdminAdapter(private var listAdmin: List<UserAdminResponse>) : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemAdminBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = listAdmin[position].username
        holder.binding.tvRole.text = listAdmin[position].role

        holder.binding.cvAdmin.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("datadetail",listAdmin[position])
        }

        holder.binding.btnEdit.setOnClickListener {

        }

        holder.binding.btnRemove.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listAdmin.size
    }
}