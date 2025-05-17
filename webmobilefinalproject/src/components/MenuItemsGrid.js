import React from 'react';
import MenuItemCard from './MenuItemCard';
import './MenuItemsGrid.css';
import ReactPaginate from 'react-paginate';

const MenuItemsGrid = ({
                           items,
                           onAddToCart,
                           currentPage,
                           setCurrentPage,
                           itemsPerPage
                       }) => {
    // Calculate pagination
    const offset = currentPage * itemsPerPage;
    const currentItems = items.slice(offset, offset + itemsPerPage);
    const pageCount = Math.ceil(items.length / itemsPerPage);

    const handlePageClick = (data) => {
        setCurrentPage(data.selected);
        // Scroll to top of grid
        window.scrollTo({ top: document.getElementById('menu-items-grid').offsetTop - 100, behavior: 'smooth' });
    };

    return (
        <div className="menu-items-container">
            <div id="menu-items-grid" className="menu-items-grid">
                {currentItems.length > 0 ? (
                    currentItems.map(item => (
                        <div key={item.id} className="menu-item-wrapper">
                            <MenuItemCard item={item} onAddToCart={onAddToCart} />
                        </div>
                    ))
                ) : (
                    <div className="no-items-message">
                        <p>No items found in this category.</p>
                    </div>
                )}
            </div>

            {pageCount > 1 && (
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