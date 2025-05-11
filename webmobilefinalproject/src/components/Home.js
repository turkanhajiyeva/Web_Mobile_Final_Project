import ReactPaginate from 'react-paginate'

const handlePageClick = (data)=>{
    console.log(data.selected)
}

const Home = () => {
  return (
    <>
        <h1>IL'PALAZZO PIZZA</h1>

        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
            <img class="carousel-custom-height d-block w-100" src="./images/horsin_around.jpg" alt="First slide"/>
            </div>
            <div class="carousel-item">
            <img class="carousel-custom-height d-block w-100" src="..." alt="Second slide"/>
            </div>
            <div class="carousel-item">
            <img class="carousel-custom-height d-block w-100" src="..." alt="Third slide"/>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
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