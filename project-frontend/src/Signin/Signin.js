import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

export default function Signin() {
  let navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
  
    try {
      const response = await axios.post('http://localhost:8080/users/login', credentials);
      console.log(response);
      const userId = response.data.userId;
      const username = response.data.username; // Log the response
      if (userId && username) {
        localStorage.setItem('userId', userId);
        localStorage.setItem('username', username);
        setSuccess('Login successful!');
        navigate('/home'); // Redirect to main page after successful login
      } else {
        setError('Invalid response from the server.');
      }
    } catch (err) {
      setError('Error logging in. Please check your credentials.');
      console.error('Login error:', err);
    }
  };
  
  

  return (
    <main className="container d-flex justify-content-center align-items-center vh-100">
      <div
        className="form-signin w-100 p-4"
        style={{
          maxWidth: "400px",
          borderRadius: "8px",
          boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
        }}
      >
        <form onSubmit={handleSubmit}>
          <h1 className="h3 mb-4 fw-bold text-center" style={{ color: "#333" }}>
            Sign In
          </h1>

          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="floatingInput"
              placeholder="name@example.com"
              name="email"
              value={credentials.email}
              onChange={handleChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingInput">Email address</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="Password"
              name="password"
              value={credentials.password}
              onChange={handleChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingPassword">Password</label>
          </div>

          <div className="d-flex justify-content-between align-items-center mb-3">
            <Link to='/updatepassword'  style={{ textDecoration: "none", color: "#007bff", fontSize: "14px" }}>
              Forget Password?
            </Link>
          </div>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <Link to='/signup'  style={{ textDecoration: "none", color: "red", fontSize: "14px" }}>
              Don't have an account?
            </Link>
          </div>

          <button
            className="btn btn-primary w-100 py-2"
            type="submit"
            style={{
              borderRadius: "6px",
              backgroundColor: "#007bff",
              borderColor: "#007bff",
            }}
          >
            Sign In
          </button>
          <Link
            className="btn btn-primary w-30 py-2 my-2"
            type="button"
            onClick={() => {
              console.log('Navigate to Admin login');
            }}
            style={{
              borderRadius: "6px",
              backgroundColor: "FA4032",
              borderColor: "FA4032",
            }}
            to="/Admin"
          >
            Login As Admin
          </Link>
        </form>
      </div>
    </main>
  );
}