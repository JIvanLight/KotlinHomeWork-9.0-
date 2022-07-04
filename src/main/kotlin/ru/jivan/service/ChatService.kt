package ru.jivan.service

import ru.jivan.data.Chat
import ru.jivan.data.Chat.Message
import java.time.Instant

class ChatService {
    private val chats: MutableList<Chat> = mutableListOf()
    private var lastIdChat: Int = 0
    private var lastIdMessage: Int = 0

    private fun add(chat: Chat): Chat {
        lastIdChat++
        val chatWithId = chat.copy(id = lastIdChat)
        chats.add(chatWithId)
        return chatWithId
    }

    fun addMessage(idUser: Int, message: Message): Message {
        val chatByPeerId = chats.lastOrNull {
            !it.deleted && it.userIds.contains(message.peerId) && it.userIds.contains(idUser)
        }
        if (chatByPeerId !== null) {
            lastIdMessage++
            val messageWithId = message.copy(id = lastIdMessage)
            chatByPeerId.messages.add(messageWithId)
            return messageWithId
        } else {
            val newChat = Chat()
            newChat.userIds.add(message.fromId)
            newChat.userIds.add(message.peerId)
            lastIdMessage++
            val messageWithId = message.copy(id = lastIdMessage)
            newChat.messages.add(messageWithId)
            add(newChat)
            return messageWithId
        }
    }

    fun deleteChat(idChat: Int): Boolean {
        for ((index, chat) in chats.withIndex()) {
            if (!chat.deleted && chat.id == idChat) {
                val deletedChat = chat.copy(deleted = true)
                chats[index] = deletedChat
                chat.messages.forEachIndexed { i, message ->
                    if (!message.deleted) {
                        chat.messages[i] = message.copy(deleted = true)
                    }
                }
                return true
            }
        }
        return false
    }

    fun deleteMessage(idChat: Int, idMessage: Int): Boolean {
        val foundChat = chats.firstOrNull { !it.deleted && it.id == idChat }
        val foundMessage = foundChat?.messages?.firstOrNull { !it.deleted && it.id == idMessage }
        val indexMessage = foundChat?.messages?.indexOf(foundMessage)
        if (indexMessage != null && foundMessage != null) {
            foundChat.messages[indexMessage] = foundMessage.copy(deleted = true)
            return true
        }
        return false
    }

    fun getMessages(idUser: Int, idChat: Int, idStartMessage: Int, numberMessages: Int): List<Message> {
        val foundMessages: MutableList<Message> = mutableListOf()
        val foundChat = chats.firstOrNull { !it.deleted && it.id == idChat && it.userIds.contains(idUser) }
        if (foundChat != null) {
            val startMessage = foundChat.messages.firstOrNull { it.id == idStartMessage }
            val indexStartMessage: Int
            if (startMessage != null) {
                indexStartMessage = foundChat.messages.indexOf(startMessage)
            } else return foundMessages

            var i = 1
            for ((index) in foundChat.messages.withIndex()) {
                if (index < indexStartMessage) continue
                if (i > numberMessages) break
                if (foundChat.messages[index].peerId == idUser) foundChat.messages[index] =
                    foundChat.messages[index].copy(readState = true)
                if (!foundChat.messages[index].deleted) foundMessages.add(foundChat.messages[index])
                i++
            }
            foundMessages.sortBy { it.date }
        }
        return foundMessages
    }

    @JvmName("getChats1")
    fun getChats(idUser: Int): List<Chat> {
        val foundChats = chats.filter {
            !it.deleted && it.userIds.contains(idUser) && it.messages.any { e -> !e.deleted }
        }
        foundChats.toMutableList().sortBy { it.id }
        return foundChats
    }

    fun getUnreadChats(idUser: Int): Int {
        var i = 0
        chats.forEach {
            if (!it.deleted && it.userIds.contains(idUser) && it.messages.any { e ->
                    !e.deleted && !e.readState && idUser != e.fromId
                }) i++
        }
        return i
    }

    fun editMessage(idMessage: Int, idChat: Int, idUser: Int, text: String): Boolean {
        val foundChat = chats.lastOrNull { !it.deleted && it.id == idChat && it.userIds.contains(idUser) }
        if (foundChat != null) {
            val foundMessage = foundChat.messages.lastOrNull {
                !it.deleted && it.id == idMessage && it.fromId == idUser
            }
            if (foundMessage != null) {
                val index = foundChat.messages.indexOf(foundMessage)
                foundChat.messages[index] = foundMessage.copy(text = text, updateTime = Instant.now())
                return true
            }
        }
        return false
    }

}