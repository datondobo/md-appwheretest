package com.example.appwhere_cesar_ortega.ui.Sucursales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_where.Models.ResponseSucursales
import com.example.app_where.Models.Sucursales
import com.example.appwhere_cesar_ortega.Adapters.Adapter_Sucursales
import com.example.appwhere_cesar_ortega.Api.Api
import com.example.appwhere_cesar_ortega.Objeto.Base_URL
import com.example.appwhere_cesar_ortega.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SucursalesFragment : Fragment() {

    private lateinit var sucursalesViewModel: SucursalesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sucursalesViewModel =
            ViewModelProviders.of(this).get(SucursalesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sucursales, container, false)


        val RV_sucursales = root.findViewById(R.id.RV_sucursales) as RecyclerView
        RV_sucursales.layoutManager = LinearLayoutManager(activity)


        //Empieza la llamada de Sucursales
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_URL.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Api::class.java)
        val call = service.respuestaMapa()

        //Respuesta de la llamada de Sucursales
        call.enqueue(object : Callback<ResponseSucursales> {
            override fun onResponse(
                call: Call<ResponseSucursales>,
                response: Response<ResponseSucursales>
            ) {
                if (response.isSuccessful) {

                    val gson: Gson = GsonBuilder().create()
                    val json = response.body()?.merchants.toString()
                    val posts: List<Sucursales> = gson.fromJson(json, Array<Sucursales>::class.java).toList()
                    val adapter = Adapter_Sucursales(posts as ArrayList<Sucursales>)
                    RV_sucursales.adapter = adapter

                }
            }

            //Fallo de la llamada de Sucursales
            override fun onFailure(call: Call<ResponseSucursales>, t: Throwable) {

                Toast.makeText(activity, "FAULIRE ::"+ t.message,
                    Toast.LENGTH_SHORT).show()

            }
        })



        return root
    }
}