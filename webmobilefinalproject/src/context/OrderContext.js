import React, { createContext, useState } from 'react';

export const OrderContext = createContext();

export const OrderProvider = ({ children }) => {
    const [cart, setCart] = useState([]);
    const [activeOrder, setActiveOrder] = useState(null);

    const addToCart = (item, quantity) => {
        // Add item to cart logic
        const existingItemIndex = cart.findIndex(cartItem => cartItem.id === item.id);

        if (existingItemIndex >= 0) {
            // Update quantity if item exists
            const updatedCart = [...cart];
            updatedCart[existingItemIndex].quantity += quantity;
            setCart(updatedCart);
        } else {
            // Add new item with quantity
            setCart([...cart, { ...item, quantity }]);
        }
    };

    const removeFromCart = (itemId) => {
        // Remove item from cart logic
        setCart(cart.filter(item => item.id !== itemId));
    };

    return (
        <OrderContext.Provider value={{ cart, activeOrder, addToCart, removeFromCart, setActiveOrder }}>
            {children}
        </OrderContext.Provider>
    );
};
