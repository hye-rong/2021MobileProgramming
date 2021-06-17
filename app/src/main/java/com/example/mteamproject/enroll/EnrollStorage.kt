package com.example.mteamproject.enroll

import android.net.Uri
import android.widget.Toast
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

class EnrollStorage(imageUri: Uri) {
    var imageUri: Uri
    init {
        this.imageUri = imageUri
    }
    fun uploadImage() {

        val storage = Firebase.storage
        var storageRef = storage.reference
        var myRef = storageRef.child("/ArtImg/${imageUri.lastPathSegment}")
        var uploadTask = myRef.putFile(imageUri)

        val urlTask = uploadTask.continueWith { task ->
            if(!task.isSuccessful) {

            }
        }.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val downloadUri = task.result
            }
        }
    }
    //업로드한 이미지는 glide로 불러오는게 편함
}