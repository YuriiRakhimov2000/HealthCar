package com.yura.newaws.ui.profile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amplifyframework.datastore.generated.model.Profile
import com.yura.newaws.R



class MyProfileRecyclerViewAdapter(
    val listener: (Profile) -> Unit
) : RecyclerView.Adapter<MyProfileRecyclerViewAdapter.ViewHolder>() {
    lateinit var values: List<Profile>
    init {
        values = listOf<Profile>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_profile_list, parent, false)
        return ViewHolder(view)
    }
     fun submitList( values: List<Profile>){
        this.values = values
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.makeView.text = item.make
        holder.modelView.text = item.model
        holder.itemView.setOnClickListener{
            listener(item)}

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val makeView: TextView = view.findViewById(R.id.item_make)
        val modelView: TextView = view.findViewById(R.id.item_model)

        override fun toString(): String {
            return super.toString() + " '" + makeView.text + "'"
        }
    }



}