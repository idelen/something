import './App.css';
import {Route, Routes} from "react-router-dom";
import BoardList from "./routes/BoardList";
import Home from "./routes/Home";
import ChatRoomList from "./routes/ChatRoomList";
import Chat from "./routes/Chat";
import Login from "./routes/Login";
import PrivateRoute from "./routes/PrivateRoute";
import Signup from "./routes/Signup";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/board" element={<BoardList/>}/>
            <Route path="/chat-room" element={<PrivateRoute element={ChatRoomList} />}/>
            <Route path="/chat-room/:roomId" element={<Chat/>}/>
        </Routes>
    );
}

export default App;
