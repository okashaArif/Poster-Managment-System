
import './App.css';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router,Routes,Route} from "react-router-dom";
import AddUser from './users/AddUser';
import EditUser from './users/EditUser';
import ViewUser from './users/ViewUser';
import Admin from './Admin/Admin';
import Signin from './Signin/Signin';
import Signup from './Signin/Signup';
import ForgetPassword from './Signin/ForgetPassword';
import MainPage from './MainPage/MainPage';
import ShowCart from './Cart/ShowCart';
import Wishlist from './Cart/Wishlist';
import Payment from './Cart/Payment';
import AddProduct from './Product/AddProduct'
import EditProduct from './Product/EditProduct';
import ViewProduct from './Product/ViewProduct';
import Products from './Pages/Products'
import User from './Pages/User'
import CreateShippingAddress from './Cart/CreateShippingAddress'
import Order from './Cart/Order';

function App() {
  return (
    <div className="App">
      <Router>

        <Routes>
          <Route exact path="/adduser" element={<AddUser/>}/>
          <Route exact path="/edituser/:id" element={<EditUser/>}/>
          <Route exact path="/Viewuser/:id" element={<ViewUser/>}/>
          <Route exact path="/Admin" element={<Admin/>}/>
          <Route exact path="/" element={<Signin/>}/>
          <Route exact path="/signup" element={<Signup/>}/>
          <Route exact path="/updatepassword" element={<ForgetPassword/>}/>
          <Route exact path="/mainpage" element={<MainPage/>}/>
          <Route exact path="/showcart" element={<ShowCart/>}/>
          <Route exact path="/wishlist" element={<Wishlist/>}/>
          <Route exact path="/payment" element={<Payment/>}/>
          <Route exact path="/addProduct" element={<AddProduct/>}/>
          <Route exact path="/editProduct/:id" element={<EditProduct/>}/>
          <Route exact path="/ViewProduct/:id" element={<ViewProduct/>}/>
          <Route exact path="/allproducts" element={<Products/>}/>
          <Route exact path="/allusers" element={<User/>}/>
          <Route exact path="/home" element={<MainPage/>}/>
          <Route exact path="/Payment" element={<Payment/>}/>
          <Route exact path="/Shipping" element={<CreateShippingAddress/>}/>
          <Route exact path="/allorder" element={<Order/>}/>
        </Routes>
        </Router>
    </div>
  );
}

export default App;
