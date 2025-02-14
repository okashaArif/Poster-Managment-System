import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

export default function ViewProduct() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    const loadProduct = async () => {
        try {
            const result = await axios.get(`http://localhost:8084/products/${id}`);
            console.log(result);
            setProduct(result.data);
        } catch (error) {
            console.error('Error loading product:', error);
        }
    };

    useEffect(() => {
        loadProduct();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    if (!product) {
        return <div>Loading...</div>; // Loading state
    }

    return (
        <div className='container'>
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className='text-center m-4'>{product.name}</h2>
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Description</h5>
                            <p className="card-text">{product.description}</p>
                            <h5 className="card-title">Price</h5>
                            <p className="card-text">${product.price}</p>
                            <h5 className="card-title">Category</h5>
                            <p className="card-text">{product.category}</p>
                            {product.imageUrl && (
                                <div className="mb-3">
                                    <img src={product.imageUrl} alt={product.name} className="img-fluid" />
                                </div>
                            )}
                            <h5 className="card-title">Status</h5>
                            <p className="card-text">{product.isActive ? 'Active' : 'Inactive'}</p>
                        </div>
                    </div>
                    <Link to="/allproducts" className='btn btn-primary my-2'>Back to Products</Link>
                </div>
            </div>
        </div>
    );
}