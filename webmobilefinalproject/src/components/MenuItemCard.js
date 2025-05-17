import React from 'react';
import './MenuItemCard.css';

const MenuItemCard = ({ item, onAddToCart }) => {
    return (
        <div className="menu-item-card">
            <div className="card-image-container">
                <img
                    src={item.image || "./images/placeholder.png"}
                    alt={item.name}
                    className="card-image"
                />
                {item.isNew && <span className="new-badge">New</span>}
            </div>
            <div className="card-content">
                <h3 className="item-name">{item.name}</h3>
                <p className="item-description">{item.description}</p>
                <div className="card-footer">
                    <span className="item-price">${item.price.toFixed(2)}</span>
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