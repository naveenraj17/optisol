package com.naveenraj.optisolpro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.naveenraj.optisolpro.R

class SplashView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_view)
        val img: ImageView = findViewById(R.id.img)
        //loading our custom made animations
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        //starting the animation
        img.startAnimation(animation)

        Handler().postDelayed({
            startActivity(Intent(this,DashboardView::class.java))
            finish()
        }, 1000)
    }
}