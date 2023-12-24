package uz.isystem.quiztime.presentation.game_screen

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.isystem.quiztime.R
import uz.isystem.quiztime.data.netwrok.AppClient
import uz.isystem.quiztime.data.netwrok.QuizService
import uz.isystem.quiztime.data.quizModel.QuizResponse
import uz.isystem.quiztime.databinding.ScreenGameBinding
import uz.isystem.quiztime.domain.GameController
import uz.isystem.quiztime.presentation.base.BaseFragment

class GameScreen : BaseFragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val args: GameScreenArgs by navArgs()
    private var timer: CountDownTimer? = null
    private var stepCount = 30
    private val handler = Handler(Looper.getMainLooper())
    private var isMultiple = true
    private var isWaiting = false
    override fun onCreate(view: View, savedInstanceState: Bundle?) {

        val api = AppClient.getRetrofit().create(QuizService::class.java)
        api.getQuiz(category = args.category, difficulty = args.difficulty, count = 20)
            .enqueue(object : Callback<QuizResponse?> {
                override fun onResponse(
                    call: Call<QuizResponse?>,
                    response: Response<QuizResponse?>
                ) {
                    if (response.isSuccessful) {
                        val controller = GameController(response.body()!!)
                        binding.progressBar.visibility = View.GONE
                        loadData(controller)
                        setActions(controller)
                        timer = object : CountDownTimer(30000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                countTimer()
                            }

                            override fun onFinish() {
                                controller.skip()
                                stepCount = 30
                                loadData(controller)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<QuizResponse?>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        timer?.cancel()
    }

    private fun setActions(controller: GameController) {

        val trueAnimation = AnimationUtils.loadAnimation(context, R.anim.true_answer_anim)
        val falseAnimation = AnimationUtils.loadAnimation(context, R.anim.false_answer_anim)

        binding.nextBtn.setOnClickListener {
            loadData(controller)
            isWaiting = false
        }


        binding.answer1.setOnClickListener {
            if (!isWaiting) {
                isWaiting = true
                if (controller.checkAnswer(binding.answer1.text.toString())) {
                    binding.answer1.startAnimation(trueAnimation)
                    binding.answer1.setBackgroundResource(R.drawable.question_item_green)
                    showNextBtn()
                } else {
                    binding.answer1.startAnimation(falseAnimation)
                    binding.answer1.setBackgroundResource(R.drawable.question_item_red)
                    showNextBtn()
                }
            }
        }
        binding.answer2.setOnClickListener {
            if (!isWaiting) {
                isWaiting = true
                if (controller.checkAnswer(binding.answer2.text.toString())) {
                    binding.answer2.startAnimation(trueAnimation)
                    binding.answer2.setBackgroundResource(R.drawable.question_item_green)
                    showNextBtn()
                } else {
                    binding.answer2.startAnimation(falseAnimation)
                    binding.answer2.setBackgroundResource(R.drawable.question_item_red)
                    showNextBtn()
                }
            }
        }
        binding.answer3.setOnClickListener {
            if (!isWaiting) {
                isWaiting = true
                if (controller.checkAnswer(binding.answer3.text.toString())) {
                    binding.answer3.startAnimation(trueAnimation)
                    binding.answer3.setBackgroundResource(R.drawable.question_item_green)
                    showNextBtn()
                } else {
                    binding.answer3.startAnimation(falseAnimation)
                    binding.answer3.setBackgroundResource(R.drawable.question_item_red)
                    showNextBtn()
                }
            }
        }
        binding.answer4.setOnClickListener {
            if (!isWaiting) {
                isWaiting = true
                if (controller.checkAnswer(binding.answer4.text.toString())) {
                    binding.answer4.startAnimation(trueAnimation)
                    binding.answer4.setBackgroundResource(R.drawable.question_item_green)
                    showNextBtn()
                } else {
                    binding.answer4.startAnimation(falseAnimation)
                    binding.answer4.setBackgroundResource(R.drawable.question_item_red)
                    showNextBtn()
                }
            }
        }
    }

    private fun showNextBtn() {
        timer?.cancel()
        binding.nextBtn.visibility = View.VISIBLE
    }

    private fun countTimer() {
        binding.timerText.text = stepCount.toString()
        stepCount--
    }

    private fun loadData(controller: GameController) {

        timer?.cancel()
        binding.timerText.text = ""
        val newQuestAnimate = AnimationUtils.loadAnimation(context, R.anim.new_question_anim)
        binding.frame.startAnimation(newQuestAnimate)

        binding.nextBtn.visibility = View.GONE
        binding.answer1.setBackgroundResource(R.drawable.question_item)
        binding.answer2.setBackgroundResource(R.drawable.question_item)
        binding.answer3.setBackgroundResource(R.drawable.question_item)
        binding.answer4.setBackgroundResource(R.drawable.question_item)

        if (controller.isFinish()) {
            findNavController().navigate(GameScreenDirections.actionGameScreenToResultScreen())
            return
        }

        controller.setVariants()

        for (i in 0 until binding.multipleAnswers.childCount) {
            val textView = binding.multipleAnswers.getChildAt(i) as TextView
            if (i < controller.getVariantSize()) {
                isMultiple = true
                textView.visibility = View.VISIBLE
                textView.text = controller.getAllVariant()[i]
            } else {
                isMultiple = false
                textView.visibility = View.GONE
            }
        }

        binding.multipleAnswers.visibility = View.VISIBLE
        val decodedText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(controller.getQuestion(), Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            "error"
        }
        binding.category.text = "Category:${controller.getCategory()}"
        binding.difficulty.text = "Difficulty:${controller.getDifficulty()}"
        binding.currentQuestion.text = "${controller.getCurrentQuestion()}/20"
        binding.questionText.text = decodedText

        handler.postDelayed({
            stepCount = 30
            timer?.start()
        }, 1000)
    }
}