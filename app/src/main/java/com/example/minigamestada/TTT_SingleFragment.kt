package com.example.minigamestada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TTT_SingleFragment : Fragment() {

    lateinit var lottiePrize: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_t_t__single, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottiePrize = view.findViewById(R.id.lottiePrizeAnimation_Singleplayer)
        lottiePrize.setAnimation(R.raw.prize)
        lottiePrize.playAnimation()
    }


}