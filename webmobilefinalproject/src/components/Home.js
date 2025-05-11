import ReactPaginate from 'react-paginate'

const handlePageClick = ()=>{
    console.log("clicked")
}

const Home = () => {
  return (
    <>
        <h1>IL'PALAZZO PIZZA</h1>

        <ReactPaginate 
            previousLabel={'<'}
            nextLabel={'>'}
            breakLabel={'...'}
            pageCount={10}
            marginPagesDisplayed={3}
            pageRangeDisplayed={6}
            onPageChange={handlePageClick}
        />
    </>
  )
};

export default Home;