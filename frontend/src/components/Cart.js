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
    if (!user || !user.userid) {
      setOrderStatus("error");
      alert("You must be logged in to place an order.");
      return;
    }

    if (cartItems.length === 0) {
      setOrderStatus("error");
      alert("Your cart is empty.");
      return;
    }

    setIsSubmitting(true);
    setOrderStatus(null);

    try {
      const params = new URLSearchParams(window.location.search);
      const urlTableId = params.get("table_id");
      const resolvedTableId = urlTableId || tableNumber.trim();

      if (!resolvedTableId) {
        setOrderStatus("error");
        alert("Table number is required to place an order.");
        setIsSubmitting(false);
        return;
      }

      const orderData = {
        userId: user.userid,
        tableId: resolvedTableId,
        items: cartItems.map(item => ({
          menuItemId: item.id,
          quantity: item.quantity,
        })),
        totalAmount: total,
      };

      console.log("Order Data:", orderData);

      const response = await fetch("http://localhost:8080/api/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${user.token}` 
        },
        body: JSON.stringify(orderData),
        credentials: "include", 
      });

      const responseData = await response.json();

      if (response.ok) {
        setOrderStatus("success");
        clearCart();
        // setTimeout(() => window.location.href = `/details?order_id=${responseData.orderId}`, 2000);
      } else {
        setOrderStatus("error");
        alert(responseData.message || "Failed to place order");
      }
    } catch (error) {
      console.error("Error submitting order:", error);
      setOrderStatus("error");
      alert("Server error. Please try again later.");
    } finally {
      setIsSubmitting(false);
    }
  };

  // Helper to check if table ID is available either from URL or input
  const params = new URLSearchParams(window.location.search);
  const urlTableId = params.get("table_id");
  const isPlaceOrderDisabled =
    isSubmitting ||
    cartItems.length === 0 ||
    (!urlTableId && !tableNumber.trim()) ||
    !user?.userid;

  return (
    <div className="cart-container">
      {/* Table number input if not in URL */}
      {!urlTableId && (
        <div className="mb-3">
          <label htmlFor="tableNumber" className="form-label">
            Table Number
          </label>
          <input
            type="text"
            className="form-control"
            id="tableNumber"
            placeholder="Enter your table number"
            value={tableNumber}
            onChange={(e) => setTableNumber(e.target.value)}
            disabled={isSubmitting}
          />
        </div>
      )}

      {cartItems.length === 0 ? (
        <div className="text-center py-4">
          <i className="bi bi-cart-x fs-1"></i>
          <p className="mt-3">Your cart is empty</p>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {cartItems.map((item) => (
              <div key={item.id} className="cart-item d-flex justify-content-between align-items-center mb-2">
                <div>
                  <span className="fw-bold">{item.name}</span>
                  <br />
                  <small className="text-muted">${item.price.toFixed(2)} each</small>
                </div>
                <div className="quantity-controls d-flex align-items-center">
                  <button
                    className="btn btn-sm btn-miku"
                    onClick={() => removeFromCart(item.id)}
                    disabled={isSubmitting}
                  >
                    <i className="bi bi-dash"></i>
                  </button>
                  <span className="mx-2">{item.quantity}</span>
                  <button
                    className="btn btn-sm btn-miku"
                    onClick={() => addToCart(item)}
                    disabled={isSubmitting}
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
              disabled={isPlaceOrderDisabled}
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
