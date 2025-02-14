import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

export default function ViewUser() {
    const [user, setUser] = useState({
        username: '',
        email: '',
        password: '' // Optional, if you want to display it (but do not display passwords for security reasons)
    });
    const { id } = useParams();

    const loadUser = async () => {
        try {
            const result = await axios.get(`http://localhost:8080/users/${id}`); // Correct endpoint
            setUser(result.data);
        } catch (error) {
            console.error('Error loading user:', error);
        }
    };

    useEffect(() => {
        loadUser();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <div className='container'>
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className='text-center m-4'>User Details</h2>

                    <div className="card">
                        <div className="card-header">
                            Details of user id:
                            <ul className='list-group list-group-flush'>
                                <li className="list-group-item"><b>Name: </b> {user.username}</li>
                                <li className="list-group-item"><b>Email: </b> {user.email}</li>
                            </ul>
                        </div>
                    </div>
                    <Link className="btn btn-primary my-2" to="/allusers">Home</Link>
                </div>
            </div>
        </div>
    );
}