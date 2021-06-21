package com.example.minigamestada

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
class TTT_SingleFragment : Fragment() {

    var curPlayer: Int = -1
    val row = 3
    val col = 3

    val robotNames = ArrayList<String>()

    var v: View? = null

    var matrix = Array(row) { IntArray(col) }

    var myDrawable: Int = -1
    var opponentDrawable: Int = -1

    lateinit var lottiePrize: LottieAnimationView
    lateinit var lottieTossCoin: LottieAnimationView
    lateinit var yourPlayImage: LottieAnimationView
    lateinit var opponentPlayImage: LottieAnimationView
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
    lateinit var opponentProfilePic_ImageView: CircleImageView
    lateinit var gameOverCaption: TextView
    lateinit var myName: TextView
    lateinit var opponentName: TextView
    lateinit var restartBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_t_t__single, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRobotNames()
        MainActivity.flag = true
        navController = Navigation.findNavController(view)
        v = view
        initViewsAndListeners(view)
        disableBoard(view)
        toss(view)
        lottiePrize = view.findViewById(R.id.lottiePrizeAnimation_Singleplayer)
        lottiePrize.setAnimation(R.raw.prize)
        lottiePrize.playAnimation()

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

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.flag = false
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun toss(view: View) {
        lottieTossCoin = view.findViewById(R.id.tossTheCoin)!!
        lottieTossCoin.visibility = View.VISIBLE
        lottieTossCoin.setAnimation(R.raw.dicetoss)
        lottieTossCoin.repeatCount = 3
        lottieTossCoin.playAnimation()

        val roboVal: Int = ((Math.random() * 2021) % 10).toInt()
        opponentName.text = robotNames[roboVal]
        opponentProfilePic_ImageView.circleBackgroundColor = getRandomColor()
        gameOverCaption.visibility = View.VISIBLE
        gameOverCaption.text = "Wait For The Toss!!"
        Handler().postDelayed({
            val x: Int = ((Math.random() * 21) % 2).toInt()
            if (x == 0) {
                Toast.makeText(view.context, "You Won The Toss", Toast.LENGTH_SHORT).show()
                gameOverCaption.text = "You Won The Toss!!"
                lottieTossCoin.setAnimation(R.raw.obox)
                yourPlayImage.setAnimation(R.raw.obox)
                opponentPlayImage.setAnimation(R.raw.xbox)
                myDrawable = R.raw.obox
                opponentDrawable = R.raw.xbox
                curPlayer = 1
                enableBoard(view)
                play(view)
            } else {
                Toast.makeText(view.context, "Your Opponent Won The Toss", Toast.LENGTH_SHORT)
                    .show()
                gameOverCaption.text = "Your Opponent Won The Toss!!"
                lottieTossCoin.setAnimation(R.raw.xbox)
                yourPlayImage.setAnimation(R.raw.xbox)
                opponentPlayImage.setAnimation(R.raw.obox)
                myDrawable = R.raw.xbox
                opponentDrawable = R.raw.obox
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

    private fun disableBoard(view: View) {
        l11.isEnabled = false
        l12.isEnabled = false
        l13.isEnabled = false
        l21.isEnabled = false
        l22.isEnabled = false
        l23.isEnabled = false
        l31.isEnabled = false
        l32.isEnabled = false
        l33.isEnabled = false
    }


    private fun enableBoard(view: View) {
        l11.isEnabled = true
        l12.isEnabled = true
        l13.isEnabled = true
        l21.isEnabled = true
        l22.isEnabled = true
        l23.isEnabled = true
        l31.isEnabled = true
        l32.isEnabled = true
        l33.isEnabled = true
    }

    private fun initViewsAndListeners(view: View) {
        opponentProfilePic_ImageView = view.findViewById(R.id.opponentProfilePic_ImageView)
        myName = view.findViewById(R.id.myName)
        opponentName = view.findViewById(R.id.opponentName)
        restartBtn = view.findViewById(R.id.restartBtn)
        gameOverCaption = view.findViewById(R.id.gameOverCaption)
        opponentPlayImage = view.findViewById(R.id.opponentPlayImage)
        yourPlayImage = view.findViewById(R.id.myPlayImage)
        lottieTossCoin = view.findViewById(R.id.tossTheCoin)
        l11 = view.findViewById(R.id.animation_view11)
        l12 = view.findViewById(R.id.animation_view12)
        l13 = view.findViewById(R.id.animation_view13)
        l21 = view.findViewById(R.id.animation_view21)
        l22 = view.findViewById(R.id.animation_view22)
        l23 = view.findViewById(R.id.animation_view23)
        l31 = view.findViewById(R.id.animation_view31)
        l32 = view.findViewById(R.id.animation_view32)
        l33 = view.findViewById(R.id.animation_view33)

        restartBtn.setOnClickListener {
            navController.navigate(R.id.action_TTT_SingleFragment_self)
        }

        l11.setOnClickListener {
            if (matrix[0][0] == 1 || matrix[0][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l11.setAnimation(myDrawable)
                stopAnimation(l11)
                curPlayer = 2
                disableBoard(view)
                matrix[0][0] = 1
                play(view)
            }
        }
        l12.setOnClickListener {
            if (matrix[0][1] == 1 || matrix[0][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l12.setAnimation(myDrawable)
                stopAnimation(l12)
                curPlayer = 2
                disableBoard(view)
                matrix[0][1] = 1
                play(view)
            }
        }
        l13.setOnClickListener {
            if (matrix[0][2] == 1 || matrix[0][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l13.setAnimation(myDrawable)
                stopAnimation(l13)
                curPlayer = 2
                disableBoard(view)
                matrix[0][2] = 1
                play(view)
            }
        }
        l21.setOnClickListener {
            if (matrix[1][0] == 1 || matrix[1][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l21.setAnimation(myDrawable)
                stopAnimation(l21)
                curPlayer = 2
                disableBoard(view)
                matrix[1][0] = 1
                play(view)
            }
        }
        l22.setOnClickListener {
            if (matrix[1][1] == 1 || matrix[1][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l22.setAnimation(myDrawable)
                stopAnimation(l22)
                curPlayer = 2
                disableBoard(view)
                matrix[1][1] = 1
                play(view)
            }
        }
        l23.setOnClickListener {
            if (matrix[1][2] == 1 || matrix[1][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l23.setAnimation(myDrawable)
                stopAnimation(l23)
                curPlayer = 2
                disableBoard(view)
                matrix[1][2] = 1
                play(view)
            }
        }
        l31.setOnClickListener {
            if (matrix[2][0] == 1 || matrix[2][0] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l31.setAnimation(myDrawable)
                stopAnimation(l31)
                curPlayer = 2
                disableBoard(view)
                matrix[2][0] = 1
                play(view)
            }
        }
        l32.setOnClickListener {
            if (matrix[2][1] == 1 || matrix[2][1] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l32.setAnimation(myDrawable)
                stopAnimation(l32)
                curPlayer = 2
                disableBoard(view)
                matrix[2][1] = 1
                play(view)
            }
        }
        l33.setOnClickListener {
            if (matrix[2][2] == 1 || matrix[2][2] == 2) {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            } else {
                l33.setAnimation(myDrawable)
                stopAnimation(l33)
                curPlayer = 2
                disableBoard(view)
                matrix[2][2] = 1
                play(view)
            }
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
        if (curPlayer == 1) {
            processMatrix()
            if (isMatrixFull()) {
                gameOver(0)
                return
            }
        } else if (curPlayer == 2) {
            Handler().postDelayed({
                if (isMatrixFull()) {
                    gameOver(0)
                    processMatrix()
                } else {
                    playRandom()
                    curPlayer = 1
                    enableBoard(view)
                    gameOverCaption.text = "Your Turn"
                    gameOverCaption.visibility = View.VISIBLE
                    Handler().postDelayed({
                        gameOverCaption.visibility = View.GONE
                    }, 500)
                }
            }, 500)
        }

        for (i in 0 until row) {
            for (j in 0 until col) {
                print(matrix[i][j])
            }
            println()
        }
    }

    private fun playRandom() {
        for (i in 0 until row) {

            //row game
            if (matrix[i][0] == 1 && matrix[i][1] == 1 && matrix[i][2] != 1 && matrix[i][2] != 2) {
                matrix[i][2] = curPlayer
                setDrawable(i, 2)
                return
            }
            if (matrix[i][1] == 1 && matrix[i][2] == 1 && matrix[i][0] != 1 && matrix[i][0] != 2) {
                matrix[i][0] = curPlayer
                setDrawable(i, 0)
                return
            }
            if (matrix[i][0] == 1 && matrix[i][2] == 1 && matrix[i][1] != 1 && matrix[i][1] != 2) {
                matrix[i][1] = curPlayer
                setDrawable(i, 1)
                return
            }

            //column game
            if (matrix[0][i] == 1 && matrix[1][i] == 1 && matrix[2][i] != 1 && matrix[2][i] != 2) {
                matrix[2][i] = curPlayer
                setDrawable(2, i)
                return
            }
            if (matrix[1][i] == 1 && matrix[2][i] == 1 && matrix[0][i] != 1 && matrix[0][i] != 2) {
                matrix[0][i] = curPlayer
                setDrawable(0, i)
                return
            }
            if (matrix[0][i] == 1 && matrix[2][i] == 1 && matrix[1][i] != 1 && matrix[1][i] != 2) {
                matrix[1][i] = curPlayer
                setDrawable(1, i)
                return
            }
        }

        for (i in 0 until row) {
            for (j in 0 until col) {
                if (matrix[i][j] != 1 && matrix[i][j] != 2) {
                    matrix[i][j] = curPlayer
                    setDrawable(i, j)
                    return
                }
            }
        }

    }

    private fun setDrawable(i: Int, j: Int) {
        if (i == 0 && j == 0) {
            l11.setAnimation(opponentDrawable)
            stopAnimation(l11)
        } else if (i == 0 && j == 1) {
            l12.setAnimation(opponentDrawable)
            stopAnimation(l12)
        } else if (i == 0 && j == 2) {
            l13.setAnimation(opponentDrawable)
            stopAnimation(l13)
        } else if (i == 1 && j == 0) {
            l21.setAnimation(opponentDrawable)
            stopAnimation(l21)
        } else if (i == 1 && j == 1) {
            l22.setAnimation(opponentDrawable)
            stopAnimation(l22)
        } else if (i == 1 && j == 2) {
            l23.setAnimation(opponentDrawable)
            stopAnimation(l23)
        } else if (i == 2 && j == 0) {
            l31.setAnimation(opponentDrawable)
            stopAnimation(l31)
        } else if (i == 2 && j == 1) {
            l32.setAnimation(opponentDrawable)
            stopAnimation(l32)
        } else if (i == 2 && j == 2) {
            l33.setAnimation(opponentDrawable)
            stopAnimation(l33)
        }
        processMatrix()
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
            if (isMatrixFull()) {
                gameOver(0)
                return
            }
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

    private fun gameOver(i: Int) {
        if (i == 1) {
            lottieTossCoin.visibility = View.VISIBLE
            lottieTossCoin.setAnimation(R.raw.winner)
            lottieTossCoin.playAnimation()
        }
        if(i==2 || i==0){
            disableBoard(v!!)
        }
        Handler().postDelayed({
            if (i == 1) {
                lottieTossCoin.repeatCount = 15
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "You Won The Match!!"
            } else if (i == 2) {
                lottieTossCoin.visibility = View.VISIBLE
                lottieTossCoin.setAnimation(R.raw.looser)
                lottieTossCoin.repeatCount = 10
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "You Loss The Match!!"
            } else if (i == 0) {
                lottieTossCoin.visibility = View.VISIBLE
                lottieTossCoin.setAnimation(R.raw.drawmatch)
                lottieTossCoin.repeatCount = 10
                gameOverCaption.visibility = View.VISIBLE
                gameOverCaption.text = "LOL! Draw Match!!"
            }
            lottieTossCoin.playAnimation()
        }, 3000)
    }

}
