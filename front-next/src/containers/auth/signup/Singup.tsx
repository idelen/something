import api from '@/service/api/api'
import axios from 'axios'
import { useState } from 'react'

export default function Signup() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const handleSignup = async () => {
    try {
      const requestBody = {
        id: username,
        password: password,
      }

      const response = await api.post(`/auth/signup`, requestBody)
      const token = response.data.token
      localStorage.setItem(`token`, token)
    } catch (error) {
      console.error('Signup failed : ', error)
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
  )
}
