import React from "react";
import { BrowserRouter as Router, Routes, Route, Link, useNavigate, Navigate } from "react-router-dom";
import Home from "./pages/Home";
import Explore from "./pages/Explore";
import About from "./pages/About";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Profile from "./pages/Profile";


function Navbar() {

  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">

      <Link className="navbar-brand fw-bold" to="/">
        <span style={{color:"#00d4ff"}}>UV</span> UrbanVogue
      </Link>

      <div className="collapse navbar-collapse">

        <ul className="navbar-nav ms-auto">

          <li className="nav-item">
            <Link className="nav-link" to="/">Home</Link>
          </li>

          <li className="nav-item">
            <Link className="nav-link" to="/explore">Explore</Link>
          </li>

          <li className="nav-item">
            <Link className="nav-link" to="/about">About Us</Link>
          </li>

          {!token && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/login">Login</Link>
              </li>

              <li className="nav-item">
                <Link className="nav-link" to="/register">Register</Link>
              </li>
            </>
          )}

          {token && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/profile">MyProfile</Link>
              </li>

              <li className="nav-item">
                <button className="btn btn-danger ms-2" onClick={handleLogout}>
                  Logout
                </button>
              </li>
            </>
          )}

        </ul>
      </div>
    </nav>
  );
}


/* ----------- PROTECTED ROUTE ----------- */

function ProtectedRoute({ children }) {

  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" />;
  }

  return children;
}


function App() {

  return (
    <Router>

      <Navbar/>

      <div className="container mt-4">

        <Routes>

          <Route path="/" element={<Home/>} />

          <Route path="/explore" element={<Explore/>} />

          <Route path="/about" element={<About/>} />

          <Route path="/login" element={<Login/>} />

          <Route path="/register" element={<Register/>} />

          {/* Protected Profile Route */}
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Profile/>
              </ProtectedRoute>
            }
          />

        </Routes>

      </div>

    </Router>
  );
}

export default App;