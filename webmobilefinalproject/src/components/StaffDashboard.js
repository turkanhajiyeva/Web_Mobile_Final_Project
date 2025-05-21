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
      // Update the order in the UI
      setOrders(orders => orders.map(order =>
        order.orderId === orderId ? { ...order, status: newStatus } : order
      ));
    } catch (err) {
      alert('Error updating status: ' + err.message);
    } finally {
      setUpdatingOrderId(null);
    }
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
    </div>
  );
};

export default StaffDashboard; 