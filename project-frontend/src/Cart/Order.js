import React, { useEffect, useState } from 'react';
import axios from 'axios';

export default function Order() {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    axios
      .get('http://localhost:8083/cart')
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        console.error('Error fetching carts:', error);
      });
  }, []);

  const fetchOrdersByUser = (userId) => {
    axios
      .get(`http://localhost:8081/orders/user/${userId}`)
      .then((response) => {
        setSelectedUser(userId);
        setOrders(response.data);
      })
      .catch((error) => {
        console.error(`Error fetching orders for user ${userId}:`, error);
      });
  };

  const markAsDelivered = (orderId) => {
    axios.put(`http://localhost:8081/orders/${orderId}/status?status=DELIVERED`)
      .then(() => {
        alert('Order marked as delivered.');
        setOrders((prevOrders) =>
          prevOrders.map((order) =>
            order.id === orderId ? { ...order, status: 'DELIVERED' } : order
          )
        );
      })
      .catch((error) => {
        console.error(`Error marking order ${orderId} as delivered:`, error);
      });
  };

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', padding: '20px', color: '#333' }}>
      <h1 style={{ textAlign: 'center', marginBottom: '30px', color: '#4CAF50' }}>
        Users and Orders
      </h1>

      <div
        style={{
          display: 'flex',
          gap: '20px',
          justifyContent: 'space-between',
          backgroundColor: '#f9f9f9',
          borderRadius: '10px',
          padding: '20px',
          boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
        }}
      >
        {/* Users Section */}
        <div style={{ flex: 1, padding: '10px', backgroundColor: '#fff', borderRadius: '10px' }}>
          <h2 style={{ color: '#4CAF50', borderBottom: '2px solid #4CAF50', paddingBottom: '10px' }}>
            Users with Carts
          </h2>
          <ul style={{ listStyleType: 'none', padding: '0' }}>
            {users.map((user) => (
              <li
                key={user.id}
                style={{
                  marginBottom: '10px',
                  padding: '10px',
                  backgroundColor: '#f0f0f0',
                  borderRadius: '5px',
                }}
              >
                <span>User ID: {user.id}</span>
                <button
                  onClick={() => fetchOrdersByUser(user.id)}
                  style={{
                    marginLeft: '10px',
                    padding: '5px 10px',
                    backgroundColor: '#4CAF50',
                    color: '#fff',
                    border: 'none',
                    borderRadius: '5px',
                    cursor: 'pointer',
                  }}
                >
                  View Orders
                </button>
              </li>
            ))}
          </ul>
        </div>

        {/* Orders Section */}
        <div style={{ flex: 2, padding: '10px', backgroundColor: '#fff', borderRadius: '10px' }}>
          <h2 style={{ color: '#4CAF50', borderBottom: '2px solid #4CAF50', paddingBottom: '10px' }}>
            Orders
          </h2>
          {selectedUser ? (
            <div>
              <h3 style={{ color: '#333' }}>Orders for User ID: {selectedUser}</h3>
              <ul style={{ listStyleType: 'none', padding: '0' }}>
                {orders.map((order) => (
                  <li
                    key={order.id}
                    style={{
                      marginBottom: '20px',
                      padding: '10px',
                      backgroundColor: '#f0f0f0',
                      borderRadius: '5px',
                      boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                    }}
                  >
                    <p>
                      <strong>Order ID:</strong> {order.id}
                    </p>
                    <p>
                      <strong>Status:</strong>{' '}
                      <span
                        style={{
                          color: order.status === 'DELIVERED' ? '#4CAF50' : '#f44336',
                        }}
                      >
                        {order.status}
                      </span>
                    </p>
                    <p>
                      <strong>Shipping Address:</strong> {order.shippingAddress}
                    </p>
                    {order.status !== 'DELIVERED' && (
                      <button
                        onClick={() => markAsDelivered(order.id)}
                        style={{
                          padding: '5px 10px',
                          backgroundColor: '#4CAF50',
                          color: '#fff',
                          border: 'none',
                          borderRadius: '5px',
                          cursor: 'pointer',
                          marginTop: '10px',
                        }}
                      >
                        Mark as Delivered
                      </button>
                    )}
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <p style={{ color: '#666' }}>Select a user to view their orders.</p>
          )}
        </div>
      </div>
    </div>
  );
}
