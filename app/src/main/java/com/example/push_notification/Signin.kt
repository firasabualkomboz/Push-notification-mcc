package com.example.push_notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class Signin : AppCompatActivity() {

    var api_login = "https://mcc-users-api.herokuapp.com/login";
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var logIn: TextView
    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.txtEmailSignIn)!!
        password = findViewById(R.id.txtPasswordSignIn)!!
        logIn = findViewById(R.id.btnSignIn)

        queue = Volley.newRequestQueue(this)
        logIn.setOnClickListener {
            Companion.email = email.text.toString()
            Companion.password = password.text.toString()

            login()
//            logIn.setOnClickListener {
//                logIn();
//            }

        }
    }

    fun login() {
        val intent = Intent(this , Signin::class.java)
        val postRequest: StringRequest = object : StringRequest(
                Method.POST, api_login, Response.Listener { response ->
            Log.e("Response", response.toString())
            startActivity(intent)
        }, Response.ErrorListener { error ->
            Toast.makeText(this , "There is error " , Toast.LENGTH_SHORT).show()
            Log.e("Response", error.message.toString())
        }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = email.text.toString()
                params["password"] = password.text.toString()
                return params
            }
        }
        queue!!.add(postRequest)
    }


    companion object{
        var email = ""
        var password = ""
    }

}