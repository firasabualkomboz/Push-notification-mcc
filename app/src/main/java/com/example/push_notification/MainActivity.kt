package com.example.push_notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    var api_token = "https://mcc-users-api.herokuapp.com/add_reg_token";
    lateinit var token: TextView
    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({

            setContentView(R.layout.layout_sign_in);

        }, 2000)



        token = findViewById(R.id.editTexttoken)
        queue = Volley.newRequestQueue(this)
        getuserToken()

        }

    private fun getuserToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            Log.e("Token : ", task.result.toString())
            val postRequest: StringRequest = object : StringRequest(
                    Method.PUT, api_token, Response.Listener { response ->

                Log.e("Response", response.toString())
            }, Response.ErrorListener { error ->
                Log.e("Response", error.message.toString())
            }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = Signin.email
                    params["password"] = Signin.password
                    params["reg_token"] = task.result.toString()
                    return params
                }
            }
            queue!!.add(postRequest)
            token.text = task.result.toString()
        }
    }



    }

