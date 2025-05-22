import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import WebSocketNotifs from './components/WebSocketNotifs';
import Navbar from './components/Navbar';
import Home from './components/Home';
import Login from './components/Login';
import Order from './components/Order';
import StaffDashboard from './components/StaffDashboard';
import MenuManagement from './components/MenuManagement';
import { AuthProvider } from "./context/AuthContext";
import { CartProvider } from "./context/CartContext";
import "./components/Main.css"
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <WebSocketNotifs />
          <Navbar />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login />} />
            <Route path='/order' element={<Order />} />
            <Route path="/staff" element={<StaffDashboard />} />
            <Route path="/menu-management" element={<MenuManagement />} />
          </Routes>
        </Router>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
