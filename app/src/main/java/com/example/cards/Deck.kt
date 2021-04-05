package com.example.cards
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.util.*

class Deck(
    private var name: String,
    private var id: String = UUID.randomUUID().toString()
) {
    private val cards: MutableList<Card> = mutableListOf()

    fun addCard() {
        println("Añadiendo tarjeta al mazo $name: ")
        print("Teclea el tipo (0 -> Card 1 -> Cloze): ")
        val tipo = readLine()?.toInt()

        print("Teclea la pregunta: ")
        val question = readLine()
        print("Teclea la respuesta: ")
        val answer = readLine()

        if (question == null || answer == null)
            throw InputMismatchException()

        cards.add(
            when (tipo) {
                0 -> Card(question, answer)
                1 -> Cloze(question, answer)
                else -> throw Exception("Invalid card type")
            }
        )
        println("Tarjeta añadida correctamente")
    }

    fun listCards() {
        for (c in cards) {
            println("${c.question} -> ${c.answer}")
        }
    }

    fun simulate(period: Int) {
        println("Simulacion del mazo $name: ")
        var now = LocalDateTime.now()
        for (i in 0L..period) {
            println("Fecha actual: ${now.toString().substring(0, 10)}")
            for (card in cards)
                if (now.toString().substring(0, 10) == card.nextPracticeDate.substring(0, 10)) {
                    card.show()
                    card.update(now)
                    card.details()
                }
            now = now.plusDays(1)
        }
    }
    fun writeCards(name: String) {
        var first = true
        val file = File(name)
        for (c in cards) {
            if (first) {
                file.writeText(c.toString())
                first = false
            } else
                file.appendText(c.toString())
        }
    }
    fun readCards(name: String) {
        try {
            val lines = File(name).readLines()
            for (c in lines) {
                val tokens = c.split("|")
                val tipo = tokens[0].trim()
                if (tipo == "card")
                    cards.add(Card.fromString(c))
                else if (tipo == "cloze")
                    cards.add(Cloze.fromString(c))
            }
        }catch (e:FileNotFoundException){
            println("Fichero no encontrado")
        }


    }
}
