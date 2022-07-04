package ru.jivan.service

import ru.jivan.data.Chat.Message

fun main() {
    val chatService = ChatService()

    var id: Int

    fun inputNum(s: String): Int {
        while (true) {
            println(s)
            val numInput = readln()
            val num: Int
            try {
                num = numInput.toInt()
            } catch (e: NumberFormatException) {
                println("Некорректный ввод!")
                continue
            }
            return num
        }
    }

    id = inputNum("Введите свой id")

    while (true) {
        println(
            """
        Текущий пользователь id $id
        ___________________________
        Выберите номер действия
        1. Создать новое сообщение.
        2. Редактировать сообщение.
        3. Удалить сообщение.
        4. Удалить чат.
        5. Получить список чатов.
        6. Получить список сообщений из чата.
        7. Получить информацию о количестве непрочитанных чатов.
        8. Сменить пользователя.
        9. Выйти из программы.
    """.trimIndent()
        )
        val userChoiceInput = readln()
        val userChoice: Int
        try {
            userChoice = userChoiceInput.toInt()
        } catch (e: NumberFormatException) {
            println("Некорректный ввод!")
            continue
        }
        if (userChoice !in 1..9) {
            println("Некорректный ввод!")
            continue
        }

        when (userChoice) {
            1 -> {
                println("Введите текст сообщения:")
                val messageText = readln()
                val peerId = inputNum("Введите id получателя сообщения")

                val message = Message(text = messageText, peerId = peerId, fromId = id)

                println(chatService.addMessage(id, message))
            }

            2 -> {
                val idMessage = inputNum("Введите id сообщения")
                val idChat = inputNum("Введите id чата")
                println("Введите текст сообщения")
                val text = readln()
                chatService.editMessage(idMessage, idChat, id, text)
                if (chatService.editMessage(id, idMessage, idChat, text))
                    println("Успешно!") else println("Не выполнено!")
            }

            3 -> {
                val idChat = inputNum("Введите id чата")
                val idMessage = inputNum("Введите id удаляемого сообщения")

                if (chatService.deleteMessage(id, idChat, idMessage))
                    println("Успешно!") else println("Не выполнено!")
            }

            4 -> {
                val idChat = inputNum("Введите id чата")
                if (chatService.deleteChat(id, idChat))
                    println("Успешно!") else println("Не выполнено!")
            }

            5 -> {
                chatService.getChats(id).forEach { println(it) }
            }

            6 -> {
                val idChat = inputNum("Введите id чата")
                val idStartMessage = inputNum(
                    "Введите id последнего сообщения," +
                            " начиная с которого нужно подгрузить более новые"
                )
                val numberMessage = inputNum("Введите количество подгружаемых сообщений")
                val resultList = chatService.getMessages(id, idChat, idStartMessage, numberMessage)
                resultList.forEach { println(it) }
            }

            7 -> {
                val result = chatService.getUnreadChats(id)
                println("Количество непрочитанных чатов: $result")
            }

            8 -> {
                id = inputNum("Введите id пользователя")
            }

            9 -> break
        }
    }
}