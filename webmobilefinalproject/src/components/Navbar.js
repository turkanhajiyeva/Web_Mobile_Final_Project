import { Outlet, Link } from "react-router-dom";

const Navbar = () => {
  return (
    <>
      <div class='intro'>
        <h1>IL'PALAZZO PIZZA</h1>
        <p>Hannah Montana said nobody is perfect but here we are</p>
      </div>
      <nav class='navnobs'>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/blogs">Blogs</Link>
          </li>
          <li>
            <Link to="/details">Details</Link>
          </li>
          <li>
            
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Navbar;