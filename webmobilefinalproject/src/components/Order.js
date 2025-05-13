import ReactPaginate from 'react-paginate';
import { useState, useEffect } from 'react';
import { useAuth } from "../context/AuthContext";

const Order = () => {
    const [items, setItems] = useState([]);
    const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
    const [currentPage, setCurrentPage] = useState(1);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const [hasAccess, setHasAccess] = useState(false);
    const [pageCount, setPageCount] = useState(0);
    const itemsPerPage = 6;
    const { user } = useAuth();

    // Detect screen size changes
    useEffect(() => {
        const handleResize = () => setIsMobile(window.innerWidth < 768);
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    useEffect(() => {
        setCurrentPage(1);
    }, [selectedCategory]);

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const tableId = params.get("table_id");

        if (user || tableId) {
            setHasAccess(true);
            fetchData();
        } else {
            setHasAccess(false);
        }
    }, [user, currentPage, selectedCategory]);

    // Fetch products
    const fetchData = async () => {
        try {
            const categoryQuery = selectedCategory === "All" ? "" : `&category=${selectedCategory}`;
            const res = await fetch(`http://localhost:3009/products?_page=${currentPage}&_limit=${itemsPerPage}${categoryQuery}`);
            const data = await res.json();
            const totalItems = res.headers.get("X-Total-Count") || data.length;
            setItems(data);
            setPageCount(Math.ceil(totalItems / itemsPerPage));
        } catch (err) {
            console.error('Failed to fetch products:', err);
        }
    };

    const handlePageClick = (data) => {
        setCurrentPage(data.selected + 1);
    };

    return (
        <>
            {/* QR Code & Message */}
            <div className='row QR'>
                <div className="col-sm-9 col-md-6 my-2 qrtext">
                    <h2 className='fw-bold'>Scan QR code on your desk or order online</h2>
                    <p className="text-muted">
                        Scan the QR so we know where to send your order, or log in to order for delivery.
                    </p>
                    <img id='QRCode' src='./images/frame.png' alt='qr code' />
                    <p className="text-muted">
                        Some products may only be purchased after age verification requiring sign-in.
                    </p>
                </div>
                <div className="col-sm-3 col-md-6 text-end floatingmiku">
                    <img src='./images/UnderwaterMiku.png' alt='WideCutePic' className='widepic' />
                </div>
            </div>

            {/* Category Filter Menu */}
            <nav className="menunavbar">
                <ul className="d-flex list-unstyled gap-3 justify-content-center">
                    {['All', 'Main Course', 'Drinks', 'Special', 'Alcohol'].map(category => (
                        <li key={category}>
                            <button
                                onClick={() => setSelectedCategory(category)}
                                className={`btn btn-link ${selectedCategory === category ? 'fw-bold text-decoration-underline' : ''}`}
                            >
                                {category}
                            </button>
                        </li>
                    ))}
                </ul>
                <div className='cart d-flex align-items-center justify-content-end pe-3'>
                    <i className="bi bi-cart4 fs-4"></i>
                    <span className='ms-2'>0 items - $0.00</span>
                </div>
            </nav>

            {/* Access Control */}
            {!hasAccess ? (
                <>
                    {/* Product Cards */}
                    <div className="row my-4 products">
                        {items.map((item) => (
                            <div className={`col-sm-12 ${isMobile ? '' : 'col-md-6'} my-2`} key={item.id}>
                                <div className="card shadow-sm w-100">
                                    <img src={item.image} className="card-img-top" alt={item.name || "menu item"} />
                                    <div className="card-body">
                                        <h5>{item.name}</h5>
                                        <p>{item.description}</p>
                                        <p><strong>Price:</strong> ${item.price}</p>
                                        <p><strong>In stock:</strong> {item.stock}</p>
                                        <button className="btn btn-miku">ADD TO CART</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* Pagination */}
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
                        forcePage={currentPage - 1}
                    />
                </>
            ) : (
                <>
                    {/* Product Cards */}
                    <div className="row my-4 products">
                        {items.map((item) => (
                            <div className={`col-sm-12 ${isMobile ? '' : 'col-md-6'} my-2`} key={item.id}>
                                <div className="card shadow-sm w-100">
                                    <img src={item.image} className="card-img-top" alt={item.name || "menu item"} />
                                    <div className="card-body">
                                        <h5>{item.name}</h5>
                                        <p>{item.description}</p>
                                        <p><strong>Price:</strong> ${item.price}</p>
                                        <p><strong>In stock:</strong> {item.stock}</p>
                                        <button className="btn btn-miku">ADD TO CART</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* Pagination */}
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
                        forcePage={currentPage - 1}
                    />
                </>
            )}
        </>
    );
};

export default Order;
