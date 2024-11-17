import React, { useState, useEffect } from "react";
import * as StompJs from "@stomp/stompjs";
import {useParams} from "react-router-dom";
import axios from "axios";
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";

export default function Chat() {
    const {roomId} = useParams();

    const [messages, setMessages] = useState([]); // 채팅 메시지 리스트
    const [newMessage, setNewMessage] = useState(""); // 새로 입력되는 메시지
    const [sendFrom, setSendFrom] = useState(""); // 발신자 이름 상태
    const [client, setClient] = useState(null); // WebSocket 클라이언트

    useEffect(() => {
        fetchMessages();
    }, [roomId]);

    const fetchMessages = async () => {
        try {
            const response = await axios.get(`/v1.0/chat-room/${roomId}/messages`);
            setMessages(response.data);
        } catch (error) {
            console.error('Failed to fetch messages', error);
        }
    }

    useEffect(() => {
        const token = localStorage.getItem('token');

        const socket = new SockJS('/ws-stomp');
        const stompClient = Stomp.over(socket);

        console.log(roomId);
        const client = new stompClient.Client({
            brokerURL: 'ws://localhost:8080/ws-stomp',
            connectHeaders: {
                Authorization: `Bearer ${token}`,
            },
            onConnect: (frame) => {
                console.log('Connected: ' + frame);
                client.subscribe(`/sub/chat-room/${roomId}/latest-message`, (message) => {
                    const newMsg = JSON.parse(message.body);
                    setMessages((prevMessages) => [...prevMessages, newMsg]); // 실시간으로 받은 메시지 추가
                });
            },
            onWebSocketError: (error) => {
                console.error('Error with websocket', error);
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            },
        });
        client.activate(); // WebSocket 연결 시작
        setClient(client);

        // 컴포넌트가 언마운트될 때 WebSocket 연결 해제
        return () => {
            if (client) {
                client.deactivate();
            }
        };
    }, [roomId]);

    const sendMessage = () => {
        if (client && client.connected) {
            client.publish({
                destination: `/pub/chat-room/${roomId}/send`,
                body: JSON.stringify({
                    message: newMessage,
                    sendFrom: sendFrom
                })
            });
            setNewMessage(""); // 메시지 전송 후 입력 필드 초기화
        }
    };

    return (
        <div>
            <h2>Chat Messages for Room {roomId}</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>
                        <strong>{message.sendFrom}</strong>: {message.message}
                    </li>
                ))}
            </ul>

            <div>
                <input
                    type="text"
                    value={sendFrom}
                    onChange={(e) => setSendFrom(e.target.value)}
                    placeholder="Type your name..."
                />
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    placeholder="Type your message..."
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
}