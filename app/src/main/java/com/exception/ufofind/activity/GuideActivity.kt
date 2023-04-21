package com.exception.ufofind.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.exception.ufofind.R
import com.exception.ufofind.databinding.ActivityGuideBinding
import com.exception.ufofind.viewmodel.GuideViewModel


class GuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuideBinding
    private lateinit var viewModel: GuideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, GuideViewModel.GuideViewModelFactory(application))[GuideViewModel::class.java]
        setContentView(binding.root)

        viewModel.liveDataPageCount.observe(this) {
            when(it){
                1 -> {
                    binding.guideText.text = "Это приложение UFO Find, созданное командой Exception 402"
                    binding.btnPrev.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.guideText.text = "Оно поможет найти вам вашего коллегу в большом офисе"
                    binding.btnPrev.visibility = View.VISIBLE
                }
                3 -> binding.guideText.text = "Вам необходимо иметь на своём устройстве компас и включенный Bluetooth"
                4 -> binding.guideText.text = "Введите своё имя и UFO Find покажет вам путь до нужного вам человека"
            }
        }

        viewModel.liveDataShowApplyButton.observe(this) {
            if(it) {
                binding.btnPrev.visibility = View.GONE
                binding.btnNext.text = getString(R.string.apply)

            }
            else {
                binding.btnPrev.visibility = View.VISIBLE
                binding.btnNext.text = getString(R.string.next)
            }
        }

        binding.btnNext.setOnClickListener {
            if(binding.btnNext.text==getString(R.string.apply)) {
                startChooseNameActivity()
            }
            else {
                viewModel.nextPage()
            }
        }
        binding.btnPrev.setOnClickListener {
            viewModel.previousPage()
        }
    }

    private fun startChooseNameActivity() {
        val intent = Intent(this@GuideActivity, ChooseNameActivity::class.java)
        startActivity(intent)
    }

}