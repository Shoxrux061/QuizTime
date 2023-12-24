package uz.isystem.quiztime.presentation.home_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.isystem.quiztime.R
import uz.isystem.quiztime.databinding.ScreenHomeBinding
import uz.isystem.quiztime.presentation.base.BaseFragment

class HomeScreen : BaseFragment(R.layout.screen_home){
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private var difficulty = "easy"
    private val easy = "easy"
    private val medium = "medium"
    private val hard = "hard"
    override fun onCreate(view: View, savedInstanceState: Bundle?) {
        selectDifficulty()
        setActions()
    }

    private fun setActions() {
        binding.geogBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "22"))
        }
        binding.historyBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "23"))
        }
        binding.mathBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = "",
                category = "19"))
        }
        binding.animalsBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "27"))
        }
        binding.sportsBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "21"))
        }
        binding.gameBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "15"))
        }
        binding.coddingBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "18"))
        }
        binding.knowledgeBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "9"))
        }
        binding.politicBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = "",
                category = "24"))
        }
        binding.natureBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "17"))
        }
        binding.musicBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = difficulty,
                category = "12"))
        }
        binding.gadgetBtn.setOnClickListener {
            findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(difficulty = "",
                category = "30"))
        }
    }

    private fun selectDifficulty() {
        binding.easyBtn.setOnClickListener {
            difficulty = easy
        }
        binding.mediumBtn.setOnClickListener {
            difficulty = medium
        }
        binding.hardBtn.setOnClickListener {
            difficulty = hard
        }
    }
}