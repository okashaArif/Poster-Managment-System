import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

export default function Signup() {
  let navigate=useNavigate();
  const [user, setUser] = useState({
    username: '',
    email: '',
    password: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      await axios.post('http://localhost:8080/users/register', user);
      setSuccess('User registered successfully!');
      // Optionally reset the form
      setUser({ username: '', email: '', password: '' });
      navigate('/');
    } catch (err) {
      setError('Error registering user. Please try again.');
      console.error('Registration error:', err);
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
            Sign Up
          </h1>

          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="floatingInputName"
              placeholder="Enter Your Name"
              name="username"
              value={user.username}
              onChange={handleChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingInputName">Name</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="floatingInputEmail"
              placeholder="Enter Your Email"
              name="email"
              value={user.email}
              onChange={handleChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingInputEmail">Email address</label>
          </div>

          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="Enter Your Password"
              name="password"
              value={user.password}
              onChange={handleChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingPassword">Password</label>
          </div>
          <Link to='/'  style={{ textDecoration: "none", color: "#007bff", fontSize: "14px" }}>
              Already Have an account?
            </Link>
          <button
            className="btn btn-primary w-100 py-2"
            type="submit"
            style={{
              borderRadius: "6px",
              backgroundColor: "#007bff",
              borderColor: "#007bff",
            }}
          >
            Sign Up
          </button>
        </form>
      </div>
    </main>
  );
}