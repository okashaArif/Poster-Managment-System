import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

export default function CreateShippingAddress() {
    const { orderId } = useParams();
    const [address, setAddress] = useState({
        fullName: '',
        addressLine1: '',
        addressLine2: '',
        city: '',
        state: '',
        zipCode: '',
        country: '',
        phoneNumber: '',
    });
    const [status, setStatus] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAddress({ ...address, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/shipping-addresses', {
                ...address,
                order: { orderId }, // Include the order ID as part of the request
            });
            setStatus(`Shipping address created successfully! ID: ${response.data.addressId}`);
            setAddress({
                fullName: '',
                addressLine1: '',
                addressLine2: '',
                city: '',
                state: '',
                zipCode: '',
                country: '',
                phoneNumber: '',
            }); // Reset form
        } catch (error) {
            console.error('Error creating shipping address:', error);
            setStatus('Failed to create shipping address. Please try again.');
        }
    };

    return (
        <div style={{ maxWidth: '600px', margin: 'auto', padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h2>Create Shipping Address</h2>
            {status && <p>{status}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Full Name</label>
                    <input
                        type="text"
                        name="fullName"
                        value={address.fullName}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Address Line 1</label>
                    <input
                        type="text"
                        name="addressLine1"
                        value={address.addressLine1}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Address Line 2</label>
                    <input
                        type="text"
                        name="addressLine2"
                        value={address.addressLine2}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>City</label>
                    <input
                        type="text"
                        name="city"
                        value={address.city}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>State</label>
                    <input
                        type="text"
                        name="state"
                        value={address.state}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Zip Code</label>
                    <input
                        type="text"
                        name="zipCode"
                        value={address.zipCode}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Country</label>
                    <input
                        type="text"
                        name="country"
                        value={address.country}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Phone Number</label>
                    <input
                        type="text"
                        name="phoneNumber"
                        value={address.phoneNumber}
                        onChange={handleChange}
                        required
                        style={{ width: '100%', padding: '10px', border: '1px solid #ddd', borderRadius: '4px' }}
                    />
                </div>
                <button
                    type="submit"
                    style={{
                        display: 'block',
                        width: '100%',
                        padding: '15px',
                        backgroundColor: '#4CAF50',
                        color: '#fff',
                        fontSize: '16px',
                        fontWeight: 'bold',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    Save Shipping Address
                </button>
            </form>
        </div>
    );
}