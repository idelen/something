import React, { useState, useEffect } from "react";
import * as StompJs from "@stomp/stompjs";
import {useParams} from "react-router-dom";

export default function Chat() {
    const {roomId} = useParams();

    const [stompClient, setStompClient] = useState(null);
    const [connected, setConnected] = useState(false);
    const [greetings, setGreetings] = useState([]);
    const [name, setName] = useState("");
    const [message, setMessage] = useState(""); // 메시지 입력을 위한 상태
    const [sendFrom, setSendFrom] = useState(""); // 발신자 이름 상태

    useEffect(() => {
        const client = new StompJs.Client({
            brokerURL: 'ws://localhost:8080/ws-stomp',
            onConnect: (frame) => {
                setConnected(true);
                console.log('Connected: ' + frame);
                client.subscribe(`/sub/chat-room/${roomId}/latest-message`, (greeting) => {
                    const parsedData = JSON.parse(greeting.body); // 서버에서 전달된 내용 파싱
                    const { message, sendFrom } = parsedData; // ChatInputMessageDto 구조
                    showGreeting({ message, sendFrom }); // 발신자 이름과 메시지를 함께 보여줌
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

        setStompClient(client);

        // Cleanup WebSocket on component unmount
        return () => {
            if (client) {
                client.deactivate();
            }
        };
    }, []);

    const connect = () => {
        if (stompClient) {
            stompClient.activate();
        }
    };

    const disconnect = () => {
        if (stompClient) {
            stompClient.deactivate();
            setConnected(false);
            console.log("Disconnected");
        }
    };

    const sendMessage = () => {
        if (stompClient && connected) {
            stompClient.publish({
                destination: `/pub/chat-room/${roomId}/send`,
                body: JSON.stringify({
                    message: message,
                    sendFrom: sendFrom
                })
            });
        }
    };

    const showGreeting = (receivedMessage) => {
        // receivedMessage에는 message와 sendFrom이 포함됨
        setGreetings((prevGreetings) => [...prevGreetings, receivedMessage]);
    };

    return (
        <div>
            <div>
                <button onClick={connect} disabled={connected}>Connect</button>
                <button onClick={disconnect} disabled={!connected}>Disconnect</button>
            </div>

            {connected && (
                <div>
                    <div>
                        <input
                            type="text"
                            value={sendFrom}
                            onChange={(e) => setSendFrom(e.target.value)}
                            placeholder="Enter your name"
                        />
                        <input
                            type="text"
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            placeholder="Enter your message"
                        />
                        <button onClick={sendMessage}>Send</button>
                    </div>
                    <div>
                        <h3>Messages:</h3>
                        <table>
                            <tbody>
                            {greetings.map((greeting, index) => (
                                <tr key={index}>
                                <td><strong>{greeting.sendFrom}</strong>: {greeting.message}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>

                </div>
            )}
        </div>
    );
}