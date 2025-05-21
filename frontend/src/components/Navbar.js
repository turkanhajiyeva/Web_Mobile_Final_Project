import { Outlet, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const STAFF_ROLES = ['Manager', 'Waiter', 'Kitchen Staff'];

const Navbar = () => {
    const { user } = useAuth();
    const isStaff = user && STAFF_ROLES.includes(user.role);
    
  return (
    <>
      <div class='intro'>
        <h1>IL'PALAZZO PIZZA</h1>
        <p className="text-muted">Hannah Montana said nobody is perfect but here we are</p>
      </div>
      <nav className='navnobs'>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/order">Order</Link>
          </li>
          <li>
            <Link to="/details">Details</Link>
          </li>
          {isStaff && (
            <>
              <li>
                <Link to="/staff">Staff Dashboard</Link>
              </li>
              <li>
                <Link to="/menu-management">Menu Management</Link>
              </li>
            </>
          )}
          <li className="loginstuff">
            <Link to="/login">Hello, {user ? user.username : "Please Log-In"}</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Navbar;