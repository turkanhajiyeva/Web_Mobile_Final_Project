import { Outlet, Link } from "react-router-dom";

const Navbar = () => {
  return (
    <>
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
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Navbar;