import axios, { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from "axios";

const api = axios.create({
    baseURL: `http://localhost:8080`
})

api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token = localStorage.getItem('token');

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response: AxiosResponse) => {
        return response;
    },
    (error: AxiosError) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token')
            window.location.href = '/auth/login'
        }
        return Promise.reject(error);
    }
)

export default api;