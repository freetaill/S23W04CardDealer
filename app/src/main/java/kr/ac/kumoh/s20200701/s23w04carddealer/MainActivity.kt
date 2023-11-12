package kr.ac.kumoh.s20200701.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20200701.s23w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    val message = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            val res = IntArray(5)
            for (i in it.indices) {
                res[i] = resources.getIdentifier(
                    getCardName(it[i]),
                    "drawable",
                    packageName
                )
            }

            main.list.text = fin_result(it)
            main.card1.setImageResource(res[0])
            main.card2.setImageResource(res[1])
            main.card3.setImageResource(res[2])
            main.card4.setImageResource(res[3])
            main.card5.setImageResource(res[4])
        })

        main.btnGenerate.setOnClickListener {
            model.shuffle()
        }
    }

    private fun getCardName(c: Int) : String {
        var shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> {
                shape = shape.plus("2")
                "jack"
            }
            11 -> {
                shape = shape.plus("2")
                "queen"
            }
            12 -> {
                shape = shape.plus("2")
                "king"
            }
            else -> "error"
        }
        return "c_${number}_of_${shape}"
    }

    private fun fin_result(v: IntArray): String{
        val number = mutableMapOf<Int, Int>()
        val shape = mutableMapOf<Int, Int>()
        var num = 0

        for (card in v) {
            val rank = card % 13
            number[rank] = number.getOrDefault(rank, 0) + 1
        }
        for (card in v) {
            val rank = card / 13
            shape[rank] = shape.getOrDefault(rank, 0) + 1
        }
        if(shape.values.any { it == 5 }){
            if(number == listOf(0, 1, 2, 3, 4)){
                return "Back Straight Flush!"
            }
            else if(number == listOf(v[0], v[0]+1, v[0]+2, v[0]+3, v[0]+4)){
                return "Straight Flush!"
            }
            else if(number == listOf(0, 9, 10, 11, 12)){
                return "Royal Straight Flush!"
            }
            else{
                return "Flush!"
            }
        }
        if(number.values.any { it == 4 }){
            return "Four Card!"
        }
        else if(number.values.any { it == 3 }){
            if(number.values.any { it == 2 }){
                return "Full House!"
            }
            else{
                return "Triple!"
            }
        }
        else if(number.values.any { it == 2 }){
            num = 0
            for(i in 0..12){
                if (number[i] == 2){
                    num = num + 1
                }
            }
            if(num == 2){
                return "Two Pair!"
            }
            else{
                return "Pair!"
            }
        }
        if(number == listOf(0, 1, 2, 3, 4)){
            return "Back Straight!"
        }
        else if(number == listOf(v[0], v[0]+1, v[0]+2, v[0]+3, v[0]+4)){
            return "Straight!"
        }
        else if(number == listOf(0, 9, 10, 11, 12)){
            return "Mountain!"
        }
        else{
            return "Top!"
        }
    }
}