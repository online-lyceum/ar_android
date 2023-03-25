package com.vuforia.engine.native_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vuforia.engine.native_sample.databinding.ActivityGuideBinding


class GuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var countPages: Int = 0

        binding.btnNext.setOnClickListener {
            countPages++
            checkTextValue(countPages)
            showGuideText(countPages)
        }
        binding.btnPrev.setOnClickListener {
            countPages--
            checkTextValue(countPages)
            showGuideText(countPages)
        }
    }

    private fun checkTextValue(page: Int) {
        if (page > 0) {
            binding.btnPrev.visibility = View.VISIBLE
        } else {
            binding.btnPrev.visibility = View.INVISIBLE
        }

        if (page == GUIDE_AMOUNT_PAGES) {
            binding.btnNext.text = "Погнали!"
        }else if(page == GUIDE_AMOUNT_PAGES + 1){
            startMainActivity()
        }
    }

    private fun showGuideText(page: Int){
        when(page){
            1 -> binding.guideText.text = "Это приложение UFO Find, созданное командой Exception 402"
            2 -> binding.guideText.text = "Оно поможет найти вам вашего коллегу в большом офисе"
            3 -> binding.guideText.text = "Вам необходимо иметь на своём устройстве компас и включенный Bluetooth"
            4 -> binding.guideText.text = "Введите своё имя и UFO Find покажет вам путь до нужного вам человека"
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this@GuideActivity, ChooseNameActivity::class.java)
        startActivity(intent)
    }

}