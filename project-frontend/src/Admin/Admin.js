import React from 'react';
import { Link } from 'react-router-dom';

export default function Admin() {
  return (
    <div className="container">
      {/* Go Back Button */}
      <div style={{ position: 'absolute', top: '20px', left: '20px' }}>
        <Link className="btn btn-secondary" to="/">Go Back</Link>
      </div>

      <h1>Admin Page</h1>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>User</td>
            <td>
              <Link className="btn btn-primary m-2" to="/allusers">View</Link>
            </td>
          </tr>
          <tr>
            <td>Products</td>
            <td>
              <Link className="btn btn-primary m-2" to="/allproducts">View</Link>
              <Link className="btn btn-primary m-2" to="/addProduct">Add</Link>
            </td>
          </tr>
          <tr>
            <td>Order</td>
            <td>
              <Link className="btn btn-primary m-2" to="/allorder">View</Link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
