import React, {useEffect, useRef, useState} from "react";
import {useParams} from "react-router-dom";
import SockJS from "sockjs-client";
import {Client, Stomp} from "@stomp/stompjs";
import api from "./config/api";

export default function Chat() {
    const {roomId} = useParams();

    const [messages, setMessages] = useState([]); // 채팅 메시지 리스트
    const [newMessage, setNewMessage] = useState(""); // 새로 입력되는 메시지
    const [sendFrom, setSendFrom] = useState(""); // 발신자 이름 상태
    const clientRef = useRef<Client | null> (null);

    useEffect(() => {
        fetchMessages();
    }, [roomId]);

    const fetchMessages = async () => {
        try {
            const response = await api.get(`/v1.0/chat-room/${roomId}/messages`);
            setMessages(response.data);
        } catch (error) {
            console.error('Failed to fetch messages', error);
        }
    }

    useEffect(() => {
        const token = localStorage.getItem('token');

        console.log(token);

        const connect = () => {
            if (clientRef.current && clientRef.current.connected) {
                clientRef.current.deactivate();
            }

            clientRef.current = new Client({
                connectHeaders: {
                    Authorization: `Bearer ${token}`,
                },
                webSocketFactory: () => new SockJS('http://localhost:8080/ws-stomp'),
                onConnect: () => {
                    if (clientRef.current?.connected) {
                        console.log('connect');
                    }
                },
                onDisconnect: () => {
                    console.log("disconnect");
                },
            });
            clientRef.current.activate();
        }


        // const socket = new SockJS('http://localhost:8080/ws-stomp', null, {
        //     timeout: 10000,
        //     heartbeat_delay: 5000,
        // });
        //
        // const stompClient = Stomp.over(socket);
        // // heart-beat 설정
        // stompClient.heartbeat.outgoing = 10000; // 클라이언트에서 서버로 보내는 주기 (밀리초)
        // stompClient.heartbeat.incoming = 10000; // 서버에서 클라이언트로 받는 주기 (밀리초)
        //
        // stompClient.connect({
        //     Authorization: `Bearer ${token}`,
        // }, (frame) => {
        //     console.log('Connected: ' + frame);
        //
        //     stompClient.subscribe(`/sub/chat-room/${roomId}/latest-message`, (message) => {
        //         const newMsg = JSON.parse(message.body);
        //         setMessages((prevMessages) => [...prevMessages, newMsg]); // 실시간으로 받은 메시지 추가
        //     }, {
        //         Authorization: `Bearer ${token}`
        //     });
        // }, (error) => {
        //     console.log('STOMP connection error: ' + error);
        // })

        // 컴포넌트가 언마운트될 때 WebSocket 연결 해제

        connect();

        return () => {
            console.log("연결 해제해야하는데?")
        };
    }, [roomId]);

    const sendMessage = () => {
        const token = localStorage.getItem('token');

        if (clientRef.current && clientRef.current.connected) {
            clientRef.current.publish({
                destination: `/pub/chat-room/${roomId}/send`,
                body: JSON.stringify({
                    message: newMessage,
                    sendFrom: sendFrom
                }),
                headers: {
                    Authorization: `Bearer ${token}`,
                },
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