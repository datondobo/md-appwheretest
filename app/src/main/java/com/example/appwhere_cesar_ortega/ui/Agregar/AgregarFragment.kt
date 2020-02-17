package com.example.appwhere_cesar_ortega.ui.Agregar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.appwhere_cesar_ortega.Api.Api
import com.example.appwhere_cesar_ortega.Models.ResponseAddSucursal
import com.example.appwhere_cesar_ortega.Objeto.Base_URL
import com.example.appwhere_cesar_ortega.R
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AgregarFragment : Fragment() {

    private lateinit var agregarViewModel: AgregarViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agregarViewModel =
            ViewModelProviders.of(this).get(AgregarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_agregar, container, false)

        val boton_ADD = root.findViewById(R.id.BTN_ADD_Send) as Button

        val ET_ADD_Name = root.findViewById(R.id.ET_ADD_Name) as EditText
        val ET_ADD_Adress = root.findViewById(R.id.ET_ADD_Adress) as EditText
        val ET_ADD_Phone = root.findViewById(R.id.ET_ADD_Phone) as EditText
        val ET_ADD_Lat = root.findViewById(R.id.ET_ADD_Lat) as EditText
        val ET_ADD_Lon = root.findViewById(R.id.ET_ADD_Lon) as EditText

        boton_ADD.setOnClickListener(View.OnClickListener {

            if (ET_ADD_Name.text.isNullOrEmpty() || ET_ADD_Adress.text.isNullOrEmpty() || ET_ADD_Phone.text.isNullOrEmpty() ||
                ET_ADD_Lat.text.isNullOrEmpty() || ET_ADD_Lon.text.isNullOrEmpty()){

                Toast.makeText(activity, "Te falta llenar campos", Toast.LENGTH_SHORT).show()


            } else {


                val nombre = ET_ADD_Name.text.toString()
                val direccion = ET_ADD_Adress.text.toString()
                val telefono = ET_ADD_Phone.text.toString()
                val latitude = ET_ADD_Lat.text.toString()
                val longitude = ET_ADD_Lon.text.toString()

                //Empieza la llamada de agregarSucursal
                val retrofit = Retrofit.Builder()
                    .baseUrl(Base_URL.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val json= JSONObject()
                json.put("merchantName",nombre)
                json.put("merchantAddress",direccion)
                json.put("merchantTelephone",telefono)
                json.put("merchantName",latitude)
                json.put("longitude",longitude)


                //Toast.makeText(activity, "PARAMS:: $json", Toast.LENGTH_SHORT).show()

                val service = retrofit.create(Api::class.java)
                val call = service.addSucursal(nombre, direccion, telefono, latitude, longitude)
                //val call = service.addSucursal(json)

                //Respuesta de la llamada de agregarSucursal
                call.enqueue(object : Callback<ResponseAddSucursal> {
                    override fun onFailure(call: Call<ResponseAddSucursal>, t: Throwable) {

                        Toast.makeText(activity, "FAULIRE ::"+ t.message,
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseAddSucursal>,
                        response: Response<ResponseAddSucursal>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(activity, "CORRECTO ::" + response.body()?.description,
                                Toast.LENGTH_SHORT).show()

                        } else{

                            Toast.makeText(activity, "ERROR :: " + response.code(),
                                Toast.LENGTH_SHORT).show()

                        }
                    }

                })


            }



        })



        return root
    }
}