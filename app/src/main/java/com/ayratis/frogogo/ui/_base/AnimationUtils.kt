package com.ayratis.frogogo.ui._base

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.os.Parcelable
import android.view.ViewAnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.parcel.Parcelize
import kotlin.math.sqrt
import android.animation.AnimatorListenerAdapter


object AnimationUtils {
    fun registerCircularRevealAnimation(
        context: Context?,
        view: View?,
        revealSettings: RevealAnimationSetting,
        startColor: Int,
        endColor: Int
    ) {
        if (view == null || context == null) return
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                p0: View?,
                p1: Int,
                p2: Int,
                p3: Int,
                p4: Int,
                p5: Int,
                p6: Int,
                p7: Int,
                p8: Int
            ) {
                p0?.removeOnLayoutChangeListener(this)
                val cx = revealSettings.centerX
                val cy = revealSettings.centerY
                val width = revealSettings.width.toDouble()
                val height = revealSettings.height.toDouble()
                val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime)

                val finalRadius = sqrt(width * width + height * height).toFloat()
                val anim = ViewAnimationUtils
                    .createCircularReveal(p0, cx, cy, 0f, finalRadius)
                    .apply {
                        this.duration = duration.toLong()
                        interpolator = FastOutSlowInInterpolator()
                    }
                anim.start()
                startColorAnimation(view, startColor, endColor, duration)
            }

        })
    }

    fun startColorAnimation(view: View, startColor: Int, endColor: Int, duration: Int) {
        ValueAnimator().apply {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener {
                view.setBackgroundColor(it.animatedValue as Int)
            }
            setDuration(duration.toLong())
        }.start()

    }

    fun startCircularRevealExitAnimation(
        context: Context?,
        view: View?,
        revealSettings: RevealAnimationSetting,
        startColor: Int,
        endColor: Int,
        listener: () -> Unit
    ) {
        if (context == null || view == null) return
        val cx = revealSettings.centerX
        val cy = revealSettings.centerY
        val width = revealSettings.width.toDouble()
        val height = revealSettings.height.toDouble()
        val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime)

        val initRadius = sqrt(width * width + height * height).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initRadius, 0f)
        anim.duration = duration.toLong()
        anim.interpolator = FastOutSlowInInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
                listener.invoke()
            }
        })

        anim.start()
        startColorAnimation(view, startColor, endColor, duration)

    }
}

@Parcelize
data class RevealAnimationSetting(
    val centerX: Int,
    val centerY: Int,
    val width: Int,
    val height: Int
) : Parcelable
