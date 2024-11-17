import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function Signup() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSignup = async () => {
        try {
            const requestBody = {
                id: username,
                password: password
            }

            const response = await axios.post(`/auth/signup`, requestBody);
            const token = response.data.token;
            localStorage.setItem(`token`, token);
            navigate(`/`);
        } catch (error) {
            console.error('Signup failed : ', error);
        }
    }

    return (
        <div>
            <h1>Signup</h1>
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
            <button onClick={handleSignup}>Signup</button>
        </div>
    );
}