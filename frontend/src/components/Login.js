import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./Login.css";

const SAMPLE_USER = {
  username: "a_r_i_z_o_na",
  password: "passwordweb",
  role: "Kitchen Staff",
};

const Login = () => {
  const [form, setForm] = useState({ username: "", password: "" });
  const [isRegistering, setIsRegistering] = useState(false);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const endpoint = isRegistering
      ? "http://localhost:8080/api/logininfo" // registration endpoint
      : "http://localhost:8080/api/logininfo/authenticate"; // login endpoint

    try {
      const res = await fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      if (!res.ok) {
        setMessage("Authentication failed.");
        return;
      }

      const data = await res.json();
      console.log("just check", data);

      if (!isRegistering) {
        login({
          userid: data.user.userId,
          username: data.user.username,
          role: data.user.role,
          token: data.token,
        });

        setMessage(`Login successful. Welcome ${data.user.username}!`);
        navigate("/");
      } else {
        setMessage("Registration successful. Please log in.");
        setIsRegistering(false);
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("Server error.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="sample-info">
          <div className="sample-title">Sample Staff Login</div>
          <div className="sample-detail">
            <span>Username:</span> <code>{SAMPLE_USER.username}</code>
          </div>
          <div className="sample-detail">
            <span>Password:</span> <code>{SAMPLE_USER.password}</code>
          </div>
          <div className="sample-detail">
            <span>Role:</span> <code>{SAMPLE_USER.role}</code>
          </div>
        </div>

        <h2 className="login-title">{isRegistering ? "Register" : "Login"}</h2>

        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <input
              type="text"
              name="username"
              className="login-input"
              placeholder="Username"
              value={form.username}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <input
              type="password"
              name="password"
              className="login-input"
              placeholder="Password"
              value={form.password}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="login-button">
            {isRegistering ? "Create Account" : "Login"}
          </button>
        </form>

        {message && (
          <p
            className={
              message.includes("successful") ? "success-message" : "error-message"
            }
          >
            {message}
          </p>
        )}

        <p className="toggle-form">
          {isRegistering ? "Already have an account?" : "Don't have an account?"}{" "}
          <button
            type="button"
            className="toggle-button"
            onClick={() => {
              setIsRegistering(!isRegistering);
              setMessage("");
            }}
          >
            {isRegistering ? "Login" : "Register"}
          </button>
        </p>
      </div>
    </div>
  );
};

export default Login;
