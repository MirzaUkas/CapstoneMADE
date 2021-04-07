package com.mirdev.capstone.core.di

import androidx.room.Room
import com.mirdev.capstone.core.BuildConfig
import com.mirdev.capstone.core.data.source.MovieRepository
import com.mirdev.capstone.core.data.source.local.LocalDataSource
import com.mirdev.capstone.core.data.source.local.room.MovieDatabase
import com.mirdev.capstone.core.data.source.remote.RemoteDataSource
import com.mirdev.capstone.core.data.source.remote.network.ApiService
import com.mirdev.capstone.core.domain.repository.IMovieRepository
import com.mirdev.capstone.core.utils.AppExecutors
import com.mirdev.capstone.core.utils.CustomInterceptors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
    val factory = SupportFactory(passphrase)
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add("*.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(CustomInterceptors())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}