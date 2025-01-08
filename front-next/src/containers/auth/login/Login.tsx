'use client'

import api from '@/service/api/api'
import { useState } from 'react'

export default function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const handleLogin = async () => {
    try {
      const requestBody = {
        id: username,
        password: password,
      }

      const response = await api.post(`/auth/login`, requestBody)
      const token = response.data.token
      localStorage.setItem(`token`, token)
    } catch (error) {
      console.error('Login failed : ', error)
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
  )
}
