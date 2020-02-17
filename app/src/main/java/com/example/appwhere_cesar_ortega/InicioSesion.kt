package com.example.appwhere_cesar_ortega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.app_where.Models.ResponseLogin
import com.example.appwhere_cesar_ortega.Activitys.Bottom_Navigation
import com.example.appwhere_cesar_ortega.Api.Api
import com.example.appwhere_cesar_ortega.Objeto.Base_URL
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_iinicio_sesion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InicioSesion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iinicio_sesion)
        val actionBar = supportActionBar
        actionBar!!.title = "App Where"

        //Se declara el id del boton
        val BTN_Login = findViewById<FloatingActionButton>(R.id.BTN_Login)

        //Click de boton LOGIN
        BTN_Login.setOnClickListener(View.OnClickListener {

            validaCampos()

        })

    }

    // Metodo para validiar campos vacos de login
    fun validaCampos(){

        //texto de los EditTexts de Login
        val mail = ET_Mail.text
        val pass = ET_Pass.text

        if (mail.isNullOrEmpty() || pass.isNullOrEmpty()){

            if (mail.isNullOrEmpty() && pass.isNullOrEmpty()) {

                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
                clearFocusET()

            } else{

                if (mail.isNullOrEmpty()) {

                    Toast.makeText(this, "Correo electrónico vacío", Toast.LENGTH_SHORT).show()
                    clearFocusET()

                }

                if (pass.isNullOrEmpty()) {

                    Toast.makeText(this, "Contraseña vacía", Toast.LENGTH_SHORT).show()
                    clearFocusET()

                }

            }


        }else{

//            val startBottom = Intent(this, Bottom_Navigation::class.java)
//            startActivity(startBottom)

            //Empieza la llamada de login
            val retrofit = Retrofit.Builder()
                .baseUrl(Base_URL.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(Api::class.java)
            val call = service.respuestaLogin(ET_Mail.text.toString(), ET_Pass.text.toString())

            //Respuesta de la llamada de login
            call.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful) {

                        val startBottom = Intent(this@InicioSesion, Bottom_Navigation::class.java)
                        startActivity(startBottom)

                    } else{


                        val dialog = BottomSheetDialog(this@InicioSesion)
                        val view = layoutInflater.inflate(R.layout.bottom_sheet_login, null)
                        val cerrar = view.findViewById<Button>(R.id.BTN_entendido)
                        cerrar.setOnClickListener(View.OnClickListener {
                            dialog.dismiss()
                        })
                        dialog.setContentView(view)
                        dialog.show()

                        clearFocusET()

                    }
                }

                //Fallo de la llamada de login
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                    Toast.makeText(this@InicioSesion, "ERROR: " + t.message, Toast.LENGTH_SHORT).show()

                }
            })



        }

    }

    fun clearFocusET(){

        //Limpiando EditText y colocando puntero a EditText
        ET_Mail.error = "REQUERIDO"
        ET_Pass.error = "REQUERIDO"
        ET_Mail.text.clear()
        ET_Pass.text?.clear()
        ET_Mail.requestFocus()

    }

}
