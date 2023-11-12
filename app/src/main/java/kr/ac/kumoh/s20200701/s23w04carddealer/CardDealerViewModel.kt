package kr.ac.kumoh.s20200701.s23w04carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CardDealerViewModel : ViewModel() {
    private var _cards = MutableLiveData<IntArray>(IntArray(5) { -1 })
    val cards: LiveData<IntArray>
        get() = _cards
    fun shuffle() {
        var num = 0
        val newCards = IntArray(5) { -1 }

        for (i in newCards.indices) {
            // 중복 검사
            do {
                num = Random.nextInt(52)
            } while (newCards.contains(num))
            newCards[i] = num
        }

        // 정렬
        newCards.sort()

        _cards.value = newCards
    }
}