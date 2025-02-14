import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function MainPage() {
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [userId] = useState(localStorage.getItem('userId'));
    const [username] = useState(localStorage.getItem('username') || '');
    const [showReviewForm, setShowReviewForm] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [reviewContent, setReviewContent] = useState('');
    const [rating, setRating] = useState(1);

    useEffect(() => {
        fetchProducts();
    }, [userId, navigate]);

    const fetchProducts = async () => {
        try {
            const response = await axios.get('http://localhost:8084/products');
            const activeProducts = response.data.filter((product) => product.isActive);
            
            // Fetch likes and dislikes for each product
            const productsWithLikes = await Promise.all(
                activeProducts.map(async (product) => {
                    const { data: counts } = await axios.get(`http://localhost:8084/product-likes-dislikes/product/${product.id}/likes-dislikes-count`);
                    const { data: reviews } = await axios.get(`http://localhost:8086/reviews/product/${product.id}`); // Fetch reviews for the product
                    console.log(counts.likes,counts.dislikes);
                    return { ...product, likes: counts.likes, dislikes: counts.dislikes, reviews };
                })
            );

            setProducts(productsWithLikes);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleLike = async (productId) => {
        try {
            await axios.post('http://localhost:8084/product-likes-dislikes', {
                user: { id: userId },
                product: { id: productId },
                like: true,
            });
            fetchProducts();
        } catch (error) {
            console.error('Error liking product:', error);
        }
    };

    const handleDislike = async (productId) => {
        try {
            await axios.post('http://localhost:8084/product-likes-dislikes', {
                user: { id: userId },
                product: { id: productId },
                like: false,
            });
            fetchProducts();
        } catch (error) {
            console.error('Error disliking product:', error);
        }
    };

    const addToCart = async (productId, productPrice) => {
        const cartItem = {
            product: { id: productId },
            cart: { id: userId },
            quantity: 1,
            price: productPrice,
        };
        try {
            await axios.post('http://localhost:8083/cart-items', cartItem);
            alert('Added to cart!');
        } catch (error) {
            console.error('Error adding to cart:', error);
        }
    };

    const addToWishlist = async (productId) => {
        const wishlistItem = {
            user: { id: parseInt(userId) },
            products: [{ id: productId }],
        };
        try {
            await axios.post('http://localhost:8082/wishlists', wishlistItem, {
                headers: { 'Content-Type': 'application/json' },
            });
            alert('Added to wishlist!');
        } catch (error) {
            console.error('Error adding to wishlist:', error);
        }
    };

    const handleReviewClick = (product) => {
        console.log(product);
        setSelectedProduct(product);
        setShowReviewForm(true);
    };

    const handleSubmitReview = async () => {
        if (!reviewContent || !rating) {
            alert('Please provide a review and a rating.');
            return;
        }
        const review = {
            content: reviewContent,
            rating: rating,
            product: { id: selectedProduct.id },
            user: { id: userId },
        };
        console.log(review);
        try {
            await axios.post('http://localhost:8086/reviews', review);
            alert('Review submitted!');
            setShowReviewForm(false);
            setReviewContent('');
            setRating(1);
            fetchProducts();
        } catch (error) {
            console.error('Error submitting review:', error);
        }
    };

    return (
        <div style={{ backgroundColor: '#f8f9fa', minHeight: '100vh' }}>
            <div className="d-flex justify-content-between align-items-center p-3" style={{ backgroundColor: '#343a40', color: '#fff' }}>
                <span style={{ fontSize: '18px', fontWeight: 'bold' }}>Welcome, {username}</span>
                <div>
                    <button className="btn btn-warning btn-sm mx-2" onClick={() => navigate('/showcart')}>Cart</button>
                    <button className="btn btn-secondary btn-sm mx-2" onClick={() => navigate('/wishlist')}>Wishlist</button>
                    <button className="btn btn-danger btn-sm mx-2" onClick={() => navigate('/')}>Logout</button>
                </div>
            </div>
            <div className="container mt-5">
                <h2 className="mb-4">Product List</h2>
                <div className="row">
                    {products.length > 0 ? (
                        products.map((product) => (
                            <div className="col-md-3 mb-4" key={product.id}>
                                <div className="card shadow-sm" style={{ borderRadius: '8px', border: 'none' }}>
                                    <img src={product.imageUrl} alt={product.name} className="card-img-top" style={{ height: '200px', objectFit: 'cover', width: '100%' }} />
                                    <div className="card-body text-center">
                                        <h5 className="card-title">{product.name}</h5>
                                        <p className="card-text">Price: {product.price}</p>
                                        <p className="card-text">Likes: {product.likes} | Dislikes: {product.dislikes}</p>
                                        <div className="d-flex justify-content-center mb-2">
                                            <button className="btn btn-success btn-sm mx-1" onClick={() => handleLike(product.id)}>Like</button>
                                            <button className="btn btn-danger btn-sm mx-1" onClick={() => handleDislike(product.id)}>Dislike</button>
                                        </div>
                                        <button className="btn btn-primary btn-sm mx-1" onClick={() => addToCart(product.id, product.price)}>Add to Cart</button>
                                        <button className="btn btn-secondary btn-sm mx-1" onClick={() => addToWishlist(product.id)}>Add to Wishlist</button>
                                        <button className="btn btn-info btn-sm mx-1" onClick={() => handleReviewClick(product)}>Review</button>
                                    </div>
                                    <div className="card-footer">
                                        <h6>Reviews:</h6>
                                        {product.reviews.length > 0 ? (
                                            product.reviews.map((review) => (
                                                <div key={review.id} className="mb-2">
                                                    <p><strong>{review.user.username}</strong> - Rating: {review.rating}</p>
                                                    <p>{review.content}</p>
                                                </div>
                                            ))
                                        ) : (
                                            <p>No reviews yet</p>
                                        )}
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        <p>No products available</p>
                    )}
                </div>
            </div>
            {showReviewForm && selectedProduct && (
                <div className="container my-5">
                    <h4>Write your Review for {selectedProduct.name}</h4>
                    <textarea
                        className="form-control"
                        rows="4"
                        value={reviewContent}
                        onChange={(e) => setReviewContent(e.target.value)}
                        placeholder="Write your review here"
                    ></textarea>
                    <div className="mt-2">
                        <label htmlFor="rating">Rating:</label>
                        <select
                            id="rating"
                            className="form-control"
                            value={rating}
                            onChange={(e) => setRating(parseInt(e.target.value))}
                        >
                            {[1, 2, 3, 4, 5].map((star) => (
                                <option key={star} value={star}>{star}</option>
                            ))}
                        </select>
                    </div>
                    <button className="btn btn-primary mt-3" onClick={handleSubmitReview}>Submit Review</button>
                </div>
            )}
        </div>
    );
}
