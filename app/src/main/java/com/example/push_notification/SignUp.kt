package com.example.push_notification

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.Response
import java.lang.reflect.Method

class SignUp : AppCompatActivity(){

    var api_added_user = "https://mcc-users-api.herokuapp.com/add_new_user";

    lateinit var firstName: EditText
    lateinit var secondName: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var SignUp: TextView
    lateinit var signIn: TextView
    var queue: RequestQueue? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firstName = findViewById(R.id.first_name);
        secondName = findViewById(R.id.second_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        SignUp   = findViewById(R.id.btnSignup);
        queue = Volley.newRequestQueue(this)

        SignUp.setOnClickListener {
        added_user();
        }

    }

    fun added_user() {
        val intent = Intent(this , MainActivity::class.java)
        val postRequest: StringRequest = object : StringRequest(

            Method.POST, api_added_user, Response.Listener  { response ->

                Log.e("Response", response.toString())
                Toast.makeText(this , "Success." , Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }, Response.ErrorListener { error ->

                Toast.makeText(this , "There is error " , Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message.toString())
            }
        )
        {

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                params["firstName"]     =  firstName.text.toString()
                params["secondName"]    =  secondName.text.toString()
                params["email"]         =  email.text.toString()
                params["password"]      =  password.text.toString()

                return params
            }
        }
        queue!!.add(postRequest)
    }

}