import { useState } from "react";
import { loginUser } from "../service/authService";
import { useNavigate } from "react-router-dom";

function Login() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {

      const response = await loginUser(formData);

      localStorage.setItem("token", response.data.token);

      alert("Login Successful");

      navigate("/");

    } catch (error) {
      alert("Login Failed");
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>

      <form onSubmit={handleSubmit}>

        <div className="mb-3">
          <label>Email</label>
          <input
            type="email"
            className="form-control"
            name="email"
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            name="password"
            onChange={handleChange}
            required
          />
        </div>

        <button className="btn btn-primary">Login</button>

      </form>
    </div>
  );
}

export default Login;