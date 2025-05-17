import React from 'react';
import './MenuItemCard.css';

const MenuItemCard = ({ item, onAddToCart }) => {
    return (
        <div className="menu-item-card">
            <div className="card-image-container">
                <img
                    src="./images/placeholder.png"
                    alt={item.name}
                    className="card-image"
                />
                {/* Note: newItem flag is not part of the DTO */}
            </div>
            <div className="card-content">
                <h3 className="item-name">{item.name}</h3>
                <p className="item-description">{item.description}</p>
                <div className="category-badge">{item.category}</div>
                <div className="card-footer">
                    <span className="item-price">${parseFloat(item.price).toFixed(2)}</span>
                    <button
                        className="add-to-cart-btn"
                        onClick={() => onAddToCart(item)}
                    >
                        Add to Cart
                    </button>
                </div>
            </div>
        </div>
    );
};

export default MenuItemCard;