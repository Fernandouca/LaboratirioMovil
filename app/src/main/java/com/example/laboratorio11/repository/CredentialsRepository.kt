package com.example.laboratorio11.repository

import com.example.laboratorio11.network.ApiResponse
import com.example.laboratorio11.network.dto.login.LoginRequest
import com.example.laboratorio11.network.dto.register.RegisterRequest
import com.example.laboratorio11.network.service.AuthService
import retrofit2.HttpException
import java.io.IOException

class CredentialsRepository(private val api: AuthService) {

    suspend fun login(email: String, password: String): ApiResponse<String>{
        try {
            val response = api.login(LoginRequest(email, password))
            return ApiResponse.Success(response.token)
        }catch (e: HttpException){
            if (e.code()==400){
                return ApiResponse.ErrorWithMessage("Los datos no son validos")
            }
            return ApiResponse.Error(e)
        }catch (e: IOException){
            return ApiResponse.Error(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): ApiResponse<String> {
        try {
            val response = api.register(RegisterRequest(name,email,password))
            return ApiResponse.Success(response.message)
        } catch (e: HttpException) {
            if (password.length < 8) {
                return ApiResponse.ErrorWithMessage("Su contrasena tiene que tener 8 caracteres")
            }

            if (e.code() == 400) {
                return ApiResponse.ErrorWithMessage("Se a validado los datos")
            }

            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }
}