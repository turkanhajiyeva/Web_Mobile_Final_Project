import { useCart } from "../context/CartContext";
import { useAuth } from "../context/AuthContext";
import { useState } from "react";

const Cart = () => {
  const { cartItems, total, removeFromCart, clearCart, addToCart } = useCart();
  const { user } = useAuth();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [orderStatus, setOrderStatus] = useState(null);
  const [tableNumber, setTableNumber] = useState("");

  const handleSubmitOrder = async () => {
    if (cartItems.length === 0) return;
    setIsSubmitting(true);
    try {
      const params = new URLSearchParams(window.location.search);
      const urlTableId = params.get("table_id");
      const resolvedTableId = urlTableId || tableNumber.trim();
      if (!resolvedTableId) {
        setOrderStatus("error");
        throw new Error("Table number is required to place an order.");
      }
      const orderData = {
        userId: user?.id, // Add this line
        tableId: resolvedTableId,
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
      const responseData = await response.json();
      if (response.ok) {
        setOrderStatus("success");
        clearCart();
        // Redirect to order status page after 2 seconds
        setTimeout(() => {
          window.location.href = `/details?order_id=${responseData.orderId}`;
        }, 2000);
      } else {
        setOrderStatus("error");
        throw new Error(responseData.message || "Failed to place order");
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
      {/* Table number input if not in URL */}
      {(() => {
        const params = new URLSearchParams(window.location.search);
        const urlTableId = params.get("table_id");
        if (!urlTableId) {
          return (
            <div className="mb-3">
              <label htmlFor="tableNumber" className="form-label">Table Number</label>
              <input
                type="text"
                className="form-control"
                id="tableNumber"
                placeholder="Enter your table number"
                value={tableNumber}
                onChange={e => setTableNumber(e.target.value)}
                disabled={isSubmitting}
              />
            </div>
          );
        }
        return null;
      })()}
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
              disabled={isSubmitting || (!tableNumber.trim() && !(new URLSearchParams(window.location.search).get("table_id")))}
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