package com.example.expenseTracker.di

import android.content.Context
import com.example.expenseTracker.data.services.CategoryService
import com.example.expenseTracker.data.services.ExpenseService
import com.example.expenseTracker.data.services.UserService
import com.example.expenseTracker.network.SessionManager
import com.example.expenseTracker.network.interceptor.HeaderInterceptor
import com.example.expenseTracker.network.interceptor.NetworkConnectionInterceptor
import com.example.expenseTracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        sessionManager: SessionManager
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient
            .Builder()
            .addInterceptor(HeaderInterceptor(sessionManager))
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideCustomRetrofit(
        @ApplicationContext context: Context,
        sessionManager: SessionManager
    ): Retrofit {
        val url = Constants.BASE_URL

        val nullOnEmptyConverterFactory =
            object : Converter.Factory() {
                fun converterFactory() = this

                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit,
                ): Converter<ResponseBody, Any?> {
                    val nextResponseBodyConverter =
                        retrofit.nextResponseBodyConverter<Any?>(
                            converterFactory(),
                            type,
                            annotations
                        )

                    return Converter { value ->
                        if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
                    }
                }
            }

        return Retrofit
            .Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(provideOkHttpClient(context, sessionManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideExpenseService(retrofit: Retrofit): ExpenseService = retrofit.create(ExpenseService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)
}
