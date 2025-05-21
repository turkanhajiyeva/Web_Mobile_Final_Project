import { Carousel, Dropdown } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import { useAuth } from "../context/AuthContext";
import { useCart } from "../context/CartContext";
import MenuItemsGrid from './MenuItemsGrid';
import MenuItemsService from './MenuItemsService';
import Cart from './Cart';
import './Home.css';

const Home = () => {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
    const [currentPage, setCurrentPage] = useState(0);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const [showCart, setShowCart] = useState(false);
    const itemsPerPage = 12;
    const { user } = useAuth();
    const { cartItems, total, addToCart, removeFromCart } = useCart();

    const fetchData = async () => {
        setLoading(true);
        try {
            let data;
            if (selectedCategory === 'All') {
                data = await MenuItemsService.getAllMenuItems();
            } else {
                data = await MenuItemsService.getMenuItemsByCategory(selectedCategory);
            }
            setItems(data);
            setCurrentPage(0); // reset page when category changes
            setError(null);
        } catch (err) {
            console.error('Failed to fetch menu items:', err);
            setError('Failed to load menu items. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
        const handleResize = () => setIsMobile(window.innerWidth < 768);
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, [selectedCategory]);

    return (
        <>
            <h3 id="NewsHeading">IL'PALAZZO PIZZERIA HOT NEWS:</h3>

            <Carousel className="carousel-custom">
                <Carousel.Item>
                    <div className="d-flex align-items-center carousel-custom-height" style={{ backgroundColor: '#fff' }}>
                        <div className="col-md-6">
                            <img
                                className="d-block w-100"
                                src={isMobile ? './images/MikuPizza.jpg' : './images/MikuPizzaPC.png'}
                                alt="Miku Pizza"
                            />
                        </div>
                        <div className="col-md-6 p-4">
                            <h2 className="fw-bold">Hatsune Miku orders food from «IL'PALAZZO»</h2>
                            <p className="text-muted">
                                Order 2 «Arizona Hot Pizza» and get a free Hatsune Miku badge.
                            </p>
                            <button className="btn btn-miku">Order</button>
                        </div>
                    </div>
                </Carousel.Item>
                <Carousel.Item>
                    <div className="d-flex align-items-center carousel-custom-height" style={{ backgroundColor: '#fff' }}>
                        <div className="col-md-6">
                            <img className="d-block w-100" src="./images/horsin_around.jpg" alt="Horse chief?" />
                        </div>
                        <div className="col-md-6 p-4">
                            <h2 className="fw-bold">Our chef is... A HORSE?</h2>
                            <p className="text-muted">
                                If you assume we have to investigate if this rumor is true click below.
                            </p>
                            <button className="btn btn-miku">Details</button>
                        </div>
                    </div>
                </Carousel.Item>
                <Carousel.Item>
                    <div className="d-flex align-items-center carousel-custom-height" style={{ backgroundColor: '#fff' }}>
                        <div className="col-md-6">
                            <img className="d-block w-100" src={isMobile ? './images/kekw.jpg' : './images/Bocchi.jpg'} alt="Miku Pizza" />
                        </div>
                        <div className="col-md-6 p-4">
                            <h2 className="fw-bold">OFFICIAL STATEMENT: We don't support gender equality</h2>
                            <p className="text-muted">
                                Unless you buy crispy cream donuts for $2.99.
                            </p>
                            <button className="btn btn-miku">Add to Cart</button>
                        </div>
                    </div>
                </Carousel.Item>
            </Carousel>

            {/* Division Line */}
            <div className="divline">
                <div className="divlinein">
                    <hr />
                </div>
            </div>

            <div className="menudiv"></div>

            {/* Cute Picture */}
            <div className='row QR'>
                <div className="col-sm-9 col-md-8 my-2 qrtext">
                    <h2 className='fw-bold'>Scan QR code on your desk or order online</h2>
                    <p className="text-muted">
                        Scan QR so we could know where to order your order, or just log in to our website so we can deliver food to your doorstep.
                    </p>
                    <img id='QRCode' src='./images/frame.png' alt='qr code'></img>
                    <p className="text-muted">
                        Some products may only be purchased after age verification that requires signing in
                    </p>
                </div>
                <div className="col-sm-3 col-md-4 text-end floatingmiku">
                    <img src='./images/UnderwaterMiku.png' alt='WideCutePic' className='widepic'></img>
                </div>
            </div>

            {/* Category Filter Menu */}
            <div className="menu-section">
                <h2 className="menu-title">Our Menu</h2>

                <nav className="menunavbar">
                    <ul className="d-flex list-unstyled gap-3 justify-content-center">
                        {['All', 'Main Courses', 'Drinks', 'Appetizers'].map(category => (
                            <li key={category}>
                                <button
                                    onClick={() => setSelectedCategory(category)}
                                    className={`btn btn-link ${selectedCategory === category ? 'active-category' : ''}`}
                                >
                                    {category}
                                </button>
                            </li>
                        ))}
                    </ul>
                    <div 
                        className='cart d-flex align-items-center justify-content-end pe-3'
                        onClick={() => setShowCart(!showCart)}
                        style={{ cursor: 'pointer' }}
                    >
                        <i className="bi bi-cart4 fs-4"></i>
                        <span className='ms-2'>{cartItems.length} items - ${total.toFixed(2)}</span>
                    </div>
                </nav>

                {/* Cart Sidebar */}
                {showCart && (
                    <>
                        <div className="cart-overlay" onClick={() => setShowCart(false)} />
                        <div className="cart-sidebar">
                            <div className="d-flex justify-content-between align-items-center mb-3">
                                <h3>Your Cart</h3>
                                <button 
                                    className="btn btn-link" 
                                    onClick={() => setShowCart(false)}
                                >
                                    <i className="bi bi-x-lg"></i>
                                </button>
                            </div>
                            <Cart />
                        </div>
                    </>
                )}

                {/* Loading and Error States */}
                {loading && (
                    <div className="loading-container">
                        <div className="spinner-border text-primary" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                    </div>
                )}

                {error && (
                    <div className="alert alert-danger" role="alert">
                        {error}
                    </div>
                )}

                {/* Menu Items Grid */}
                {!loading && !error && (
                    <MenuItemsGrid 
                        items={items} 
                        isMobile={isMobile}
                        onAddToCart={addToCart}
                        currentPage={currentPage}
                        setCurrentPage={setCurrentPage}
                        itemsPerPage={itemsPerPage}
                    />
                )}
            </div>
        </>
    );
};

export default Home;