import {Navigate} from "react-router-dom";

export default function PrivateRoute({ element: Element }) {
    const token = localStorage.getItem(`token`);

    return token ? <Element /> : <Navigate to="/login" />;
}