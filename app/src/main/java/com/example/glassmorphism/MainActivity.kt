package com.example.glassmorphism

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.glassmorphism.databinding.ActivityMainBinding
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val center: Boolean = true
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.blurView1.clipToPadding = true
        binding.blurView2.clipToPadding = true
        binding.navView.clipToPadding = true

        //The blurRadius
        val blurRadius = 6f
        val blurRadius2 = 6f
        val blurRadius3 = 6f

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.blurView1.setupWith(binding.main, RenderEffectBlur())
                .setBlurRadius(blurRadius)
            binding.blurView2.setupWith(binding.main, RenderEffectBlur())
                .setBlurRadius(blurRadius2)
            binding.navViewBottom.setupWith(binding.main, RenderEffectBlur())
                .setBlurRadius(blurRadius3)
        } else {
            binding.blurView1.setupWith(binding.main, RenderScriptBlur(this))
                .setBlurRadius(blurRadius2)
            binding.blurView2.setupWith(binding.main, RenderScriptBlur(this))
                .setBlurRadius(blurRadius2)
            binding.navViewBottom.setupWith(binding.main, RenderScriptBlur(this))
                .setBlurRadius(blurRadius3)
        }

        binding.drive.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                .create()
            val view = layoutInflater.inflate(R.layout.custom_dialog,null)
            builder.setView(view)
            builder.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            builder.window?.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)

            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.setCancelable(false)
            inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


            val yes  = view.findViewById<AppCompatButton>(R.id.btn_yes)
            val no = view.findViewById<AppCompatButton>(R.id.btn_no)
            val blurViewDialog = view.findViewById<BlurView>(R.id.blurViewDialog)
            val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)

            blurViewDialog.clipToPadding = true
            val blurRadiusDialog = 6f

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                blurViewDialog.setupWith(constraintLayout, RenderEffectBlur())
                    .setBlurRadius(blurRadius)
            } else {
                blurViewDialog.setupWith(constraintLayout, RenderScriptBlur(this))
                    .setBlurRadius(blurRadius2)
            }

            /* Set the view of the dialog box. */
//        builder.setView(customLayout)

            yes.setOnClickListener {
                builder.dismiss()
            }

            no.setOnClickListener {
                builder.dismiss()
            }
            /* Showing the dialog box. */
//        alertDialog = builder.create()
            builder.show()
        }
    }
}