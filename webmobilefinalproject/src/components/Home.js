import ReactPaginate from 'react-paginate';
import { Carousel,Dropdown } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import { useAuth } from "../context/AuthContext";

const Home = () => {
  const [items, setItems] = useState([]);
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
  const [currentPage, setCurrentPage] = useState(0);
  const [selectedCategory, setSelectedCategory] = useState("All");
  const itemsPerPage = 12;
  const { user } = useAuth();

  // Cart state inside Home
  const [cartItems, setCartItems] = useState([]);

  const addToCart = (item) => {
    setCartItems(prevItems => {
      const existing = prevItems.find(i => i.id === item.id);
      if (existing) {
        return prevItems.map(i =>
          i.id === item.id ? { ...i, quantity: i.quantity + 1 } : i
        );
      }
      return [...prevItems, { ...item, quantity: 1 }];
    });
  };

  const removeFromCart = (id) => {
    setCartItems(prevItems => prevItems.filter(i => i.id !== id));
  };

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const fetchData = async (category) => {
    try {
      const categoryPath = category === 'All' ? '' : encodeURIComponent(category);
      const url = categoryPath 
          ? `http://localhost:8080/api/menuitems/category/${categoryPath}` 
          : 'http://localhost:8080/api/menuitems';
      const res = await fetch(url);
      const data = await res.json();
      setItems(data);
      setCurrentPage(0); // reset page when category changes
    } catch (err) {
      console.error('Failed to fetch products:', err);
    }
  };

  useEffect(() => {
    fetchData(selectedCategory);
    const handleResize = () => setIsMobile(window.innerWidth < 768);
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, [selectedCategory]);

  const handlePageClick = (data) => {
    setCurrentPage(data.selected);
  };

  const offset = currentPage * itemsPerPage;
  const currentItems = items.slice(offset, offset + itemsPerPage);
  const pageCount = Math.ceil(items.length / itemsPerPage);

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
            <div class="divline">
                <div class="divlinein">
                    <hr></hr>
                </div>
            </div>
            
            <div className="menudiv">
            </div>
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
            <nav className="menunavbar">
                <ul className="d-flex list-unstyled gap-3 justify-content-center">
                    {['All', 'Main Courses', 'Drinks', 'Appetizers'].map(category => (
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
            </nav>
            {/* Cart */}

            <Dropdown>
            <Dropdown.Toggle variant="primary" id="dropdown-basic">
                My Cart: ${totalPrice.toFixed(2)}
            </Dropdown.Toggle>

            <Dropdown.Menu>
                {cartItems.length === 0 && <Dropdown.ItemText>Your cart is empty.</Dropdown.ItemText>}
                {cartItems.map(item => (
                <Dropdown.Item key={item.id} as="div" className="d-flex justify-content-between align-items-center">
                    <div>
                    <strong>{item.name}</strong><br />
                    Qty: {item.quantity}<br />
                    Price: ${(item.price * item.quantity).toFixed(2)}
                    </div>
                    <button
                    className="btn btn-sm btn-danger"
                    onClick={() => removeFromCart(item.id)}
                    >
                    Remove
                    </button>
                </Dropdown.Item>
                ))}
            </Dropdown.Menu>
            </Dropdown>

            {/* Display paginated items */}
            <div className="container my-4">
                <h2 className="mb-4 text-center">Menu</h2>
                <div className="row">
                    {currentItems.map(item => (
                        <div key={item.id} className="col-md-4 mb-4">
                            <div className="card h-100">
                                <img
                                    src={item.image || "./images/placeholder.png"}
                                    className="card-img-top"
                                    alt={item.name}
                                    style={{ height: "200px", objectFit: "cover" }}
                                />
                                <div className="card-body d-flex flex-column">
                                    <h5 className="card-title">{item.name}</h5>
                                    <p className="card-text">{item.description}</p>
                                    <div className="mt-auto">
                                        <p className="fw-bold">${item.price.toFixed(2)}</p>
                                        <button className="btn btn-miku w-100" onClick={() => addToCart(item)}>Add to Cart</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

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
            />
        </>
    );
};

export default Home;