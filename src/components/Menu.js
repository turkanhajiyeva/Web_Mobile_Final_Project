document.getElementById("searchInput").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        const query = this.value.trim();
        if (query !== "") {
            alert("You searched for: " + query);
        }
    }
});

import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Menu = () => {
  const [menuItems, setMenuItems] = useState([]);
  
  useEffect(() => {
    axios.get('http://localhost:3000/menu')
      .then(response => {
        setMenuItems(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the menu items!', error);
      });
  }, []);
  
  return (
    <div className="restaurant">
      <h1 className="menu">Restaurant Menu</h1>

      <div className="category" id="appetizers">
        <h2 className="menu">Appetizers</h2>
        <ul className="menu">
          {menuItems.filter(item => item.category === 'Appetizers').map(item => (
            <li key={item.id}>
              <h3>{item.name} - ${item.price}</h3>
              <p>{item.description}</p>
            </li>
          ))}
        </ul>
      </div>

      <div className="category" id="main-courses">
        <h2 className="menu">Main Courses</h2>
        <ul className="menu">
          {menuItems.filter(item => item.category === 'Main Courses').map(item => (
            <li key={item.id}>
              <h3>{item.name} - ${item.price}</h3>
              <p>{item.description}</p>
            </li>
          ))}
        </ul>
      </div>

      <div className="category" id="drinks">
        <h2 className="menu">Drinks</h2>
        <ul className="menu">
          {menuItems.filter(item => item.category === 'Drinks').map(item => (
            <li key={item.id}>
              <h3>{item.name} - ${item.price}</h3>
              <p>{item.description}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Menu;
