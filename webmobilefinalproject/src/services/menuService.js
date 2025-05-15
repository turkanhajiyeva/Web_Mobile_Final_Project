import api from './api';

export const getMenuItems = () => api.get('/menu');
export const getMenuItemsByCategory = (category) => api.get(`/menu/category/${category}`);