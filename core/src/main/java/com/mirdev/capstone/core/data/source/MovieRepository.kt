package com.mirdev.capstone.core.data.source

import com.mirdev.capstone.core.data.NetworkBoundResource
import com.mirdev.capstone.core.data.Resource
import com.mirdev.capstone.core.data.source.local.LocalDataSource
import com.mirdev.capstone.core.data.source.remote.RemoteDataSource
import com.mirdev.capstone.core.data.source.remote.network.ApiResponse
import com.mirdev.capstone.core.data.source.remote.response.MovieResponse
import com.mirdev.capstone.core.domain.model.Movie
import com.mirdev.capstone.core.domain.repository.IMovieRepository
import com.mirdev.capstone.core.utils.AppExecutors
import com.mirdev.capstone.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true


            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()


            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val list = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(list)
            }

        }.asFlow()

    override fun getMovieByQuery(query: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true


            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovieByQuery(query)


            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val list = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(list)
            }

        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

}