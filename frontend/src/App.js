import './App.css';
import {Route, Routes} from "react-router-dom";
import BoardList from "./routes/BoardList";
import Home from "./routes/Home";
import Chat from "./routes/Chat";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home/>}/>
      <Route path="/board" element={<BoardList/>}/>
        <Route path="/chat" element={<Chat/>}/>
    </Routes>
  );
}

export default App;
