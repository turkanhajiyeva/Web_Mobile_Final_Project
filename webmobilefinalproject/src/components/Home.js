import ReactPaginate from 'react-paginate';
import { Carousel } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import { useAuth } from "../context/AuthContext";

const Home = () => {
    const [items, setItems] = useState([]);
    const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
    const [currentPage, setCurrentPage] = useState(1);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const itemsPerPage = 6;
    const { user } = useAuth();

    const fetchData = async (page) => {
        try {
            const res = await fetch(`http://localhost:3009/products?_page=${page}&_limit=${itemsPerPage}`);
            const data = await res.json();
            setItems(data);
        } catch (err) {
            console.error('Failed to fetch products:', err);
        }
    };

    useEffect(() => {
        fetchData(currentPage);
        const handleResize = () => setIsMobile(window.innerWidth < 768);
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, [currentPage]);

    const handlePageClick = (data) => {
        const selectedPage = data.selected + 1;
        setCurrentPage(selectedPage);
    };

    const filteredItems = selectedCategory === 'All'
        ? items
        : items.filter(item => item.category === selectedCategory);

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
            </nav>

            {/* Filtered Products */}
            <div className="row my-4 products">
                {filteredItems.map((item) => (
                    <div className="col-sm-12 col-md-6 my-2" key={item.id}>
                        <div className="card shadow-sm w-100">
                            <img src={item.image} className="card-img-top" alt={item.name} />
                            <div className="card-body">
                                <h5>{item.name}</h5>
                                <p>{item.description}</p>
                                <p><strong>Price:</strong> ${item.price}</p>
                                <p><strong>In stock:</strong> {item.stock}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            <ReactPaginate
                previousLabel={'<'}
                nextLabel={'>'}
                breakLabel={'...'}
                pageCount={3}
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
            />
        </>
    );
};

export default Home;
