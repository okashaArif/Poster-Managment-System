import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

export default function Wishlist() {
    const [userId] = useState(localStorage.getItem('userId'));
    const [wishlists, setWishlists] = useState([]);

    const loadWishlists = async () => {
        try {
            const response = await axios.get(`http://localhost:8082/wishlists/${userId}`);
            // Filter out wishlists with no products or invalid data
            const filteredWishlists = response.data?.filter(wishlist => wishlist.products?.length > 0) || [];
            setWishlists(filteredWishlists);
        } catch (error) {
            console.error('Error loading wishlists:', error);
        }
    };

    const removeProduct = async (wishlistId, productId) => {
        try {
            await axios.delete(`http://localhost:8082/wishlists/${userId}/products/${productId}`);
            setWishlists((prevWishlists) =>
                prevWishlists.map((wishlist) =>
                    wishlist.id === wishlistId
                        ? {
                              ...wishlist,
                              products: wishlist.products.filter((product) => product.id !== productId),
                          }
                        : wishlist
                ).filter(wishlist => wishlist.products.length > 0) // Remove wishlists with no products
            );
        } catch (error) {
            console.error('Error removing product:', error);
        }
    };

    useEffect(() => {
        loadWishlists();
    }, []);

    return (
        <div className="container">
            <h2 className="text-center m-4">Your Wishlist</h2>
            <div className="row">
                <div className="col-md-8 offset-md-2 border rounded p-4 mt-2 shadow">
                    <h4>Wishlist Items</h4>
                    {wishlists.length === 0 ? (
                        <p>Your wishlist is empty.</p>
                    ) : (
                        wishlists.map((wishlist) => (
                            <div key={wishlist.id} className="mb-3">
                                
                                <ul className="list-group">
                                    {wishlist.products.map((product) => (
                                        <li
                                            key={product.id}
                                            className="list-group-item d-flex justify-content-between align-items-center"
                                        >
                                            <div>
                                                <h5>{product.name}</h5>
                                                <p>{product.description}</p>
                                                <p>Price: ${product.price.toFixed(2)}</p>
                                            </div>
                                            <button
                                                className="btn btn-danger"
                                                onClick={() => removeProduct(wishlist.id, product.id)}
                                            >
                                                Delete
                                            </button>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        ))
                    )}
                    <Link to="/home" className="btn btn-primary mt-3">
                        Continue Shopping
                    </Link>
                </div>
            </div>
        </div>
    );
}
