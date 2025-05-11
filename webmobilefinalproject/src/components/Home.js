import ReactPaginate from 'react-paginate'
import { Carousel } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import { useAuth } from "../context/AuthContext";


const Home = () => {

    const[items,setItems]=useState([])
    const [isMobile, setIsMobile] = useState(window.innerWidth < 768);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const { user } = useAuth();

    const fetchData = async (page) => {
    try {
        const res = await fetch(`http://localhost:3009/products?_page=${page}&_limit=${itemsPerPage}`);
        console.log(`http://localhost:3009/products?_page=${page}&_limit=${itemsPerPage}`)
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

    const handlePageClick = async (data) => {
        console.log(data.selected)
    const selectedPage = data.selected + 1;
    setCurrentPage(selectedPage);
  };

  return (
    <>
        
        <h3 id="NewsHeading" class=''>IL'PALAZZO PIZZERIA HOT NEWS:</h3>

        <Carousel class='carousel-custom'>
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
                        Order 2 «Arizona Hot Pizza» and get a free Hatsune Miku badge. I know you want that Miku badge you indoor slut.
                    </p>
                    <button className="btn btn-miku">Order</button>
                    </div>
                </div>
            </Carousel.Item>
            <Carousel.Item>
                <div className="d-flex align-items-center carousel-custom-height" style={{ backgroundColor: '#fff' }}>
                    <div className="col-md-6">
                    <img
                        className="d-block w-100"
                        src="./images/horsin_around.jpg"
                        alt="Horse chief?"
                    />
                    </div>
                    <div className="col-md-6 p-4">
                    <h2 className="fw-bold"> Our chef is.... A HORSE?</h2>
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
                    <img
                        className="d-block w-100"
                        src={isMobile ? './images/MikuPizza.jpg' : './images/Bocchi.jpg'}
                        alt="Miku Pizza"
                    />
                    </div>
                    <div className="col-md-6 p-4">
                    <h2 className="fw-bold">OFFICIAL STATEMENT: Our company doesn't support gender equality</h2>
                    <p className="text-muted">
                        unless you buy crispy cream donuts for 2.99$.
                    </p>
                    <button className="btn btn-miku">Add to Cart</button>
                    </div>
                </div>
            </Carousel.Item>
        </Carousel>

        {/* orders and stuff */}
        <div className="row my-4 products">
            {items.map((item) => (
            <div className="col-sm-6 col-md-4 my-2" key={item.id}>
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
            pageCount={10}
            marginPagesDisplayed={1}
            pageRangeDisplayed={2}
            onPageChange={handlePageClick}
            containerClassName='pagination'
            pageClassName='page-item'
            pageLinkClassName='page-link'
            previousClassName='previous-link'
            nextClassName='next-link'
        />
    </>
  )
};

export default Home;