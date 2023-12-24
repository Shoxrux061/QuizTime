package uz.isystem.quiztime.presentation.result_screen

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.isystem.quiztime.R
import uz.isystem.quiztime.databinding.ScreenResultBinding
import uz.isystem.quiztime.presentation.base.BaseFragment

class ResultScreen : BaseFragment(R.layout.screen_result){
    private val binding by viewBinding(ScreenResultBinding::bind)
    override fun onCreate(view: View, savedInstanceState: Bundle?) {

    }
}
