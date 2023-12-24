package uz.isystem.quiztime.data.netwrok

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uz.isystem.quiztime.data.quizModel.QuizResponse

interface QuizService {

    @GET("api.php")
    fun getQuiz(
        @Query("category")category:String,
        @Query("amount")count:Int,
        @Query("difficulty")difficulty:String
    ):Call<QuizResponse?>
}