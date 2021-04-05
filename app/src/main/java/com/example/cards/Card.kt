package com.example.cards
import android.view.View
import java.time.LocalDateTime
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong


open class Card(
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().toString(),
    var id: String = UUID.randomUUID().toString()

    ) {
    var quality = 0
    protected var repetitions = 0
    protected var interval = 1L
    var nextPracticeDate = date
    var easiness = 2.5
    var answered = false

    open fun show() {
        print("$question (INTRO para ver respuesta)")
        readLine()
        print("$answer (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): ")
        quality = readLine()?.toInt() ?: throw InputMismatchException()
    }

    fun update(currentDate: LocalDateTime) {
        val value: Double = easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)
        easiness = max(1.3, value)
        if (quality < 3)
            repetitions = 0
        else
            repetitions += 1

        interval = when {
            repetitions <= 1 -> 1L
            repetitions == 2 -> 6L
            else -> (easiness * interval).roundToLong()
        }
        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    fun details() {
        print("eas = ${"%.2f".format(easiness)} ")
        print("rep = $repetitions int =$interval ")
        println("next = ${nextPracticeDate.substring(0, 10)}")
    }
    override fun toString()=
        "card| $question | $answer | $date | $id | $easiness | $repetitions | $interval | $nextPracticeDate\\n\"\n"

    fun setDetails(easiness:Double,repetitions:Int,interval:Long,nextPracticeDate:String){
        this.easiness=easiness
        this.repetitions=repetitions
        this.interval=interval
        this.nextPracticeDate=nextPracticeDate

    }
    companion object{
        fun fromString(cad:String): Card {
            val tokens= cad.split("|")
            val q= tokens[1].trim()
            val a= tokens[2].trim()
            val card=Card(q,a)
            card.date = tokens[3].trim()
            card.id= tokens[4].trim()
            card.setDetails(
                tokens[5].trim().toDouble(),
                tokens[6].trim().toInt(),
                tokens[7].trim().toLong(),
                tokens[8].trim()
            )
            return card
        }
    }

    fun update_easy() {
        quality = 5
        update(LocalDateTime.now())
    }
    fun update_doubt() {
        quality = 3
        update(LocalDateTime.now())
    }
    fun update_difficult() {
        quality = 0
        update(LocalDateTime.now())
    }
}


