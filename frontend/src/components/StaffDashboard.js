import React, { useEffect, useState, useCallback, useRef } from 'react';

const statusOptions = [
  'all',
  'pending',
  'in_preparation',
  'ready',
  'delivered'
];

const STAFF_ROLES = ['Manager', 'Waiter', 'Kitchen Staff'];

// Mock auth context for demo - replace with your actual auth context
const mockUser = { id: 1, role: 'Kitchen Staff', name: 'John Doe' };

const StaffDashboard = () => {
  const user = mockUser; // Replace with: const { user } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [updatingOrderId, setUpdatingOrderId] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [detailsLoading, setDetailsLoading] = useState(false);
  const [detailsError, setDetailsError] = useState(null);
  const [filterStatus, setFilterStatus] = useState('all');
  const [filterTable, setFilterTable] = useState('');
  const [notifications, setNotifications] = useState([]);
  const [wsConnection, setWsConnection] = useState(null);
  const [connectionStatus, setConnectionStatus] = useState('disconnected');
  const wsRef = useRef(null);
  const reconnectTimeoutRef = useRef(null);

  // Mock data for demonstration
  const mockOrders = [
    {
      orderId: 1,
      tableId: 'A1',
      status: 'pending',
      orderTime: new Date().toISOString(),
      totalAmount: 25.50,
      items: [
        {
          orderItemId: 1,
          quantity: 2,
          menuItem: { name: 'Burger', price: 12.75 }
        }
      ]
    },
    {
      orderId: 2,
      tableId: 'B3',
      status: 'in_preparation',
      orderTime: new Date(Date.now() - 600000).toISOString(),
      totalAmount: 18.25,
      items: [
        {
          orderItemId: 2,
          quantity: 1,
          menuItem: { name: 'Pizza', price: 18.25 }
        }
      ]
    }
  ];

  useEffect(() => {
    // Initialize with mock data and setup WebSocket
    setOrders(mockOrders);
    setLoading(false);
    setupWebSocketConnection();
    
    // Cleanup on unmount
    return () => {
      if (wsRef.current) {
        wsRef.current.close();
      }
      if (reconnectTimeoutRef.current) {
        clearTimeout(reconnectTimeoutRef.current);
      }
    };
  }, []);

  const setupWebSocketConnection = useCallback(() => {
    if (!user) return;

    // Mock WebSocket connection for demo
    setConnectionStatus('connected');
    
    // Simulate real-time updates
    const interval = setInterval(() => {
      if (Math.random() > 0.8) { // 20% chance every 5 seconds
        const newOrder = {
          orderId: Date.now(),
          tableId: `T${Math.floor(Math.random() * 10) + 1}`,
          status: 'pending',
          orderTime: new Date().toISOString(),
          totalAmount: Math.floor(Math.random() * 50) + 10,
          items: [
            {
              orderItemId: Date.now(),
              quantity: Math.floor(Math.random() * 3) + 1,
              menuItem: { 
                name: ['Burger', 'Pizza', 'Salad', 'Pasta'][Math.floor(Math.random() * 4)], 
                price: Math.floor(Math.random() * 20) + 5 
              }
            }
          ]
        };
        
        setOrders(prev => [newOrder, ...prev]);
        showNotification(`New order received: Order #${newOrder.orderId}`, 'info');
      }
    }, 5000);

    return () => clearInterval(interval);
  }, [user]);

  const handleWebSocketMessage = useCallback((message) => {
    switch (message.type) {
      case 'NEW_ORDER':
        setOrders(prevOrders => [message.order, ...prevOrders]);
        showNotification(`New order received: Order #${message.order.orderId}`, 'info');
        break;
        
      case 'ORDER_STATUS_UPDATED':
        setOrders(prevOrders => 
          prevOrders.map(order => 
            order.orderId === message.orderId 
              ? { ...order, status: message.status }
              : order
          )
        );
        
        if (message.status === 'ready' && user.role === 'Waiter') {
          showNotification(`Order #${message.orderId} is ready for delivery!`, 'success');
        }
        break;
        
      case 'ORDER_CANCELLED':
        setOrders(prevOrders => 
          prevOrders.filter(order => order.orderId !== message.orderId)
        );
        showNotification(`Order #${message.orderId} was cancelled`, 'warning');
        break;
        
      default:
        console.log('Unknown message type:', message.type);
    }
  }, [user.role]);

  const showNotification = (message, type = 'info') => {
    const notification = {
      id: Date.now(),
      message,
      type,
      timestamp: new Date()
    };
    
    setNotifications(prev => [notification, ...prev.slice(0, 4)]);
    
    setTimeout(() => {
      setNotifications(prev => prev.filter(n => n.id !== notification.id));
    }, 5000);
  };

  const fetchOrders = async () => {
    setLoading(true);
    try {
      // Mock API call - replace with actual fetch
      setTimeout(() => {
        setOrders(mockOrders);
        setError(null);
        setLoading(false);
      }, 1000);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const handleStatusChange = async (orderId, newStatus) => {
    setUpdatingOrderId(orderId);
    try {
      // Mock API call - replace with actual PATCH request
      setTimeout(() => {
        setOrders(orders => orders.map(order =>
          order.orderId === orderId ? { ...order, status: newStatus } : order
        ));
        showNotification(`Order #${orderId} status updated to ${newStatus.replace('_', ' ')}`, 'success');
        setUpdatingOrderId(null);
      }, 500);
      
    } catch (err) {
      console.error('Error updating status:', err);
      showNotification(`Error updating status: ${err.message}`, 'error');
      setUpdatingOrderId(null);
    }
  };

  const openOrderDetails = async (orderId) => {
    setDetailsLoading(true);
    setDetailsError(null);
    setSelectedOrder(null);
    
    try {
      // Mock API call - replace with actual fetch
      const order = orders.find(o => o.orderId === orderId);
      setTimeout(() => {
        setSelectedOrder(order);
        setDetailsLoading(false);
      }, 300);
    } catch (err) {
      setDetailsError(err.message);
      setDetailsLoading(false);
    }
  };

  const closeOrderDetails = () => {
    setSelectedOrder(null);
    setDetailsError(null);
  };

  const dismissNotification = (notificationId) => {
    setNotifications(prev => prev.filter(n => n.id !== notificationId));
  };

  // Role-based filtering
  const getFilteredOrders = () => {
    let filtered = orders;

    if (user.role === 'Kitchen Staff') {
      filtered = filtered.filter(order => 
        ['pending', 'in_preparation'].includes(order.status)
      );
    } else if (user.role === 'Waiter') {
      filtered = filtered.filter(order => 
        ['ready', 'delivered'].includes(order.status)
      );
    }

    filtered = filtered.filter(order => {
      const statusMatch = filterStatus === 'all' || order.status === filterStatus;
      const tableMatch = filterTable.trim() === '' || 
        (order.tableId && order.tableId.toLowerCase().includes(filterTable.trim().toLowerCase()));
      return statusMatch && tableMatch;
    });

    return filtered;
  };

  const getAvailableStatusOptions = (currentStatus) => {
    const allStatuses = statusOptions.filter(s => s !== 'all');
    
    if (user.role === 'Kitchen Staff') {
      return allStatuses.filter(s => ['pending', 'in_preparation', 'ready'].includes(s));
    } else if (user.role === 'Waiter') {
      return allStatuses.filter(s => ['ready', 'delivered'].includes(s));
    }
    
    return allStatuses;
  };

  const filteredOrders = getFilteredOrders();

  return (
    <div className="container-fluid mt-3">
      {/* Header with connection status */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Staff Dashboard - {user.role}</h2>
        <div className="d-flex align-items-center">
          <span className={`badge me-3 ${connectionStatus === 'connected' ? 'bg-success' : 'bg-danger'}`}>
            {connectionStatus === 'connected' ? '🟢 Live Updates' : '🔴 Disconnected'}
          </span>
          <button 
            className="btn btn-outline-primary btn-sm" 
            onClick={fetchOrders}
            disabled={loading}
          >
            {loading ? 'Loading...' : 'Refresh'}
          </button>
        </div>
      </div>

      {/* Notifications */}
      {notifications.length > 0 && (
        <div className="position-fixed top-0 end-0 p-3" style={{ zIndex: 1050 }}>
          {notifications.map(notification => (
            <div 
              key={notification.id}
              className={`alert alert-${getNotificationClass(notification.type)} alert-dismissible fade show`}
              role="alert"
            >
              <strong>{notification.message}</strong>
              <button 
                type="button" 
                className="btn-close" 
                onClick={() => dismissNotification(notification.id)}
              ></button>
            </div>
          ))}
        </div>
      )}

      {/* Filter Controls */}
      <div className="row mb-3">
        <div className="col-md-3 mb-2">
          <label className="form-label">Filter by Status</label>
          <select 
            className="form-select" 
            value={filterStatus} 
            onChange={e => setFilterStatus(e.target.value)}
          >
            {statusOptions.map(status => (
              <option key={status} value={status}>
                {status.charAt(0).toUpperCase() + status.slice(1).replace('_', ' ')}
              </option>
            ))}
          </select>
        </div>
        <div className="col-md-3 mb-2">
          <label className="form-label">Filter by Table</label>
          <input
            type="text"
            className="form-control"
            placeholder="Enter table number or ID"
            value={filterTable}
            onChange={e => setFilterTable(e.target.value)}
          />
        </div>
        <div className="col-md-6 mb-2 d-flex align-items-end">
          <div className="me-3">
            <span className="badge bg-primary me-2">{filteredOrders.length}</span>
            Orders displayed
          </div>
        </div>
      </div>

      {loading && <div className="text-center">Loading orders...</div>}
      {error && <div className="alert alert-danger">{error}</div>}
      
      {!loading && !error && (
        <div className="table-responsive">
          <table className="table table-bordered table-hover">
            <thead className="table-dark">
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
              {filteredOrders.length === 0 ? (
                <tr><td colSpan="6" className="text-center">No orders found.</td></tr>
              ) : (
                filteredOrders.map(order => (
                  <tr key={order.orderId} className={getRowClass(order.status)}>
                    <td><strong>#{order.orderId}</strong></td>
                    <td>Table {order.tableId}</td>
                    <td>
                      <select
                        value={order.status}
                        onChange={e => handleStatusChange(order.orderId, e.target.value)}
                        disabled={updatingOrderId === order.orderId}
                        className={`form-select form-select-sm ${getStatusClass(order.status)}`}
                      >
                        {getAvailableStatusOptions(order.status).map(status => (
                          <option key={status} value={status}>
                            {status.replace('_', ' ')}
                          </option>
                        ))}
                      </select>
                    </td>
                    <td>{new Date(order.orderTime).toLocaleString()}</td>
                    <td><strong>${order.totalAmount}</strong></td>
                    <td>
                      <button 
                        className="btn btn-info btn-sm me-2" 
                        onClick={() => openOrderDetails(order.orderId)}
                      >
                        View Details
                      </button>
                      {updatingOrderId === order.orderId && (
                        <span className="spinner-border spinner-border-sm text-primary"></span>
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
                <h5 className="modal-title">Order Details (ID: #{selectedOrder.orderId})</h5>
                <button type="button" className="btn-close" onClick={closeOrderDetails}></button>
              </div>
              <div className="modal-body">
                {detailsLoading ? (
                  <div className="text-center">
                    <div className="spinner-border" role="status"></div>
                    <div>Loading...</div>
                  </div>
                ) : detailsError ? (
                  <div className="alert alert-danger">{detailsError}</div>
                ) : (
                  <>
                    <div className="row mb-3">
                      <div className="col-md-6">
                        <p><strong>Table:</strong> {selectedOrder.tableId}</p>
                        <p><strong>Status:</strong> 
                          <span className={`badge bg-${getStatusColor(selectedOrder.status)} ms-2`}>
                            {selectedOrder.status.replace('_', ' ')}
                          </span>
                        </p>
                      </div>
                      <div className="col-md-6">
                        <p><strong>Order Time:</strong> {new Date(selectedOrder.orderTime).toLocaleString()}</p>
                        <p><strong>Total Amount:</strong> <span className="h5 text-success">${selectedOrder.totalAmount}</span></p>
                      </div>
                    </div>
                    
                    <h6>Order Items</h6>
                    <div className="table-responsive">
                      <table className="table table-sm">
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
                              <td><span className="badge bg-secondary">{item.quantity}</span></td>
                              <td>{item.menuItem ? `$${item.menuItem.price}` : "-"}</td>
                              <td><strong>{item.menuItem ? `$${(item.quantity * item.menuItem.price).toFixed(2)}` : "-"}</strong></td>
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

// Helper functions
function getStatusColor(status) {
  switch (status.toLowerCase()) {
    case 'pending': return 'warning';
    case 'in_preparation': return 'info';
    case 'ready': return 'success';
    case 'delivered': return 'primary';
    default: return 'secondary';
  }
}

function getStatusClass(status) {
  return `border-${getStatusColor(status)}`;
}

function getRowClass(status) {
  switch (status.toLowerCase()) {
    case 'pending': return 'table-warning';
    case 'ready': return 'table-success';
    default: return '';
  }
}

function getNotificationClass(type) {
  switch (type) {
    case 'success': return 'success';
    case 'error': return 'danger';
    case 'warning': return 'warning';
    default: return 'info';
  }
}

export default StaffDashboard;