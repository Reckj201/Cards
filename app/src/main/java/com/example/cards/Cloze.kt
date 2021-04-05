package com.example.cards
import java.util.*

class Cloze(
    question: String,
    answer: String
) : Card(question, answer) {

    override fun show() {
        var resultado = ""
        var entreParentesis = false

        for (c in question) {
            if (c == '*') {
                if (!entreParentesis) {
                    resultado += answer
                    entreParentesis = true
                } else
                    entreParentesis = false
            } else if (!entreParentesis)
                resultado += c
        }

        print("$question (INTRO para ver respuesta) ")
        readLine()
        print("$resultado (Teclea 0 -> Dificil 3 -> Dudo 5 -> Facil): ")
        quality = readLine()?.toInt() ?: throw InputMismatchException()
    }
    override fun toString()=
        "cloze | $question | $answer | $date | $id | $easiness | $repetitions | $interval | $nextPracticeDate\n"

    companion object{
        fun fromString(cad:String): Cloze{
            val tokens= cad.split("|")
            val q= tokens[1].trim()
            val a= tokens[2].trim()
            val card=Cloze(q,a)
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
}
