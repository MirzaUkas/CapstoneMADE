package com.mirdev.capstone.core.data.source.remote

import android.util.Log
import com.mirdev.capstone.core.data.source.remote.network.ApiResponse
import com.mirdev.capstone.core.data.source.remote.network.ApiService
import com.mirdev.capstone.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.movieResponses
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.movieResponses))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieByQuery(query: String): Flow<ApiResponse<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListByQuery(query)
                val dataArray = response.movieResponses
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.movieResponses))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}