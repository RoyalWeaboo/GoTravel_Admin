package com.binar.c5team.gotraveladmin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.c5team.gotraveladmin.databinding.ItemAdminBinding
import com.binar.c5team.gotraveladmin.model.admin.User

class AdminAdapter(private var listAdmin: List<User>) : RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    private var onEditClick : ((User)->Unit)? = null
    var onDeleteClick : ((Int)->Unit)? = null


    class ViewHolder(var binding: ItemAdminBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = listAdmin[position].username
        holder.binding.tvRole.text = listAdmin[position].role

        holder.binding.btnEdit.setOnClickListener {
            onEditClick?.invoke(listAdmin[position])

        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick?.invoke(listAdmin[position].id)
        }

    }

    override fun getItemCount(): Int {
        return listAdmin.size
    }
}