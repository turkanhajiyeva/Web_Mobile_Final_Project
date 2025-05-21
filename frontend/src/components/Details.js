import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useAuth } from "../context/AuthContext";

const Details = () => {
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const location = useLocation();
    const { user } = useAuth();

    useEffect(() => {
        const fetchOrderDetails = async () => {
            const params = new URLSearchParams(location.search);
            const orderId = params.get("order_id");

            if (!orderId) {
                setError("No order ID provided");
                setLoading(false);
                return;
            }

            try {
                const response = await fetch(`http://localhost:8080/api/orders/${orderId}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch order details");
                }
                const data = await response.json();
                setOrder(data);
            } catch (err) {
                console.error("Error fetching order details:", err);
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchOrderDetails();
    }, [location.search]);

    if (loading) {
        return (
            <div className="container mt-5 text-center">
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mt-5">
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            </div>
        );
    }

    if (!order) {
        return (
            <div className="container mt-5">
                <div className="alert alert-info" role="alert">
                    No order found
                </div>
            </div>
        );
    }

    return (
        <div className="container mt-5">
            <div className="card shadow-sm">
                <div className="card-header bg-miku text-white">
                    <h3 className="mb-0">Order Details</h3>
                </div>
                <div className="card-body">
                    <div className="row mb-4">
                        <div className="col-md-6">
                            <h5>Order Information</h5>
                            <p><strong>Order ID:</strong> {order.orderId}</p>
                            <p><strong>Status:</strong> <span className={`badge bg-${getStatusColor(order.status)}`}>{order.status}</span></p>
                            <p><strong>Order Time:</strong> {new Date(order.orderTime).toLocaleString()}</p>
                            <p><strong>Total Amount:</strong> ${order.totalAmount}</p>
                        </div>
                    </div>

                    <h5>Order Items</h5>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>Item</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                {order.items.map((item) => (
                                    <tr key={item.orderItemId}>
                                        <td>{item.menuItem ? item.menuItem.name : "Unknown Item"}</td>
                                        <td>{item.quantity}</td>
                                        <td>{item.menuItem ? `$${item.menuItem.price}` : "-"}</td>
                                        <td>{item.menuItem ? `$${(item.quantity * item.menuItem.price).toFixed(2)}` : "-"}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

const getStatusColor = (status) => {
    switch (status.toLowerCase()) {
        case 'pending':
            return 'warning';
        case 'in_preparation':
            return 'info';
        case 'ready':
            return 'success';
        case 'delivered':
            return 'primary';
        default:
            return 'secondary';
    }
};

export default Details;
