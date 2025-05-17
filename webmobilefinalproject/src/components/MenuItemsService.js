const API_BASE_URL = 'http://localhost:8080/api';

export const MenuItemsService = {
    // Fetch all menu items
    getAllMenuItems: async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/menuitems`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching all menu items:', error);
            throw error;
        }
    },

    // Fetch menu items by category
    getMenuItemsByCategory: async (category) => {
        try {
            const categoryPath = encodeURIComponent(category);
            const response = await fetch(`${API_BASE_URL}/menuitems/category/${categoryPath}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching menu items for category ${category}:`, error);
            throw error;
        }
    },

    // Fetch a single menu item by ID
    getMenuItemById: async (id) => {
        try {
            const response = await fetch(`${API_BASE_URL}/menuitems/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching menu item with ID ${id}:`, error);
            throw error;
        }
    },

    // Create a new menu item
    createMenuItem: async (menuItemData) => {
        try {
            const response = await fetch(`${API_BASE_URL}/menuitems`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(menuItemData)
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error creating menu item:', error);
            throw error;
        }
    },

    // Update a menu item
    updateMenuItem: async (id, menuItemData) => {
        try {
            const response = await fetch(`${API_BASE_URL}/menuitems/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(menuItemData)
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error updating menu item with ID ${id}:`, error);
            throw error;
        }
    },

    // Delete a menu item
    deleteMenuItem: async (id) => {
        try {
            const response = await fetch(`${API_BASE_URL}/menuitems/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return true;
        } catch (error) {
            console.error(`Error deleting menu item with ID ${id}:`, error);
            throw error;
        }
    }
};

export default MenuItemsService;