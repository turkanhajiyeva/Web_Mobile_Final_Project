import { Outlet, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import logo from '../assets/pizza.png';

const STAFF_ROLES = ['Manager', 'Waiter', 'Kitchen Staff'];

const Navbar = () => {
    const { user } = useAuth();
    const isStaff = user && STAFF_ROLES.includes(user.role);

    return (
        <>
            <div className='intro'>
                <div className="logo-container">
                    <img
                        src={logo}
                        alt="IL'PALAZZO PIZZA Logo"
                        className="restaurant-logo"
                    />
                </div>
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