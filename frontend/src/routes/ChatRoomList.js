import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

export default function ChatRoomList() {
    const [chatRooms, setChatRooms] = useState([]); // 채팅방 리스트 상태
    const [isPopupOpen, setPopupOpen] = useState(false); // 팝업 상태
    const [newRoomName, setNewRoomName] = useState(""); // 새 채팅방 이름
    const [creatorName, setCreatorName] = useState(""); // 새 채팅방 생성자 이름

    // 채팅방 목록 조회
    useEffect(() => {
        fetchChatRooms();
    }, []);

    const fetchChatRooms = async () => {
        try {
            const response = await axios.get("/v1.0/chat-rooms"); // API 호출
            setChatRooms(response.data); // 응답 데이터를 상태로 설정
        } catch (error) {
            console.error("Failed to fetch chat rooms", error);
        }
    };

    // 팝업 열기
    const openPopup = () => {
        setPopupOpen(true);
    };

    // 팝업 닫기
    const closePopup = () => {
        setPopupOpen(false);
        setNewRoomName(""); // 입력값 초기화
        setCreatorName("");
    };

    // 채팅방 생성
    const createChatRoom = async () => {
        try {
            const requestBody = {
                name: newRoomName,
                creatorName: creatorName,
            };
            await axios.post("/v1.0/chat-room", requestBody); // 채팅방 생성 API 호출
            fetchChatRooms(); // 채팅방 목록 갱신
            closePopup(); // 팝업 닫기
        } catch (error) {
            console.error("Failed to create chat room", error);
        }
    };

    return (
        <div>
            <h2>Chat Room List</h2>
            <ul>
                {chatRooms.map((room) => (
                    <li key={room.id}>
                        <Link to={`/chat-room/${room.id}`}>
                            <strong>{room.name}</strong> (created by: {room.creatorName})
                        </Link>
                    </li>
                ))}
            </ul>

            <button onClick={openPopup}>Create New Chat Room</button>

            {/* 채팅방 생성 팝업 */}
            {isPopupOpen && (
                <div className="popup">
                    <div className="popup-content">
                        <h3>Create a new chat room</h3>
                        <input
                            type="text"
                            placeholder="Room Name"
                            value={newRoomName}
                            onChange={(e) => setNewRoomName(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Creator Name"
                            value={creatorName}
                            onChange={(e) => setCreatorName(e.target.value)}
                        />
                        <button onClick={createChatRoom}>Create</button>
                        <button onClick={closePopup}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
}