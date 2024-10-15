package com.example.proyecto_poli

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var nameUser: TextView
    private lateinit var ocupacionUser: TextView
    private lateinit var edadUser: TextView
    private lateinit var experienciaUser: TextView
    private lateinit var dbReference: DatabaseReference
    private lateinit var profileImage: ImageView
    private lateinit var storageReference: StorageReference
    //Codigo para solicitar una imagen de la galeria
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Iniciar referencia BD
        dbReference = FirebaseDatabase.getInstance().getReference("Users")
        //inicializar vistas y variables
        nameUser = findViewById(R.id.perfil_name_user)
        ocupacionUser = findViewById(R.id.perfil_ocupacion_user)
        edadUser = findViewById(R.id.perfil_edad_user)
        experienciaUser = findViewById(R.id.perfil_experiencia_user)
        profileImage = findViewById(R.id.profile_image)

        //Codigo para añadir foto de perfil
        storageReference = FirebaseStorage.getInstance().reference
        profileImage = findViewById(R.id.profile_image)


        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {

            //Recuperar los datos del usuario
            dbReference.child(currentUserId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //obtener los datos de la instancia
                    val name = dataSnapshot.child("Name").getValue(String::class.java)
                    val lastName = dataSnapshot.child("LastName").getValue(String::class.java)
                    val ocupacion = dataSnapshot.child("Ocupacion").getValue(String::class.java)
                    val edad = dataSnapshot.child("Edad").getValue(String::class.java)
                    val experiencia = dataSnapshot.child("Experiencia").getValue(String::class.java)

                    //Actualizar los datos de la vista perfil con los datos recuperados de la BD
                    nameUser.text = "$name $lastName"
                    ocupacionUser.text = ocupacion
                    edadUser.text = edad
                    experienciaUser.text = experiencia


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //Mensaje de error
                    Log.e("ProfileActivity", "Error al recuperar los datos del usuario: ${databaseError.message}")
                }
            })
        }else {
            // Manejar el caso donde el usuario no ha iniciado sesión
            Log.w("ProfileActivity", "El usuario no hainiciado sesión")
        }

        loadProfileImage()

    }


    fun home_button(view: View){
        startActivity(Intent(this,LoginNews::class.java))
    }

    fun web_button(view: View){
        startActivity(Intent(this,WebView::class.java))
    }
  //Abre la galeria
    fun btnguardar_foto(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    //Maneja el resultado de la actividad de selección de imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verificar si la solicitud es la de elegir una imagen y si el resultado es correcto
        if (requestCode ==PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!! // Obtener la Uri de la imagen seleccionada

            uploadImageToFirebase(imageUri) // Subir la imagen a Firebase
        }
    }
//Subir la imagen a Firebase Storage
    private fun uploadImageToFirebase(imageUri: Uri) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid // Obtener el UID del usuario actual
        if (currentUserId != null) {
            // Crear una referencia al archivo en Firebase Storage
            val fileRef: StorageReference = storageReference.child("imagenes_perfil/$currentUserId.jpg")

            // Subir el archivo a Firebase Storage
            fileRef.putFile(imageUri)
                .addOnSuccessListener {
                    // Imagen subida con éxito
                    Toast.makeText(this, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
                    // Obtener la URL de descarga de la imagen
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        // Guardar la URL de descarga en Firebase Realtime Database si es necesario

                    }
                }
                .addOnFailureListener {
                    // Manejar el error de subida de imagen
                    Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Manejar el caso donde el usuario no ha iniciado sesión
            Toast.makeText(this, "El usuario no ha iniciado sesión", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfileImage() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            val fileRef: StorageReference = storageReference.child("imagenes_perfil/$currentUserId.jpg")

            fileRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                loadImageIntoImageView(downloadUrl)
            }
                .addOnFailureListener {
                    // Handle error if image doesn't exist or there's a problem loading it
                    Log.e("ProfileActivity", "Error loading profile image: ${it.message}")
                }
        }
    }

    private fun loadImageIntoImageView(downloadUrl: String) {
        Glide.with(this)
            .load(downloadUrl)
            .circleCrop()
            .into(profileImage)
    }

}