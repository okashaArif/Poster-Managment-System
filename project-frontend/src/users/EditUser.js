import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

export default function EditUser() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [user, setUser] = useState({
        username: '',
        email: '',
        password: '', // Optional, if you want to allow password change
    });

    const { username, email } = user;

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const loadUser = async () => {
        try {
            const result = await axios.get(`http://localhost:8080/users/${id}`);
            setUser(result.data);
        } catch (error) {
            console.error('Error loading user:', error);
        }
    };

    useEffect(() => {
        loadUser();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/users/${id}`, user);
            navigate("/");
        } catch (error) {
            console.error('Error updating user:', error);
        }
    };

    return (
        <div className='container'>
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className='text-center m-4'>Edit User</h2>
                    <form onSubmit={onSubmit}>
                        <div className="mb-3">
                            <label htmlFor="username" className='form-label'>Username</label>
                            <input 
                                type="text" 
                                className='form-control' 
                                placeholder='Enter your username' 
                                name='username' 
                                value={username} 
                                onChange={onInputChange} 
                                required 
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="email" className='form-label'>Email</label>
                            <input 
                                type="email" 
                                className='form-control' 
                                placeholder='Enter your email' 
                                name='email' 
                                value={email} 
                                onChange={onInputChange} 
                                required 
                            />
                        </div>
                        <button type='submit' className='btn btn-outline-primary'>Submit</button>
                        <Link to="/Admin" className='btn btn-outline-danger mx-2'>Cancel</Link>
                    </form>
                </div>
            </div>
        </div>
    );
}