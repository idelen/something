import * as StompJs from "@stomp/stompjs";

let roomId = 1;
export default function Chat() {
    const clientData = new StompJs.Client({
        brokerURL: "ws://localhost:8080/ws-stomp",
        connectHeaders: {
            login: "",
            passcode: "password",
        },
        debug: function (str) {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

// 구독
    clientData.onConnect = function () {
        clientData.subscribe("/sub/chat-room/" + roomId + "/latest-message", callback);
    };

    const callback = function (message) {
        if (message.body) {
            let msg = JSON.parse(message.body);
            setChatList((chats) => [...chats, msg]);
        }
    }

// 전송
    const sendChat = () => {
        client.publish({
            destination: "/pub/chat-room/" + roomId + "/send",
            body: JSON.stringify({
                type: "",
            })
        });

        setChat("")
    }

    return (

    )
}