package ru.jivan.service

import ru.jivan.data.Chat
import ru.jivan.data.Chat.Message
import ru.jivan.exceptions.MessageNotFoundException

class ChatService {
    val chats: MutableList<Chat> = mutableListOf()
    var lastIdChat: Int = 0
    var lastIdMessage: Int = 0

    enum class TypeSort {ASCENDING, DESCENDING}

    fun add(chat: Chat): Chat{
        lastIdChat++
        val chatWithId = chat.copy (id = lastIdChat)
        chats.add(chatWithId)
        return chatWithId
    }

    fun addMessage(message: Message): Message{
        val chatByPeerId = chats.lastOrNull { !it.deleted && it.idInterlocutor == message.peerId }
        if (chatByPeerId !== null){
            lastIdMessage++
            val messageWithId = message.copy(id = lastIdMessage)
            chatByPeerId.messages.add(messageWithId)
            return messageWithId
        } else {
            val newChat = Chat(
                idCreator = message.fromId,
                idInterlocutor = message.peerId,
            )
            lastIdMessage++
            val messageWithId = message.copy(id = lastIdMessage)
            newChat.messages.add(messageWithId)
            add(newChat)
            return messageWithId
        }
    }

    fun delete (id: Int): Boolean{
        for ((index, chat) in chats.withIndex()){
            if (!chat.deleted && chat.id == id){
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

    fun deleteMessage(idChat: Int, idMessage: Int){
        
    }


}