package com.clay.covid_19helper.util

import android.R
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Interpolator
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import timber.log.Timber
import kotlin.math.hypot


object AnimationUtils {

    fun View.shrinkView(
        duration: Long,
        interpolator: Interpolator,
        startDimen: Int,
        endDimen: Int
    ) {

    }

    fun View.expandView(
        duration: Long,
        interpolator: Interpolator,
        startDimen: Int,
        endDimen: Int
    ) {

    }


    fun View.expandToolbar(
        duration: Long,
        parent_container: View,
        onlyList: Boolean
    ) {

        if (onlyList) {
            expandRecyclerView(duration, parent_container.height)
            return
        }

        val valueAnimatorWidth = ValueAnimator.ofInt(this.width, parent_container.width)
        valueAnimatorWidth.duration = duration
        valueAnimatorWidth.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.width = value
            requestLayout()
        }

        val valueAnimatorPosition = ValueAnimator.ofInt(top, 0)
        valueAnimatorPosition.duration = duration
        valueAnimatorPosition.addUpdateListener {
            val value = it.animatedValue as Int
            y = value.toFloat()
        }

        val valueAnimatorHeight = ValueAnimator.ofInt(height, parent_container.height)
        valueAnimatorHeight.duration = duration
        valueAnimatorHeight.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }

        valueAnimatorPosition.doOnStart {
            valueAnimatorWidth.start()
            valueAnimatorHeight.start()
        }

        valueAnimatorPosition.start()
        Timber.d("y: ${pivotY} and height/2 = ${height / 2} and y = $y")
    }

    fun View.resetToolbar(
        duration: Long,
        orgWidth: Int,
        orgTop: Int,
        orgHeight: Int
    ) {
        val va = ValueAnimator.ofInt(this.width, orgWidth)
        va.duration = duration
        va.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.width = value
            requestLayout()
        }

        val va2 = ValueAnimator.ofInt(y.toInt(), orgTop)
        va2.duration = duration
        va2.addUpdateListener {
            val value = it.animatedValue as Int
            y = value.toFloat()
        }

        val valueAnimatorHeight = ValueAnimator.ofInt(height, orgHeight)
        valueAnimatorHeight.duration = duration
        valueAnimatorHeight.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }

        va2.doOnStart {
            va.start()
            valueAnimatorHeight.start()
        }
        va2.start()
    }

    fun View.collapseRecyclerView(duration: Long, orgHeight: Int) {
        val valueAnimatorHeight = ValueAnimator.ofInt(height, orgHeight)
        valueAnimatorHeight.duration = duration
        valueAnimatorHeight.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }
        valueAnimatorHeight.start()
    }

    fun View.expandRecyclerView(duration: Long, target_height: Int) {
        val valueAnimatorHeight = ValueAnimator.ofInt(height, target_height)
        valueAnimatorHeight.duration = duration
        valueAnimatorHeight.addUpdateListener {
            val value = it.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }
        valueAnimatorHeight.start()
    }


    fun View.shrinkMap() {
        val va = ValueAnimator.ofInt(this.height, 65 * this.height / 100)
        va.duration = 500
        va.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }
        va.start()
    }

    fun View.expandMap(target_height: Int) {
        val va = ValueAnimator.ofInt(this.height, target_height)
        va.duration = 500
        va.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            layoutParams.height = value
            requestLayout()
        }
        va.start()
    }


    fun View.expandCircle() {
        val cx = width / 2
        val cy = height / 2
        val finalRadius =
            hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim =
            ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
        visibility = VISIBLE
        anim.interpolator = AccelerateInterpolator()
        anim.start()
    }

    fun View.shrinkCircle() {
        val cx = width / 2
        val cy = height / 2
        val initialRadius =
            hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim =
            ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius, 0f)
        anim.doOnEnd {
            visibility = GONE
        }
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.start()
    }


}