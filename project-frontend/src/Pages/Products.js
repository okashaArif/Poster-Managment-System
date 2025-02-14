import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from 'react-router-dom';

export default function Products() {
  const [products, setProducts] = useState([]);

  // Fetch all products
  useEffect(() => {
    axios
      .get("http://localhost:8084/products")
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error("There was an error fetching the products!", error);
      });
  }, []);

  // Delete a product
  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8084/products/${id}`)
      .then(() => {
        setProducts(products.filter((product) => product.id !== id));
      })
      .catch((error) => {
        console.error("There was an error deleting the product!", error);
      });
  };

 

  return (
    <div>
      <h1>Products</h1>
      <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>{product.name}</td>
              <td>{product.description}</td>
              <td>${product.price.toFixed(2)}</td>
              <td>{product.category}</td>
              <td>
              <Link className="btn btn-primary mx-2" to={`/ViewProduct/${product.id}`}>View</Link>
              <Link className="btn btn-outline-primary mx-2" to={`/editProduct/${product.id}`}>Edit</Link>
                <button onClick={() => handleDelete(product.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
