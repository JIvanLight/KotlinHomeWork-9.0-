package ru.jivan.data

import java.time.Instant

data class Chat(
    val id: Int = 0,
    val userIds: MutableSet<Int> = mutableSetOf(),
    val deleted: Boolean = false,
    val messages: MutableList<Message> = mutableListOf()
) {
    data class Message(
        val id: Int = 0,
        val date: Instant = Instant.now(),
        val fromId: Int = 0,
        val peerId: Int = 0,
        val deleted: Boolean = false,
        val text: String = "",
        val updateTime: Instant? = null,
        val readState: Boolean = false
    )

}