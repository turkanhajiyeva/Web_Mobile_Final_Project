import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/Home';
import Login from './components/Login';
import Order from './components/Order';
import Details from './components/Details';
import { AuthProvider } from "./context/AuthContext";
import "./components/Main.css"
import 'bootstrap/dist/css/bootstrap.min.css';



function App() {
  return (
    <AuthProvider> {/* Wrap the app with AuthProvider */}
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/login' element={<Login />} />
          <Route path='/order' element={<Order />} />
          {/* <Route path="/details" element={<Details />} /> */}
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
