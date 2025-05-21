import { useCart } from "../context/CartContext";
import { useAuth } from "../context/AuthContext";
import { useState } from "react";

const Cart = () => {
  const { cartItems, total, removeFromCart, clearCart, addToCart } = useCart();
  const { user } = useAuth();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [orderStatus, setOrderStatus] = useState(null);

  const handleSubmitOrder = async () => {
    if (cartItems.length === 0) return;
    
    setIsSubmitting(true);
    try {
      const orderData = {
        items: cartItems.map(item => ({
          menuItemId: item.id,
          quantity: item.quantity
        })),
        totalAmount: total
      };

      const response = await fetch("http://localhost:8080/api/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
      });

      if (response.ok) {
        setOrderStatus("success");
        clearCart();
      } else {
        setOrderStatus("error");
      }
    } catch (error) {
      console.error("Error submitting order:", error);
      setOrderStatus("error");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="cart-container">
      {cartItems.length === 0 ? (
        <div className="text-center py-4">
          <i className="bi bi-cart-x fs-1"></i>
          <p className="mt-3">Your cart is empty</p>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {cartItems.map((item) => (
              <div key={item.id} className="cart-item">
                <div className="d-flex flex-column">
                  <span className="fw-bold">{item.name}</span>
                  <span className="text-muted">${item.price.toFixed(2)} each</span>
                </div>
                <div className="quantity-controls">
                  <button 
                    className="btn btn-sm btn-miku" 
                    onClick={() => removeFromCart(item.id)}
                  >
                    <i className="bi bi-dash"></i>
                  </button>
                  <span className="mx-2">{item.quantity}</span>
                  <button 
                    className="btn btn-sm btn-miku" 
                    onClick={() => addToCart(item)}
                  >
                    <i className="bi bi-plus"></i>
                  </button>
                </div>
                <span className="fw-bold">${(item.price * item.quantity).toFixed(2)}</span>
              </div>
            ))}
          </div>
          <div className="cart-total mt-3">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <span>Subtotal:</span>
              <span className="fw-bold">${total.toFixed(2)}</span>
            </div>
            <button 
              className="btn btn-miku w-100" 
              onClick={handleSubmitOrder}
              disabled={isSubmitting}
            >
              {isSubmitting ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Processing...
                </>
              ) : (
                "Place Order"
              )}
            </button>
            {orderStatus === "success" && (
              <div className="alert alert-success mt-3 mb-0">
                <i className="bi bi-check-circle me-2"></i>
                Order placed successfully!
              </div>
            )}
            {orderStatus === "error" && (
              <div className="alert alert-danger mt-3 mb-0">
                <i className="bi bi-exclamation-circle me-2"></i>
                Failed to place order. Please try again.
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
};

export default Cart; 