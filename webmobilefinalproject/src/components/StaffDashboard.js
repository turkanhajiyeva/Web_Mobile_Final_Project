import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const statusOptions = [
  'pending',
  'in_preparation',
  'ready',
  'delivered'
];

const STAFF_ROLES = ['Manager', 'Waiter', 'Kitchen Staff'];

const StaffDashboard = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updatingOrderId, setUpdatingOrderId] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [detailsLoading, setDetailsLoading] = useState(false);
  const [detailsError, setDetailsError] = useState(null);

  useEffect(() => {
    // Redirect if not staff
    if (!user || !STAFF_ROLES.includes(user.role)) {
      navigate('/login');
      return;
    }
    fetchOrders();
    // eslint-disable-next-line
  }, [user]);

  const fetchOrders = async () => {
    setLoading(true);
    try {
      const response = await fetch('http://localhost:8080/api/orders');
      if (!response.ok) throw new Error('Failed to fetch orders');
      const data = await response.json();
      setOrders(data);
      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (orderId, newStatus) => {
    setUpdatingOrderId(orderId);
    try {
      const response = await fetch(`http://localhost:8080/api/orders/${orderId}/status`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status: newStatus })
      });
      if (!response.ok) throw new Error('Failed to update status');
      setOrders(orders => orders.map(order =>
        order.orderId === orderId ? { ...order, status: newStatus } : order
      ));
    } catch (err) {
      alert('Error updating status: ' + err.message);
    } finally {
      setUpdatingOrderId(null);
    }
  };

  const openOrderDetails = async (orderId) => {
    setDetailsLoading(true);
    setDetailsError(null);
    setSelectedOrder(null);
    try {
      const response = await fetch(`http://localhost:8080/api/orders/${orderId}`);
      if (!response.ok) throw new Error('Failed to fetch order details');
      const data = await response.json();
      setSelectedOrder(data);
    } catch (err) {
      setDetailsError(err.message);
    } finally {
      setDetailsLoading(false);
    }
  };

  const closeOrderDetails = () => {
    setSelectedOrder(null);
    setDetailsError(null);
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Staff Dashboard</h2>
      {loading && <div>Loading orders...</div>}
      {error && <div className="alert alert-danger">{error}</div>}
      {!loading && !error && (
        <div className="table-responsive">
          <table className="table table-bordered table-hover">
            <thead className="table-light">
              <tr>
                <th>Order ID</th>
                <th>Table</th>
                <th>Status</th>
                <th>Order Time</th>
                <th>Total</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.length === 0 ? (
                <tr><td colSpan="6" className="text-center">No orders found.</td></tr>
              ) : (
                orders.map(order => (
                  <tr key={order.orderId}>
                    <td>{order.orderId}</td>
                    <td>{order.tableId}</td>
                    <td>
                      <select
                        value={order.status}
                        onChange={e => handleStatusChange(order.orderId, e.target.value)}
                        disabled={updatingOrderId === order.orderId}
                        className="form-select"
                      >
                        {statusOptions.map(status => (
                          <option key={status} value={status}>{status}</option>
                        ))}
                      </select>
                    </td>
                    <td>{new Date(order.orderTime).toLocaleString()}</td>
                    <td>${order.totalAmount}</td>
                    <td>
                      <button className="btn btn-info btn-sm me-2" onClick={() => openOrderDetails(order.orderId)}>
                        View
                      </button>
                      {updatingOrderId === order.orderId ? (
                        <span className="text-info">Updating...</span>
                      ) : (
                        <span className="text-muted">-</span>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
      {/* Order Details Modal */}
      {selectedOrder && (
        <div className="modal show d-block" tabIndex="-1" style={{ background: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-lg">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Order Details (ID: {selectedOrder.orderId})</h5>
                <button type="button" className="btn-close" onClick={closeOrderDetails}></button>
              </div>
              <div className="modal-body">
                {detailsLoading ? (
                  <div>Loading...</div>
                ) : detailsError ? (
                  <div className="alert alert-danger">{detailsError}</div>
                ) : (
                  <>
                    <p><strong>Table:</strong> {selectedOrder.tableId}</p>
                    <p><strong>Status:</strong> <span className={`badge bg-${getStatusColor(selectedOrder.status)}`}>{selectedOrder.status}</span></p>
                    <p><strong>Order Time:</strong> {new Date(selectedOrder.orderTime).toLocaleString()}</p>
                    <p><strong>Total Amount:</strong> ${selectedOrder.totalAmount}</p>
                    <h6>Order Items</h6>
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
                          {selectedOrder.items.map((item) => (
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
                  </>
                )}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={closeOrderDetails}>Close</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

function getStatusColor(status) {
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
}

export default StaffDashboard; 