package com.devstudio.forexFusion.data.repository

import com.devstudio.forexFusion.ui.utils.Utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth
import com.devstudio.forexFusion.data.model.enums.Result
import kotlinx.coroutines.tasks.await

class AuthRepository(val auth: FirebaseAuth) {

//    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun registerUser(email: String, password: String): Result<Boolean> {
        if (!isEmailValid(email)) {
            return Result.Error(Exception("Invalid Email Address"))
        }

        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    fun signOut(): Result<Boolean> {
        return try {
            auth.signOut()
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

}