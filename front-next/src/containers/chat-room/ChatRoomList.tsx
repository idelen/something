'use client'

import { ChatProps } from '@/app/types'
import ChatsPane from '@/components/ChatsPane'
import MessagesPane from '@/components/MessagesPane'
import api from '@/service/api/api'
import { Sheet } from '@mui/joy'
import { AxiosResponse } from 'axios'
import { useEffect, useState } from 'react'

export default function ChatRoomList() {
  const [chatRooms, setChatRooms] = useState<ChatProps[]>([])
  const [selectedChat, setSelectedChat] = useState<ChatProps>()

  const fetchChatRooms = async () => {
    try {
      const response: AxiosResponse = await api.get('/v1.0/chat-rooms')
      setChatRooms(response.data)
      setSelectedChat(chatRooms[0])
    } catch (error) {
      console.error('Failed to fetch chat rooms', error)
    }
  }

  useEffect(() => {
    fetchChatRooms()
  }, [])

  return (
    <Sheet
      sx={{
        flex: 1,
        width: '100%',
        mx: 'auto',
        pt: { xs: 'var(--Header-height)', md: 0 },
        display: 'grid',
        gridTemplateColumns: {
          xs: '1fr',
          sm: 'minmax(min-content, min(30%, 400px)) 1fr',
        },
      }}
    >
      <Sheet
        sx={{
          position: { xs: 'fixed', sm: 'sticky' },
          transform: {
            xs: 'translateX(calc(100% * (var(--MessagesPane-slideIn, 0) - 1)))',
            sm: 'none',
          },
          transition: 'transform 0.4s, width 0.4s',
          zIndex: 100,
          width: '100%',
          top: 52,
        }}
      >
        <ChatsPane
          chats={chatRooms}
          selectedChatId={selectedChat?.id}
          setSelectedChat={setSelectedChat}
        />
      </Sheet>
      {selectedChat && <MessagesPane chat={selectedChat} />}
    </Sheet>
  )
}
