import React, {useEffect} from "react";
import {Link} from "react-router-dom";

export default function Header({ currentUser }) {
    useEffect(() => {

    }, )

    return (
        <header>
            <Link to={`/`}>홈</Link>
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <Link to={`/board`}>게시판</Link>
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <Link to={`/chat-room`}>채팅</Link>
            &nbsp;&nbsp;|&nbsp;&nbsp;

            {currentUser ?
                <>
                    <button>로그아웃</button>
                </>
                :
                <>
                    <Link to={`/login`}>로그인</Link> | <Link to={`/signup`}>회원가입</Link>
                </>
            }
            <hr/>
        </header>
    )
}