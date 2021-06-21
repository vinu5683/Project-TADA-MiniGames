package com.example.minigamestada

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class TTT_Two_OffilneFragment : Fragment() {

    var curPlayer: Int = -1
    val row = 3
    val col = 3

    val robotNames = ArrayList<String>()

    var v: View? = null

    var matrix = Array(row) { IntArray(col) }

    var player1Drawable: Int = -1
    var player2Drawable: Int = -1

    lateinit var lottiePrize: LottieAnimationView
    lateinit var lottieTossCoin: LottieAnimationView
    lateinit var player1PlayImage: LottieAnimationView
    lateinit var player2PlayImage: LottieAnimationView
    lateinit var l11: LottieAnimationView
    lateinit var l12: LottieAnimationView
    lateinit var l13: LottieAnimationView
    lateinit var l21: LottieAnimationView
    lateinit var l22: LottieAnimationView
    lateinit var l23: LottieAnimationView
    lateinit var l31: LottieAnimationView
    lateinit var l32: LottieAnimationView
    lateinit var l33: LottieAnimationView
    lateinit var navController: NavController
    lateinit var player1ProfilePic: CircleImageView
    lateinit var player2ProfilePic: CircleImageView
    lateinit var gameOverCaption: TextView
    lateinit var player1Name: TextView
    lateinit var player2Name: TextView
    lateinit var restartBtn: Button
    lateinit var player1_highlight_container_two: ConstraintLayout
    lateinit var player2_highlight_container_two: ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_t_t__two__offilne, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view

        setRobotNames()
        MainActivity.flag = true
        navController = Navigation.findNavController(view)
        initViewsAndListeners(view)
        toss(view)

    }

    private fun setRobotNames() {
        robotNames.add("R2-D2")
        robotNames.add("Bender")
        robotNames.add("Bishop")
        robotNames.add("Terminator")
        robotNames.add("Wall-E")
        robotNames.add("EVA")
        robotNames.add("C-3PO")
        robotNames.add("Chewbacca")
        robotNames.add("Doraemon")
        robotNames.add("KITT")
    }


    private fun initViewsAndListeners(view: View) {
        player1ProfilePic = view.findViewById(R.id.player1img_two)
        player1_highlight_container_two = view.findViewById(R.id.player1_highlight_container_two)
        player2_highlight_container_two = view.findViewById(R.id.player2_highlight_container_two)
        player2ProfilePic = view.findViewById(R.id.player2img_two)
        player1Name = view.findViewById(R.id.player1name_two)
        player2Name = view.findViewById(R.id.player2Name_two)
        restartBtn = view.findViewById(R.id.restartBtn_two)
        gameOverCaption = view.findViewById(R.id.gameOverCaption_two)
        player2PlayImage = view.findViewById(R.id.player2PlayImage_two)
        player1PlayImage = view.findViewById(R.id.player1PlayImage_two)
        lottieTossCoin = view.findViewById(R.id.tossTheCoin_two)
        l11 = view.findViewById(R.id.animation_two_view11)
        l12 = view.findViewById(R.id.animation_two_view12)
        l13 = view.findViewById(R.id.animation_two_view13)
        l21 = view.findViewById(R.id.animation_two_view21)
        l22 = view.findViewById(R.id.animation_two_view22)
        l23 = view.findViewById(R.id.animation_two_view23)
        l31 = view.findViewById(R.id.animation_two_view31)
        l32 = view.findViewById(R.id.animation_two_view32)
        l33 = view.findViewById(R.id.animation_two_view33)

        restartBtn.setOnClickListener {
            navController.navigate(R.id.action_TTT_Two_OffilneFragment_self)
        }

        l11.setOnClickListener {
            if (matrix[0][0] == 1 || matrix[0][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l11, 0, 0)
                play(view)
            }
        }
        l12.setOnClickListener {
            if (matrix[0][1] == 1 || matrix[0][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l12, 0, 1)
                play(view)
            }
        }
        l13.setOnClickListener {
            if (matrix[0][2] == 1 || matrix[0][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l13, 0, 2)
                play(view)
            }
        }
        l21.setOnClickListener {
            if (matrix[1][0] == 1 || matrix[1][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l21, 1, 0)
                play(view)
            }
        }
        l22.setOnClickListener {
            if (matrix[1][1] == 1 || matrix[1][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l22, 1, 1)

                play(view)
            }
        }
        l23.setOnClickListener {
            if (matrix[1][2] == 1 || matrix[1][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l23, 1, 2)
                play(view)
            }
        }
        l31.setOnClickListener {
            if (matrix[2][0] == 1 || matrix[2][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l31, 2, 0)
                play(view)
            }
        }
        l32.setOnClickListener {
            if (matrix[2][1] == 1 || matrix[2][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l32, 2, 1)
                play(view)
            }
        }
        l33.setOnClickListener {
            if (matrix[2][2] == 1 || matrix[2][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                game(l33, 2, 2)
                play(view)
            }
        }
    }

    private fun game(l: LottieAnimationView?, i: Int, j: Int) {
        if (curPlayer == 1) {
            l?.setAnimation(player1Drawable)
            stopAnimation(l!!)
            matrix[i][j] = 1
            player2_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
            player1_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
            curPlayer = 2
        } else if (curPlayer == 2) {
            l?.setAnimation(player2Drawable)
            stopAnimation(l!!)
            curPlayer = 1
            player1_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
            player2_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
            matrix[i][j] = 2
        }
    }

    fun stopAnimation(ll: LottieAnimationView) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2900)
            CoroutineScope(Dispatchers.Main).launch {
                ll.pauseAnimation()
            }
        }
    }

    fun play(view: View) {
        processMatrix()
        for (i in 0 until row) {
            for (j in 0 until col) {
                print(matrix[i][j])
            }
            println()
        }
    }

    private fun processMatrix() {
        for (i in 0 until row) {
            //row
            if (matrix[i][0] == 1 && matrix[i][1] == 1 && matrix[i][2] == 1) {
                gameOver(1)
                return
            }
            if (matrix[i][0] == 2 && matrix[i][1] == 2 && matrix[i][2] == 2) {
                gameOver(2)
                return
            }

            //column
            if (matrix[0][i] == 1 && matrix[1][i] == 1 && matrix[2][i] == 1) {
                gameOver(1)
                return
            }
            if (matrix[0][i] == 2 && matrix[1][i] == 2 && matrix[2][i] == 2) {
                gameOver(2)
                return
            }

            //Primary Diagonal
            if (i == 0 && matrix[0][0] == 1 && matrix[1][1] == 1 && matrix[2][2] == 1) {
                gameOver(1)
                return
            }
            if (i == 0 && matrix[0][0] == 2 && matrix[1][1] == 2 && matrix[2][2] == 2) {
                gameOver(2)
                return
            }

            //Secondary Diagonal
            if (i == 0 && matrix[0][2] == 1 && matrix[1][1] == 1 && matrix[2][0] == 1) {
                gameOver(1)
                return
            }
            if (i == 0 && matrix[0][2] == 2 && matrix[1][1] == 2 && matrix[2][0] == 2) {
                gameOver(2)
                return
            }
        }
        if (isMatrixFull()) {
            gameOver(0)
            return
        }

    }

    private fun isMatrixFull(): Boolean {
        for (i in 0 until row) {
            for (j in 0 until row) {
                if (matrix[i][j] == 0) {
                    Log.d("TAG", "isMatrixFull: " + false)
                    return false
                }
            }
        }
        return true
    }

    private fun toss(view: View) {
        lottieTossCoin = view.findViewById(R.id.tossTheCoin_two)!!
        lottieTossCoin.visibility = View.VISIBLE
        lottieTossCoin.setAnimation(R.raw.dicetoss)
        lottieTossCoin.repeatCount = 3
        lottieTossCoin.playAnimation()

        player2ProfilePic.circleBackgroundColor = getRandomColor()
        player1ProfilePic.circleBackgroundColor = getRandomColor()
        gameOverCaption.visibility = View.VISIBLE
        gameOverCaption.text = "Wait For The Toss!!"
        Handler().postDelayed({
            val x: Int = ((Math.random() * 21) % 2).toInt()
            if (x == 0) {
                Toast.makeText(view.context, "Player1 Won The Toss", Toast.LENGTH_SHORT).show()
                gameOverCaption.text = "Player1 Won The Toss!!"
                lottieTossCoin.setAnimation(R.raw.obox)
                player1PlayImage.setAnimation(R.raw.obox)
                player1Drawable = R.raw.obox
                player2PlayImage.setAnimation(R.raw.xbox)
                player2Drawable = R.raw.xbox
                player1_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
                player2_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
                curPlayer = 1
                play(view)
            } else {
                Toast.makeText(view.context, "Player2 Won The Toss", Toast.LENGTH_SHORT)
                    .show()
                gameOverCaption.text = "Player2 Won The Toss!!"
                lottieTossCoin.setAnimation(R.raw.obox)
                player1PlayImage.setAnimation(R.raw.xbox)
                player1Drawable = R.raw.xbox
                player2PlayImage.setAnimation(R.raw.obox)
                player2Drawable = R.raw.obox
                player2_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
                player1_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
                curPlayer = 2
                play(view)
            }
            lottieTossCoin.playAnimation()
            Handler().postDelayed({
                gameOverCaption.visibility = View.GONE
                lottieTossCoin.visibility = View.GONE
            }, 2000)
        }, 3440)
    }

    private fun gameOver(i: Int) {
        lottieTossCoin.visibility = View.VISIBLE
        lottieTossCoin.setAnimation(R.raw.gameover)
        lottieTossCoin.playAnimation()
        Handler().postDelayed({
            if (i == 1) {
                lottieTossCoin.setAnimation(R.raw.winner)
                lottieTossCoin.repeatCount = 15
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "Player1 Won The Match!!"
                player1_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
                player2_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
            } else if (i == 2) {
                lottieTossCoin.setAnimation(R.raw.winner)
                lottieTossCoin.repeatCount = 10
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "Player2 Won The Match!!"
                player2_highlight_container_two.setBackgroundResource(R.drawable.rectangle)
                player1_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
            } else if (i == 0) {
                lottieTossCoin.setAnimation(R.raw.drawmatch)
                lottieTossCoin.repeatCount = 10
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "LOL! Draw Match!!"
                player2_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
                player1_highlight_container_two.setBackgroundResource(R.color.backgroud_global)
            }
            lottieTossCoin.playAnimation()
        }, 3000)
    }


    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.flag = false
    }


}