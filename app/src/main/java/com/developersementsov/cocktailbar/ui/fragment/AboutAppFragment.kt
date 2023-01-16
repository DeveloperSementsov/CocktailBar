package com.developersementsov.cocktailbar.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.developersementsov.cocktailbar.R
import com.developersementsov.cocktailbar.databinding.FragmentAboutAppBinding


class AboutAppFragment : Fragment() {
    private lateinit var _binding: FragmentAboutAppBinding
    private val binding get() = _binding
    private var isAnimate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = requireContext().getString(R.string.about_app_fragment_title)
        binding.imageView.setOnClickListener {
            startAnimation(it as ImageView)

        }


    }

    private fun startAnimation(
        image: ImageView
    ) {
        if (!isAnimate) {
            val set = AnimatorSet()
            set.playSequentially(
                beginAnimatorSet(image),
                toNormalAnimatorSet(image),
                hideAnimatorSet(image)
            )
            set.addListener(getAnimationListener(set, image))
            set.start()
        }
        image.animate().alphaBy(0f).alpha(1f).start()
    }

    private fun getAnimationListener(set: AnimatorSet, image: ImageView): AnimatorListenerAdapter {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                isAnimate = true
                image.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isAnimate = false
                image.setLayerType(View.LAYER_TYPE_NONE, null)
                set.start()
            }
        }
    }

    private fun beginAnimatorSet(view: ImageView): AnimatorSet {
        val set = AnimatorSet()
        set.setDuration(BEGIN_DURATION).playTogether(
            ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.5f),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.5f),
            ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f),
            ObjectAnimator.ofFloat(view, View.ROTATION_X, 360f)
        )
        return set
    }

    private fun toNormalAnimatorSet(view: ImageView): AnimatorSet {
        val set = AnimatorSet()
        set.setDuration(TO_NORMAL_DURATION).playTogether(
            ObjectAnimator.ofFloat(view, View.SCALE_X, 1.5f, 1f),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.5f, 1f)
        )
        return set
    }

    private fun hideAnimatorSet(view: ImageView): AnimatorSet {
        val set = AnimatorSet()
        set.setDuration(HIDE_DURATION).playTogether(
            ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f),
            ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.2f),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.2f),
            ObjectAnimator.ofFloat(view, View.ROTATION_Y, 360f)
        )
        set.startDelay = HIDE_DELAY
        return set
    }

    companion object {
        private const val BEGIN_DURATION: Long = 2000
        private const val TO_NORMAL_DURATION: Long = 3000
        private const val HIDE_DURATION: Long = 2000
        private const val HIDE_DELAY: Long = 2000
    }
}