package com.judahben149.paging3trial.di

import android.util.Log
import com.google.gson.Gson
import com.judahben149.paging3trial.Constants
import com.judahben149.paging3trial.Constants.API_KEY
import com.judahben149.paging3trial.Constants.BASE_URL
import com.judahben149.paging3trial.Constants.TAG_API
import com.judahben149.paging3trial.MovieService
import com.judahben149.paging3trial.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyModules {

//    @Provides
//    @Singleton
//    fun providesMoshi(): Moshi {
//        return Moshi.Builder()
//            .addLast(KotlinJsonAdapterFactory())
//            .build()
//    }

//    fun providesGson(): Gson {
//        return Gson()
//    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(TAG_API, "log: $message")
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun providesRepository(service: MovieService): Repository {
        return Repository(service)
    }

}