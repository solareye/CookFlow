package mobile.solareye.cookflow.data.api

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object NetworkDataSourceBuilder {

    private const val BASE_URL = "https://ybplayer.sytes.net/api/v1/"

    private val interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()

            return chain.proceed(request)
        }

    }

    private fun getRetrofit(): Retrofit {

        val loggingInterceptor = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .tag("ServerLogging")
            .build()

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val networkDataSource: NetworkDataSource = getRetrofit().create(NetworkDataSource::class.java)

}