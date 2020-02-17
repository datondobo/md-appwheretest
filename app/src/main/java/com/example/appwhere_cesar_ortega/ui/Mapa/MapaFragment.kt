package com.example.appwhere_cesar_ortega.ui.Mapa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.app_where.Models.ResponseSucursales
import com.example.app_where.Models.Sucursales
import com.example.appwhere_cesar_ortega.Api.Api
import com.example.appwhere_cesar_ortega.Models.MapaSucursales
import com.example.appwhere_cesar_ortega.Objeto.Base_URL
import com.example.appwhere_cesar_ortega.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapaViewModel: MapaViewModel
    private lateinit var mMap: GoogleMap
    val CDMX = LatLng(19.432480,-99.132881)
    var mapFragment : SupportMapFragment?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mapaViewModel =
            ViewModelProviders.of(this).get(MapaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mapa, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)



        return root
    }

    override fun onMapReady(p0: GoogleMap?) {

        if (p0 != null) {
            mMap = p0
        }



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

                    //Toast.makeText(activity,response.body()?.merchants.toString(),Toast.LENGTH_LONG).show()
                    val gson: Gson = GsonBuilder().create()
                    val json = response.body()?.merchants.toString()
                    val posts: List<MapaSucursales> = gson.fromJson(json, Array<MapaSucursales>::class.java).toList()

                    for (item in posts)
                    {
                        val latitude = item.latitude.toDouble()
                        val longitude = item.longitude.toDouble()

                        val location = LatLng(latitude, longitude)
                        mMap.addMarker(MarkerOptions().position(location).title(item.merchantName + " " + item.merchantAddress))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

                    }

                }
            }

            //Fallo de la llamada de Sucursales
            override fun onFailure(call: Call<ResponseSucursales>, t: Throwable) {

                Toast.makeText(activity, "FAULIRE ::"+ t.message,
                    Toast.LENGTH_SHORT).show()

            }
        })

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CDMX, 12f))


    }

}