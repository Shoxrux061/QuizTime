package uz.isystem.quiztime.domain

import uz.isystem.quiztime.data.quizModel.QuizResponse

class GameController(private val data: QuizResponse) {

    private var currentQuestion = 0
    private var trueAnswers = 0
    private var wrongAnswers = 0
    private var skips = 0
    private val variants = ArrayList<String>()

    fun getCurrentQuestion(): Int {
        return currentQuestion + 1
    }

    fun getVariantSize(): Int {
        return getCurrentData().results[currentQuestion].incorrectAnswers.size + 1
    }

    fun setVariants(): List<String> {

        variants.clear()

        if (getType() == "multiple") {
            variants.add(data.results[currentQuestion].incorrectAnswers[0])
            variants.add(data.results[currentQuestion].incorrectAnswers[1])
            variants.add(data.results[currentQuestion].incorrectAnswers[2])
            variants.add(getTrueAnswer())
        } else {
            variants.add(data.results[currentQuestion].incorrectAnswers[0])
            variants.add(getTrueAnswer())
        }
        variants.shuffle()
        return variants
    }

    fun getAllVariant(): List<String> {
        return variants
    }

    private fun getCurrentData(): QuizResponse {
        return data
    }

    fun getQuestion(): String {
        return data.results[currentQuestion].question
    }

    private fun getType(): String {
        return data.results[currentQuestion].type
    }

    fun skip() {
        skips++
        currentQuestion++
    }

    fun getSkips(): Int {
        return skips
    }

    fun getTrueAnswer(): String {
        return data.results[currentQuestion].correctAnswer
    }

    fun getCategory(): String {

        return data.results[currentQuestion].category

    }


    fun checkAnswer(userAnswer: String) : Boolean {

        return if (userAnswer == data.results[currentQuestion].correctAnswer) {
            currentQuestion++
            trueAnswers++
            true
        } else {
            currentQuestion++
            wrongAnswers++
            false
        }
    }

    fun isFinish(): Boolean {
        return currentQuestion >= 20
    }

    fun getTrueCount(): Int {
        return trueAnswers
    }

    fun getWrongCount(): Int {
        return wrongAnswers
    }

    fun getDifficulty(): String {
        return data.results[currentQuestion].difficulty
    }

    fun isCorrect(userAnswer: String): Boolean {
        return userAnswer == data.results[currentQuestion].correctAnswer

    }

    fun next() {
        currentQuestion++
    }
}