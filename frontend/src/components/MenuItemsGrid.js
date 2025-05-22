import React, { useState, useEffect } from 'react';
import MenuItemCard from './MenuItemCard';
import './MenuItemsGrid.css';
import ReactPaginate from 'react-paginate';

const MenuItemsGrid = ({
                           items,
                           onAddToCart,
                           currentPage,
                           setCurrentPage,
                           itemsPerPage,
                           isMobile,
                           isLoading = false
                       }) => {
    const [isAnimating, setIsAnimating] = useState(false);
    const [displayItems, setDisplayItems] = useState([]);

    // Calculate pagination
    const offset = currentPage * itemsPerPage;
    const currentItems = items.slice(offset, offset + itemsPerPage);
    const pageCount = Math.ceil(items.length / itemsPerPage);

    // Handle smooth transition when items change
    useEffect(() => {
        if (isLoading) return;

        setIsAnimating(true);

        // Short delay to trigger fade out
        const fadeOutTimer = setTimeout(() => {
            setDisplayItems(currentItems);
            setIsAnimating(false);
        }, 150);

        return () => clearTimeout(fadeOutTimer);
    }, [items, currentPage, itemsPerPage, isLoading]);

    // Initialize display items
    useEffect(() => {
        if (!isLoading && items.length > 0) {
            setDisplayItems(currentItems);
        }
    }, []);

    const handlePageClick = (data) => {
        setCurrentPage(data.selected);
        // Scroll to top of grid
        const gridElement = document.getElementById('menu-items-grid');
        if (gridElement) {
            window.scrollTo({
                top: gridElement.offsetTop - 100,
                behavior: 'smooth'
            });
        }
    };

    return (
        <div className="menu-items-container">
            <div
                id="menu-items-grid"
                className={`menu-items-grid ${isAnimating ? 'fading' : ''}`}
            >
                {!isLoading && displayItems.length > 0 ? (
                    displayItems.map((item, index) => (
                        <div
                            key={`${item.id}-${currentPage}`}
                            className="menu-item-wrapper"
                        >
                            <MenuItemCard
                                item={item}
                                onAddToCart={onAddToCart}
                                isMobile={isMobile}
                            />
                        </div>
                    ))
                ) : !isLoading ? (
                    <div className="no-items-message">
                        <p>No items found in this category.</p>
                    </div>
                ) : null}
            </div>

            {isLoading && (
                <div className="loading-container">
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            )}

            {!isLoading && pageCount > 1 && (
                <ReactPaginate
                    previousLabel={'<'}
                    nextLabel={'>'}
                    breakLabel={'...'}
                    pageCount={pageCount}
                    marginPagesDisplayed={1}
                    pageRangeDisplayed={3}
                    onPageChange={handlePageClick}
                    containerClassName='pagination justify-content-center'
                    pageClassName='page-item'
                    pageLinkClassName='page-link'
                    previousClassName='page-item'
                    previousLinkClassName='page-link'
                    nextClassName='page-item'
                    nextLinkClassName='page-link'
                    breakClassName='page-item'
                    breakLinkClassName='page-link'
                    forcePage={currentPage}
                    activeClassName='selected'
                />
            )}
        </div>
    );
};

export default MenuItemsGrid;