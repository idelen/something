import React from "react";
import {Link} from "react-router-dom";

export default function Header() {
    return (
        <header>
            <Link to={`/`}>홈</Link>
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <Link to={`/board`}>게시판</Link>
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <Link to={`/chat-room`}>채팅</Link>
            <hr/>
        </header>
    )
}