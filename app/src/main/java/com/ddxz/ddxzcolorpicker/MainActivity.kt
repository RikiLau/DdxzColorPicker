package com.ddxz.ddxzcolorpicker

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.ddxz.ddxzcolorpicker.databinding.ActivityMainBinding
import android.graphics.drawable.ClipDrawable

import android.view.Gravity




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentColor: Int = Color.WHITE
    var p = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cpv.setChoiceColorListener(object : ColorPickerView.IChoiceColorListener {
            override fun onChoiceColor(color: Int, a1: String?, r: Int, g: Int, b: Int, text: String?, hsl: FloatArray) {
                binding.tvColor.text = "$text 明度:${hsl[2]}"
                binding.ivColor.setBackgroundColor(Color.parseColor("#$text"))

                currentColor = Color.parseColor("#$text")

                val progressDrawable = GradientDrawable()
                progressDrawable.setColor(Color.TRANSPARENT)

                val colorArray = intArrayOf(Color.parseColor("#$text"), Color.BLACK)
                val bgDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colorArray)
                bgDrawable.cornerRadius = 4f.dp

                val ld = binding.seekBar.progressDrawable as LayerDrawable
                ld.setDrawableByLayerId(android.R.id.progress, bgDrawable)
                binding.seekBar.invalidate()

                updateLightnessColor()
            }
        })
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, persent: Int, p2: Boolean) {
//                Log.d("riki", "onProgressChanged: p1 $persent p ${persent.toFloat() / 100}")
                p = persent.toFloat() / 100
                updateLightnessColor()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun updateLightnessColor() {
        val argbEvaluator = ArgbEvaluator() //渐变色计算类
        val color = argbEvaluator.evaluate(p, currentColor, Color.BLACK) as Int
        binding.ivColor2.setBackgroundColor(color)
    }
}