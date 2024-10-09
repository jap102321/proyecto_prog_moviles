package com.example.proyecto_poli

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtOcupacion: EditText
    private lateinit var txtExperiencia: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //se llaman las vistas
        txtName=findViewById(R.id.txtName)
        txtLastName=findViewById(R.id.txtLastName)
        txtEdad=findViewById(R.id.txtEdad)
        txtOcupacion=findViewById(R.id.txtOcupacion)
        txtExperiencia=findViewById(R.id.txtExperiencia)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)

        progressBar=findViewById(R.id.progressBarRegister)

        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")


    }

    fun registrer(view: View){
        createNewAccount()

    }

    private fun createNewAccount(){
        val name:String=txtName.text.toString()
        val lastName:String=txtLastName.text.toString()
        val edad:String=txtEdad.text.toString()
        val ocupacion:String=txtOcupacion.text.toString()
        val experiencia:String=txtExperiencia.text.toString()
        val email:String=txtEmail.text.toString()
        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(edad) && !TextUtils.isEmpty(ocupacion) && !TextUtils.isEmpty(ocupacion) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->

                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD=dbReference.child(user?.uid!!)

                        userBD.child("Name").setValue(name)
                        userBD.child("LastName").setValue(lastName)
                        userBD.child("Edad").setValue(edad)
                        userBD.child("Ocupacion").setValue(ocupacion)
                        userBD.child("Experiencia").setValue(experiencia)
                        action()

                    }
                }
        }

    }


    private fun action(){
        startActivity(Intent(this,LoginNews::class.java))

    }

    private fun verifyEmail (user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->

                if (task.isComplete){
                    Toast.makeText(this,"Email enviado",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this,"Error al enviar el email",Toast.LENGTH_LONG).show()
                }
            }
    }

}