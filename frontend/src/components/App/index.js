import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./style.css";
import Room from "../Room";
import SignUp from "../Signup";
import Login from "../Login";
import NotFound from "../NotFound";
import { USER_INITIAL_VALUE } from "../../constants";
import { UserContext } from "../../context/UserContext";
import { StyledEngineProvider } from '@mui/material/styles';


const routes = [
  { path: "/signup", component: SignUp },
  { path: "/login", component: Login },
  { path: "/room", component: Room },
  { path: "/", component: Login },
];

function App() {
  const username = localStorage.getItem("user");
  const user = {
    username: username || USER_INITIAL_VALUE.username,
  };
  const [userDetails, setUserDetails] = useState(user);
  return (
    <StyledEngineProvider injectFirst>
      <UserContext.Provider value={{ userDetails, setUserDetails }}>
        {/* <StompSessionProvider url={process.env.REACT_APP_WEBSOCKET_URL}> */}
          <div className="app">
            <Router>
              <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/room" element={<Room />} />
                <Route path="/" element={<Login />} />
                <Route component={NotFound} />
              </Routes>
            </Router>
          </div>
        {/* </StompSessionProvider> */}
      </UserContext.Provider>
    </StyledEngineProvider>
  );
}

export default App;
