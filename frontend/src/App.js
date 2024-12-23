import './App.css';
import {Route, Routes} from "react-router-dom";
import BoardList from "./routes/BoardList";
import Home from "./routes/Home";
import ChatRoomList from "./routes/ChatRoomList";
import Chat from "./routes/Chat";
import Login from "./routes/Login";
import PrivateRoute from "./routes/config/PrivateRoute";
import Signup from "./routes/Signup";
import {useEffect, useState} from "react";
import Header from "./layout/Haeder";
import Footer from "./layout/Footer";
import axios from "axios";

function App() {
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        axios.get(`/auth/me`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(`token`)}`
            }
        })
            .then(response => {
                console.log(response);
                setCurrentUser(response.data)
            })
            .catch(error => {
                setCurrentUser(null);
                console.log(error);
            });
    }, []);

    return (
        <>
            <Header currentUser={currentUser}/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<Signup/>}/>
                <Route path="/board" element={<BoardList/>}/>
                <Route path="/chat-room" element={<PrivateRoute element={ChatRoomList}/>}/>
                <Route path="/chat-room/:roomId" element={<Chat/>}/>
            </Routes>
            <Footer/>
        </>
    );
}

export default App;
