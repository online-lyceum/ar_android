package com.vuforia.engine.native_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vuforia.engine.native_sample.databinding.ActivityGuideBinding


class GuideActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var countPages: Int = 0

        binding.btnNext.setOnClickListener{
            countPages++
            checkTextValue(countPages)
        }
    }

    private fun checkTextValue(page: Int){
        if (page > 0){
            binding.btnPrev.visibility = View.VISIBLE
        }else{
            binding.btnPrev.visibility = View.INVISIBLE
        }

        if (page == GUIDE_AMOUNT_PAGES){
            binding.btnNext.text = "Погнали!"
            startMainActivity()
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this@GuideActivity, ChooseNameActivity::class.java)
        startActivity(intent)
    }

}