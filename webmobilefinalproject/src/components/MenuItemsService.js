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
    }
};

export default MenuItemsService;