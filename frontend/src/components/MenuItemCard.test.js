import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import MenuItemCard from './MenuItemCard';

describe('MenuItemCard', () => {
  const mockItem = {
    name: 'Test Pizza',
    description: 'Tasty cheese pizza',
    price: 12.5,
    category: 'Main Course',
    image: ''
  };

  it('renders item details correctly', () => {
    render(<MenuItemCard item={mockItem} onAddToCart={() => {}} />);
    expect(screen.getByText('Test Pizza')).toBeInTheDocument();
    expect(screen.getByText('Tasty cheese pizza')).toBeInTheDocument();
    expect(screen.getByText('Main Course')).toBeInTheDocument();
    expect(screen.getByText('$12.50')).toBeInTheDocument();
  });

  it('calls onAddToCart when button is clicked', () => {
    const mockAdd = jest.fn();
    render(<MenuItemCard item={mockItem} onAddToCart={mockAdd} />);
    fireEvent.click(screen.getByRole('button'));
    expect(mockAdd).toHaveBeenCalledWith(mockItem);
  });
});
