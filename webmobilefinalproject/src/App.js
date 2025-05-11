import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/Home';
import Details from './components/Details';
import "./components/Main.css"


function App() {
  return (
    <Router>
      <Navbar/>
      <Routes>
          <Route path='/' element={<Home />} />
          {/* <Route path="/details" element={<Details />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
