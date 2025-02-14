import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function AddProduct() {
    const navigate = useNavigate();
    const [product, setProduct] = useState({
        name: '',
        description: '',
        price: '',
        category: '',
        imageUrl: '',
        active: true,
    });

    const { name, description, price, category, imageUrl, active } = product;

    const onInputChange = (e) => {
        const { name, value } = e.target;
        setProduct({ ...product, [name]: value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        // eslint-disable-next-line
        const updatedProduct = {
            ...product,
            price: parseFloat(product.price) // Convert to number
        };
        
        try {
            console.log(product);
            await axios.post('http://localhost:8084/products', product);
            navigate('/Admin'); // Redirect after successful addition
        } catch (error) {
            console.error('Error adding product:', error);
        }
    };

    return (
        <div className='container'>
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className='text-center m-4'>Add Product</h2>
                    <form onSubmit={onSubmit}>
                        <div className="mb-3">
                            <label htmlFor="name" className='form-label'>Product Name</label>
                            <input 
                                type="text" 
                                className='form-control' 
                                placeholder='Enter product name' 
                                name='name' 
                                value={name} 
                                onChange={onInputChange} 
                                required 
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="description" className='form-label'>Description</label>
                            <textarea 
                                className='form-control' 
                                placeholder='Enter product description' 
                                name='description' 
                                value={description} 
                                onChange={onInputChange} 
                                required 
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="price" className='form-label'>Price</label>
                            <input 
                                type="number" 
                                className='form-control' 
                                placeholder='Enter product price' 
                                name='price' 
                                value={price} 
                                onChange={onInputChange} 
                                required 
                                min="0" 
                                step="0.01" 
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="category" className='form-label'>Category</label>
                            <input 
                                type="text" 
                                className='form-control' 
                                placeholder='Enter product category' 
                                name='category' 
                                value={category} 
                                onChange={onInputChange} 
                                required 
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="imageUrl" className='form-label'>Image URL</label>
                            <input 
                                type="text" 
                                className='form-control' 
                                placeholder='Enter product image URL' 
                                name='imageUrl' 
                                value={imageUrl} 
                                onChange={onInputChange} 
                            />
                        </div>
                        <div className="form-check mb-3">
                            <input 
                                type="checkbox" 
                                className='form-check-input' 
                                name='active' 
                                checked={active} 
                                onChange={() => setProduct({ ...product, active: false })} 
                            />
                            <label className='form-check-label'>Active</label>
                        </div>
                        <button type='submit' className='btn btn-outline-primary'>Add Product</button>
                        <button type='button' className='btn btn-outline-danger mx-2' onClick={() => navigate('/Admin')}>Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    );
}