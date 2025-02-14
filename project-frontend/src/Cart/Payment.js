import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function Payment() {
    const { state } = useLocation();
    let navigate = useNavigate();
    const [userId] = useState(localStorage.getItem('userId'));
    const [paymentDetails, setPaymentDetails] = useState({
        amount: state?.total || '', // Total amount passed from ShowCart, read-only
        paymentMethod: 'CREDIT_CARD',
        status: 'Success', // Default status, not editable
        transactionId: '', // Random 10-digit transaction ID
        order: { id: userId },
    });
    const [paymentStatus, setPaymentStatus] = useState('');

    // Generate a random 10-digit transaction ID on component mount
    useEffect(() => {
        const generateTransactionId = () => {
            const randomId = Math.floor(1000000000 + Math.random() * 9000000000);
            setPaymentDetails((prevDetails) => ({
                ...prevDetails,
                transactionId: randomId.toString(),
            }));
        };
        generateTransactionId();
    }, []);

    const handlePlacePayment = async () => {
        const { amount, paymentMethod, transactionId } = paymentDetails;

        if (!amount || !paymentMethod || !transactionId) {
            alert('Missing payment details.');
            return;
        }

        try {
            await axios.post('http://localhost:8085/payments', paymentDetails);
            alert('Order placed successfully!');
            setPaymentStatus('Payment successful!');
            navigate('/home');
        } catch (error) {
            console.error('Payment error:', error);
            setPaymentStatus('Payment failed. Please try again.');
        }
    };

    return (
        <div style={{ maxWidth: '600px', margin: 'auto', padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h2>Payment</h2>
            {paymentStatus && <p>{paymentStatus}</p>}

            {/* Payment Details Section */}
            <section>
                <h3>Payment Details</h3>
                <div style={{ marginBottom: '10px' }}>
                    <label>Amount</label>
                    <input
                        type="number"
                        name="amount"
                        value={paymentDetails.amount}
                        readOnly
                        style={{
                            width: '100%',
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '4px',
                            backgroundColor: '#f9f9f9',
                        }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Payment Method</label>
                    <select
                        name="paymentMethod"
                        value={paymentDetails.paymentMethod}
                        onChange={(e) => setPaymentDetails({ ...paymentDetails, paymentMethod: e.target.value })}
                        style={{
                            width: '100%',
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '4px',
                        }}
                    >
                        <option value="CREDIT_CARD">Credit Card</option>
                        <option value="PAYPAL">PayPal</option>
                    </select>
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Status</label>
                    <input
                        type="text"
                        name="status"
                        value={paymentDetails.status}
                        readOnly
                        style={{
                            width: '100%',
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '4px',
                            backgroundColor: '#f9f9f9',
                        }}
                    />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Transaction ID</label>
                    <input
                        type="text"
                        name="transactionId"
                        value={paymentDetails.transactionId}
                        readOnly
                        style={{
                            width: '100%',
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '4px',
                            backgroundColor: '#f9f9f9',
                        }}
                    />
                </div>
            </section>

            {/* Submit Button */}
            <button
                onClick={handlePlacePayment}
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
                    marginTop: '20px',
                }}
            >
                Submit Payment
            </button>
        </div>
    );
}
