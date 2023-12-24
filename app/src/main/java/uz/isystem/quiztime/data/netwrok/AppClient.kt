package uz.isystem.quiztime.data.netwrok

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.isystem.quiztime.app.App

object AppClient {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun client() = OkHttpClient
        .Builder()
        .addInterceptor(chuckerInterceptor())
        .build()

    private fun chuckerInterceptor() =  ChuckerInterceptor.Builder(App.instance)
        .collector(chuckerCollector())
        .maxContentLength(250_000L)
        .redactHeaders("Auth-Toke","Bearer")
        .alwaysReadResponseBody(true)
        .build()

    private fun chuckerCollector() = ChuckerCollector(
        context = App.instance,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
}