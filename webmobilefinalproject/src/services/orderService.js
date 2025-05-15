import api from './api';

export const createOrder = (tableId, orderItems) => api.post('/orders/create', { tableId, items: orderItems });
export const getOrderStatus = (orderId) => api.get(`/orders/status/${orderId}`);
export const updateOrderStatus = (orderId, status) => api.put(`/orders/${orderId}/status`, { status });