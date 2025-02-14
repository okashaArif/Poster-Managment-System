import React, { useState } from 'react';
import axios from 'axios';

export default function ForgetPassword() {
  const [email, setEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setNewPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      await axios.post('http://localhost:8080/users/reset-password', {
        email,
        newPassword,
      });
      setSuccess('Password updated successfully!');
      setEmail('');
      setNewPassword('');
    } catch (err) {
      setError('Error updating password. Please try again.');
      console.error('Password update error:', err);
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
            Update Password
          </h1>

          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <div className="form-floating mb-3">
            <input
              type="email"
              className="form-control"
              id="floatingInput"
              placeholder="name@example.com"
              value={email}
              onChange={handleEmailChange}
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
              placeholder="New Password"
              value={newPassword}
              onChange={handlePasswordChange}
              style={{ borderRadius: "6px" }}
              required
            />
            <label htmlFor="floatingPassword">New Password</label>
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
            Update Password
          </button>
        </form>
      </div>
    </main>
  );
}