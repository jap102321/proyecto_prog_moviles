package com.example.proyecto_poli

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginNews : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPass: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_login)

        txtUser=findViewById(R.id.txtEmail)
        txtPass=findViewById(R.id.txtPass)
        progressBar=findViewById(R.id.progressBar)
        auth=FirebaseAuth.getInstance()

    }

    fun ForgotPass(view: View){
        startActivity(Intent(this,ForgotPassActivity()::class.java))

    }
    fun register(view: View){
        startActivity(Intent(this,RegistroActivity::class.java))

    }
    fun login(view: View){
        loginUser()

    }
    fun perfilBtn(view: View){
        startActivity(Intent(this,ProfileActivity::class.java))
    }

    fun web_button(view: View){
        startActivity(Intent(this,WebView::class.java))
    }

    private fun loginUser() {
        val user: String = txtUser.text.toString()
        val pass: String = txtPass.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        action()
                    } else {
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun  action(){
        startActivity(Intent(this, ProfileActivity::class.java))
    }


}