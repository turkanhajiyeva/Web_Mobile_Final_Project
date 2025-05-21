import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const SAMPLE_USER = {
  username: "a_r_i_z_o_na",
  password: "passwordweb",
  role: "Kitchen Staff"
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
      ? "http://localhost:8080/api/logininfo"
      : "http://localhost:8080/api/logininfo/authenticate";

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
      setMessage(
        isRegistering
          ? "Registration successful. Please log in."
          : `Login successful. Welcome ${data.username}!`
      );

      if (!isRegistering) {
        login({ username: data.username, role: data.role });
        navigate("/");
      } else {
        setIsRegistering(false);
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("Server error.");
    }
  };

  return (
    <div>
      <div className="alert alert-info" style={{ maxWidth: 400, margin: '0 auto 1rem auto' }}>
        <strong>Sample Staff Login:</strong><br />
        Username: <code>{SAMPLE_USER.username}</code><br />
        Password: <code>{SAMPLE_USER.password}</code><br />
        Role: <code>{SAMPLE_USER.role}</code>
      </div>
      <h2>{isRegistering ? "Register" : "Login"}</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />
        <br />
        <button type="submit">
          {isRegistering ? "Create Account" : "Login"}
        </button>
      </form>

      {message && <p>{message}</p>}

      <p>
        {isRegistering ? "Already have an account?" : "Don't have an account?"}{" "}
        <button
          type="button"
          onClick={() => {
            setIsRegistering(!isRegistering);
            setMessage("");
          }}
        >
          {isRegistering ? "Login" : "Register"}
        </button>
      </p>
    </div>
  );
};

export default Login;
