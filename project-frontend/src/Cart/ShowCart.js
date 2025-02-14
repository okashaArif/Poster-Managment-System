import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function ShowCart() {
    const [userId] = useState(localStorage.getItem('userId'));
    const [cart, setCart] = useState({ items: [] });
    const [total, setTotal] = useState(0);
    const [shippingAddress, setShippingAddress] = useState('');
    const [showShippingForm, setShowShippingForm] = useState(false);
    const navigate = useNavigate();

    const loadCart = async () => {
        try {
            const response = await axios.get(`http://localhost:8083/cart/${userId}`);
            console.log(response.data);
            setCart(response.data || { items: [] });
            calculateTotal(response.data?.items || []);
        } catch (error) {
            console.error('Error loading cart:', error);
        }
    };

    const calculateTotal = (items) => {
        const totalAmount = items.reduce((acc, item) => acc + (item.price * item.quantity), 0);
        setTotal(totalAmount);
    };

    const deleteCartItem = async (cartItemId) => {
        if (!cartItemId) {
            alert('Invalid item ID. Unable to delete.');
            return;
        }
    
        try {
            await axios.delete(`http://localhost:8083/cart-items/${cartItemId}`);
            alert('Item removed from cart.');
            loadCart(); // Reload the cart after deletion
        } catch (error) {
            console.error('Error deleting item:', error);
            alert('Failed to remove item.');
        }
    };
    

    const placeOrder = async () => {
        if (!shippingAddress) {
            alert('Please enter a shipping address.');
            return;
        }
    
        const order = { userId, shippingAddress };
    
        try {
            // Place the order
            await axios.post('http://localhost:8081/orders', order);
    
            // Navigate to the Payment page with the total amount
            navigate('/Payment', { state: { total } });
    
            // Delete all items from the cart (while keeping the cart itself)
            for (const item of cart.items) {
                await axios.delete(`http://localhost:8083/cart-items/${item.cartItemId}`);
            }
            
            // Clear cart items in the frontend state
            setCart({ ...cart, items: [] });
        } catch (error) {
            console.error('Error placing order:', error);
            alert('Failed to place order.');
        }
    };
    

    useEffect(() => {
        loadCart();
    }, []);

    return (
        <div className='container'>
            <h2 className='text-center m-4'>Your Cart</h2>
            <div className="row">
                <div className="col-md-8 offset-md-2 border rounded p-4 mt-2 shadow">
                    <h4>Cart Items</h4>
                    {cart.items.length === 0 ? (
                        <p>Your cart is empty.</p>
                    ) : (
                        <ul className="list-group">
                            {cart.items.map(item => (
                                <li key={item.cartItemId} className="list-group-item d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5>{item.product ? item.product.name : 'Product Name Unavailable'}</h5>
                                        <p>Price: ${item.price ? item.price.toFixed(2) : 'N/A'}</p>
                                        <p>Quantity: {item.quantity || 0}</p>
                                    </div>
                                    <button
                                        className="btn btn-danger"
                                        onClick={() => deleteCartItem(item.cartItemId)}
                                    >
                                        Delete
                                    </button>
                                </li>
                            ))}
                        </ul>
                    )}
                    <h5 className="mt-3">Total: ${total.toFixed(2)}</h5>
                    {!showShippingForm && cart.items.length > 0 ? (
                        <button onClick={() => setShowShippingForm(true)} className='btn btn-primary mt-3 mx-2'>
                            Place Order
                        </button>
                    ) : showShippingForm && (
                        <div>
                            <div className="mt-3">
                                <h5>Shipping Address</h5>
                                <textarea
                                    className="form-control"
                                    rows="4"
                                    value={shippingAddress}
                                    onChange={(e) => setShippingAddress(e.target.value)}
                                    placeholder="Enter your shipping address"
                                ></textarea>
                            </div>
                            <button className='btn btn-success mt-3' onClick={placeOrder}>Submit Order</button>
                        </div>
                    )}
                    <Link to="/home" className='btn btn-secondary mt-3'>Continue Shopping</Link>
                </div>
            </div>
        </div>
    );
}
