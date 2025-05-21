import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const STAFF_ROLES = ['Manager', 'Waiter', 'Kitchen Staff'];

const initialForm = { name: '', description: '', price: '', category: '' };

const MenuManagement = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [menuItems, setMenuItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);
  const [formError, setFormError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (!user || !STAFF_ROLES.includes(user.role)) {
      navigate('/login');
      return;
    }
    fetchMenuItems();
    // eslint-disable-next-line
  }, [user]);

  const fetchMenuItems = async () => {
    setLoading(true);
    try {
      const response = await fetch('http://localhost:8080/api/menuitems');
      if (!response.ok) throw new Error('Failed to fetch menu items');
      const data = await response.json();
      setMenuItems(data);
      setError(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleAdd = () => {
    setForm(initialForm);
    setEditingId('new');
    setFormError('');
  };

  const handleEdit = (item) => {
    setForm({
      name: item.name,
      description: item.description,
      price: item.price,
      category: item.category
    });
    setEditingId(item.id);
    setFormError('');
  };

  const handleCancel = () => {
    setEditingId(null);
    setForm(initialForm);
    setFormError('');
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this menu item?')) return;
    setSubmitting(true);
    try {
      const response = await fetch(`http://localhost:8080/api/menuitems/${id}`, {
        method: 'DELETE'
      });
      if (!response.ok) throw new Error('Failed to delete menu item');
      setMenuItems(menuItems.filter(item => item.id !== id));
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    if (!form.name || !form.price || !form.category) {
      setFormError('Name, price, and category are required.');
      return;
    }
    setSubmitting(true);
    try {
      let response, data;
      if (editingId === 'new') {
        response = await fetch('http://localhost:8080/api/menuitems', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: form.name,
            description: form.description,
            price: parseFloat(form.price),
            category: form.category
          })
        });
        if (!response.ok) throw new Error('Failed to add menu item');
        data = await response.json();
        setMenuItems([...menuItems, data]);
      } else {
        response = await fetch(`http://localhost:8080/api/menuitems/${editingId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            name: form.name,
            description: form.description,
            price: parseFloat(form.price),
            category: form.category
          })
        });
        if (!response.ok) throw new Error('Failed to update menu item');
        data = await response.json();
        setMenuItems(menuItems.map(item => item.id === editingId ? data : item));
      }
      setEditingId(null);
      setForm(initialForm);
      setFormError('');
    } catch (err) {
      setFormError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Menu Management</h2>
      {loading && <div>Loading menu items...</div>}
      {error && <div className="alert alert-danger">{error}</div>}
      {!loading && !error && (
        <div className="table-responsive">
          <table className="table table-bordered table-hover">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Category</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {menuItems.length === 0 ? (
                <tr><td colSpan="6" className="text-center">No menu items found.</td></tr>
              ) : (
                menuItems.map(item => (
                  editingId === item.id ? (
                    <tr key={item.id} className="table-warning">
                      <td>{item.id}</td>
                      <td colSpan={5}>
                        <form className="row g-2 align-items-center" onSubmit={handleFormSubmit}>
                          <div className="col-md-2">
                            <input type="text" name="name" className="form-control" placeholder="Name" value={form.name} onChange={handleInputChange} required />
                          </div>
                          <div className="col-md-3">
                            <input type="text" name="description" className="form-control" placeholder="Description" value={form.description} onChange={handleInputChange} />
                          </div>
                          <div className="col-md-2">
                            <input type="number" name="price" className="form-control" placeholder="Price" value={form.price} onChange={handleInputChange} required min="0" step="0.01" />
                          </div>
                          <div className="col-md-2">
                            <input type="text" name="category" className="form-control" placeholder="Category" value={form.category} onChange={handleInputChange} required />
                          </div>
                          <div className="col-md-3">
                            <button type="submit" className="btn btn-success btn-sm me-2" disabled={submitting}>Save</button>
                            <button type="button" className="btn btn-secondary btn-sm" onClick={handleCancel} disabled={submitting}>Cancel</button>
                          </div>
                        </form>
                        {formError && <div className="text-danger small mt-1">{formError}</div>}
                      </td>
                    </tr>
                  ) : (
                    <tr key={item.id}>
                      <td>{item.id}</td>
                      <td>{item.name}</td>
                      <td>{item.description}</td>
                      <td>${parseFloat(item.price).toFixed(2)}</td>
                      <td>{item.category}</td>
                      <td>
                        <button className="btn btn-sm btn-warning me-2" onClick={() => handleEdit(item)} disabled={submitting}>Edit</button>
                        <button className="btn btn-sm btn-danger" onClick={() => handleDelete(item.id)} disabled={submitting}>Delete</button>
                      </td>
                    </tr>
                  )
                ))
              )}
              {editingId === 'new' && (
                <tr className="table-info">
                  <td>New</td>
                  <td colSpan={5}>
                    <form className="row g-2 align-items-center" onSubmit={handleFormSubmit}>
                      <div className="col-md-2">
                        <input type="text" name="name" className="form-control" placeholder="Name" value={form.name} onChange={handleInputChange} required />
                      </div>
                      <div className="col-md-3">
                        <input type="text" name="description" className="form-control" placeholder="Description" value={form.description} onChange={handleInputChange} />
                      </div>
                      <div className="col-md-2">
                        <input type="number" name="price" className="form-control" placeholder="Price" value={form.price} onChange={handleInputChange} required min="0" step="0.01" />
                      </div>
                      <div className="col-md-2">
                        <input type="text" name="category" className="form-control" placeholder="Category" value={form.category} onChange={handleInputChange} required />
                      </div>
                      <div className="col-md-3">
                        <button type="submit" className="btn btn-success btn-sm me-2" disabled={submitting}>Add</button>
                        <button type="button" className="btn btn-secondary btn-sm" onClick={handleCancel} disabled={submitting}>Cancel</button>
                      </div>
                    </form>
                    {formError && <div className="text-danger small mt-1">{formError}</div>}
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
      <div className="mt-4">
        <button className="btn btn-miku" onClick={handleAdd} disabled={editingId !== null || submitting}>Add New Menu Item</button>
      </div>
    </div>
  );
};

export default MenuManagement; 