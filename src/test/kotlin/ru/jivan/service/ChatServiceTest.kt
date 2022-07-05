package ru.jivan.service

import org.junit.Test

import org.junit.Assert.*
import ru.jivan.data.Chat
import ru.jivan.data.Chat.Message

class ChatServiceTest {

    @Test
    fun add_test() {

        val chatService = ChatService()

        val chat = Chat()

        val actual = chatService.add(chat)
        val expected = chat.copy(id = 1)

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test1() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)

        val actual = chatService.addMessage(1, message)
        val expected = message.copy(id = 1)

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test2() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)

        chatService.addMessage(1, message)

        val actual = chatService.chats[0]
        val expected = Chat(id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(message.copy(id = 1)))

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test3() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)
        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2))

        chatService.addMessage(1, message)

        val actual = chatService.chats[0]
        val expected = Chat(id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(message.copy(id = 1)))

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test4() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)
        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = true)

        chatService.addMessage(1, message)

        val actual = chatService.chats[0]
        val expected = Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = true)

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test5() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)
        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 3))

        chatService.addMessage(1, message)

        val actual = chatService.chats[0]
        val expected = Chat(id = 1, userIds = mutableSetOf(1, 3))

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test6() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)
        chatService.chats += Chat(id = 1, userIds = mutableSetOf(2, 6))

        chatService.addMessage(1, message)

        val actual = chatService.chats[0]
        val expected = Chat(id = 1, userIds = mutableSetOf(2, 6))

        assertEquals(expected, actual)
    }

    @Test
    fun addMessage_test7() {
        val chatService = ChatService()

        val message = Message(fromId = 1, peerId = 2)
        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2))

        val actual = chatService.addMessage(1, message)
        val expected = message.copy(id = 1)

        assertEquals(expected, actual)
    }

    @Test
    fun deleteChat_test1() {
        val chatService = ChatService()

        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2))

        val actual = chatService.deleteChat(1, 1)

        assertEquals(true, actual)
    }

    @Test
    fun deleteChat_test2() {
        val chatService = ChatService()

        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2))

        val actual = chatService.deleteChat(1, 2)

        assertEquals(false, actual)
    }

    @Test
    fun deleteChat_test3() {
        val chatService = ChatService()

        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = true)

        val actual = chatService.deleteChat(1, 1)

        assertEquals(false, actual)
    }

    @Test
    fun deleteChat_test4() {
        val chatService = ChatService()

        chatService.chats += Chat(id = 1, userIds = mutableSetOf(1, 2))

        val actual = chatService.deleteChat(3, 1)

        assertEquals(false, actual)
    }

    @Test
    fun deleteChat_test5() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                Message(deleted = false),
                Message(deleted = false),
                Message(deleted = false)
            )
        )

        chatService.deleteChat(1, 1)

        val actual = chatService.chats[0]

        val expected = chatService.chats[0].copy(
            id = 1, userIds = mutableSetOf(1, 2),
            deleted = true, messages = mutableListOf(
                chatService.chats[0].messages[0].copy(deleted = true),
                chatService.chats[0].messages[0].copy(deleted = true),
                chatService.chats[0].messages[0].copy(deleted = true),
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test1() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                    Message(id = 1),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 1, idChat = 1, idMessage = 1)

        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test2() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), deleted = true, messages = mutableListOf(
                    Message(id = 1),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 1, idChat = 1, idMessage = 1)

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test3() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                    Message(id = 1),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 1, idChat = 2, idMessage = 1)

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test4() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                    Message(id = 1),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 1, idChat = 1, idMessage = 4)

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test5() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                    Message(id = 1),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 3, idChat = 1, idMessage = 1)

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test6() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), messages = mutableListOf(
                    Message(id = 1, deleted = true),
                    Message(id = 2),
                    Message(id = 3)
                )
            )

        val actual = chatService.deleteMessage(idUser = 1, idChat = 1, idMessage = 1)

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun deleteMessage_test7() {
        val chatService = ChatService()

        chatService.chats +=
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = mutableListOf(
                    Message(id = 1, deleted = false),
                    Message(id = 2, deleted = true),
                    Message(id = 3, deleted = true)
                )
            )

        chatService.deleteMessage(idUser = 1, idChat = 1, idMessage = 1)

        val actual = chatService.chats[0].deleted

        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test1() {
        val chatService = ChatService()

        val chatsT = mutableListOf(
            Message(id = 1),
            Message(id = 2),
            Message(id = 3),
            Message(id = 4)
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        val actual = chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val expected = chatsT.filter { it.id < 4 }

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test2() {
        val chatService = ChatService()

        val chatsT = mutableListOf(
            Message(id = 1),
            Message(id = 2),
            Message(id = 3),
            Message(id = 4)
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = true, messages = chatsT)

        val actual = chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val expected = listOf<Message>()

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test3() {
        val chatService = ChatService()

        val chatsT = mutableListOf(
            Message(id = 1),
            Message(id = 2),
            Message(id = 3),
            Message(id = 4)
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        val actual = chatService.getMessages(idUser = 3, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val expected = listOf<Message>()

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test4() {
        val chatService = ChatService()

        val chatsT = mutableListOf(
            Message(id = 1),
            Message(id = 2),
            Message(id = 3, deleted = true),
            //Message(id = 4)
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        val actual = chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val expected = chatsT.filter { it.id < 3 }

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test5() {
        val chatService = ChatService()

        val message1 = Message(id = 1)
        val message2 = Message(id = 2)
        val message3 = Message(id = 3)
        val message4 = Message(id = 4)

        val chatsT = mutableListOf(
            message1,
            message2,
            message3,
            message4
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        val actual = chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 3, numberMessages = 2)

        val expected = listOf(
            message3,
            message4
        )

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test6() {
        val chatService = ChatService()

        val message1 = Message(id = 1)
        val message2 = Message(id = 2, peerId = 1)
        val message3 = Message(id = 3)

        val chatsT = mutableListOf(
            message1,
            message2,
            message3
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        val actual = chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val expected = listOf(
            message1,
            message2.copy(readState = true),
            message3
        )

        assertEquals(expected, actual)
    }

    @Test
    fun getMessages_test7() {
        val chatService = ChatService()

        val message1 = Message(id = 1)
        val message2 = Message(id = 2, peerId = 1)
        val message3 = Message(id = 3)

        val chatsT = mutableListOf(
            message1,
            message2,
            message3
        )

        chatService.chats +=
            Chat(id = 1, userIds = mutableSetOf(1, 2), deleted = false, messages = chatsT)

        chatService.getMessages(idUser = 1, idChat = 1, idStartMessage = 1, numberMessages = 3)

        val actual = chatService.chats[0].messages[1]

        val expected = message2.copy(readState = true)

        assertEquals(expected, actual)
    }

    @Test
    fun getChats_test1() {
        val chatService = ChatService()

        val message1 = Message(id = 1, deleted = false)
        val message2 = Message(id = 2, deleted = false)
        val message3 = Message(id = 3, deleted = false)
        val message4 = Message(id = 4, deleted = true)
        val message5 = Message(id = 5, deleted = false)


        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )
        chatService.chats += Chat(
            id = 2, userIds = mutableSetOf(1, 3), deleted = true,
            messages = mutableListOf(message2)
        )
        chatService.chats += Chat(
            id = 3, userIds = mutableSetOf(2, 4), deleted = false,
            messages = mutableListOf(message3)
        )
        chatService.chats += Chat(
            id = 4, userIds = mutableSetOf(1, 5), deleted = false,
            messages = mutableListOf(message4)
        )
        chatService.chats += Chat(
            id = 5, userIds = mutableSetOf(1, 6), deleted = false,
            messages = mutableListOf(message5)
        )

        val actual = chatService.getChats(idUser = 1)

        val expected = listOf(
            Chat(
                id = 1, userIds = mutableSetOf(1, 2), deleted = false,
                messages = mutableListOf(message1.copy())
            ),
            Chat(
                id = 5, userIds = mutableSetOf(1, 6), deleted = false,
                messages = mutableListOf(message5.copy())
            )
        )

        assertEquals(expected, actual)

    }

    @Test
    fun getChats_test2() {
        val chatService = ChatService()

        val message1 = Message(id = 1, deleted = false)
        val message2 = Message(id = 2, deleted = false)
        val message3 = Message(id = 3, deleted = false)
        val message4 = Message(id = 4, deleted = true)
        val message5 = Message(id = 5, deleted = false)


        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )
        chatService.chats += Chat(
            id = 2, userIds = mutableSetOf(1, 3), deleted = true,
            messages = mutableListOf(message2)
        )
        chatService.chats += Chat(
            id = 3, userIds = mutableSetOf(2, 4), deleted = false,
            messages = mutableListOf(message3)
        )
        chatService.chats += Chat(
            id = 4, userIds = mutableSetOf(1, 5), deleted = false,
            messages = mutableListOf(message4)
        )
        chatService.chats += Chat(
            id = 5, userIds = mutableSetOf(1, 6), deleted = false,
            messages = mutableListOf(message5)
        )

        val actual = chatService.getChats(idUser = 7)

        val expected = listOf<Chat>()

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test1() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = false, readState = false)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 1)

        val expected = 1

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test2() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = true,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = false, readState = false)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 1)

        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test3() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = false, readState = false)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 3)

        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test4() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = true, readState = false)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 1)

        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test5() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = false, readState = true)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 1)

        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun getUnreadChatsCount_test6() {
        val chatService = ChatService()

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(
                Message(id = 1, fromId = 2, peerId = 1, deleted = false, readState = false)
            )
        )

        val actual = chatService.getUnreadChatsCount(idUser = 2)

        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test1() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 1, idChat = 1, text = "BBB")

        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test2() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        chatService.editMessage(idUser = 1, idMessage = 1, idChat = 1, text = "BBB")

        val expextedTime = chatService.chats[0].messages[0].updateTime

        val actual = chatService.chats[0].messages[0]

        val expected = message1.copy(text = "BBB", updateTime = expextedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test3() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = true, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 1, idChat = 1, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test5() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 1, idChat = 2, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test6() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 3, idMessage = 1, idChat = 1, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test7() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = true,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 1, idChat = 1, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test8() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 1, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 2, idChat = 1, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun editMessage_test9() {
        val chatService = ChatService()

        val message1 = Message(id = 1, fromId = 3, peerId = 2, deleted = false, readState = false, text = "AAA")

        chatService.chats += Chat(
            id = 1, userIds = mutableSetOf(1, 2), deleted = false,
            messages = mutableListOf(message1)
        )

        val actual = chatService.editMessage(idUser = 1, idMessage = 1, idChat = 1, text = "BBB")

        val expected = false

        assertEquals(expected, actual)
    }
}