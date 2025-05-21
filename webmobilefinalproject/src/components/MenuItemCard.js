import React from 'react';
import './MenuItemCard.css';

const MenuItemCard = ({ item, onAddToCart, isMobile }) => {
    return (
        <div className="card shadow-sm w-100">
            <img 
                src={item.image || "./images/placeholder.png"} 
                className="card-img-top" 
                alt={item.name} 
            />
            <div className="card-body">
                <h5 className="card-title">{item.name}</h5>
                <p className="card-text">{item.description}</p>
                <div className="d-flex justify-content-between align-items-center">
                    <span className="badge bg-miku">{item.category}</span>
                    <span className="fw-bold">${parseFloat(item.price).toFixed(2)}</span>
                </div>
                <button 
                    className="btn btn-miku w-100 mt-3"
                    onClick={() => onAddToCart(item)}
                >
                    ADD TO CART
                </button>
            </div>
        </div>
    );
};

export default MenuItemCard;