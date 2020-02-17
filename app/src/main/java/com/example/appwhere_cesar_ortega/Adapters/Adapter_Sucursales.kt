package com.example.appwhere_cesar_ortega.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_where.Models.ResponseSucursales
import com.example.app_where.Models.Sucursales
import com.example.appwhere_cesar_ortega.R

class Adapter_Sucursales(val sucursalesList: ArrayList<Sucursales>):
    RecyclerView.Adapter<Adapter_Sucursales.ViewHolder>() {


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        val TV_Title = itemView.findViewById(R.id.TV_Title) as TextView
        val TV_Adress = itemView.findViewById(R.id.TV_Adress) as TextView
        val TV_phone = itemView.findViewById(R.id.TV_phone) as TextView


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.view_sucursal, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {

        return sucursalesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val valoresSucursales: Sucursales = sucursalesList[position]
        holder.TV_Title.text = valoresSucursales.merchantName
        holder.TV_Adress.text = valoresSucursales.merchantAddress
        holder.TV_phone.text = valoresSucursales.merchantTelephone

    }

}