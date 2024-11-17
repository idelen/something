import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";


export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const requestBody = {
                id: username,
                password: password
            }

            const response = await axios.post(`/auth/login`, requestBody);
            const token = response.data.token;
            localStorage.setItem(`token`, token);
            navigate(`/`);
        } catch (error) {
            console.error('Login failed : ', error);
        }
    }

    return (
        <div>
            <h1>Login</h1>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
        </div>
    );
}