package ru.jivan.data

import java.time.Instant

data class Chat(
    val id: Int = 0,
    val idCreator: Int = 0,
    val idInterlocutor: Int = 0,
    val InRead: Int = 0,
    val outRead: Int = 0,
    val unreadCount: Int = 0,
    val deleted: Boolean = false,
    val deletedMessages: Int = 0,
    val messages: MutableList<Message> = mutableListOf()
    ){
    data class Message(
        val id: Int = 0,
        val date: Instant = Instant.now(),
        val peerId: Int = 0,
        val fromId: Int = 0,
        val deleted: Boolean = false,
        val text: String = "",
        val updateTime: Instant? = null,
        val readState: Boolean = false
    )

}