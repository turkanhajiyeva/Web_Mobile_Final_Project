const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

const app = express();
const port = 3000;

// Middleware to allow CORS
app.use(cors());

// MySQL connection
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',  // Use your MySQL password
  database: 'your_database_name', // Replace with your actual database name
});

db.connect(err => {
  if (err) throw err;
  console.log('Connected to MySQL');
});

// API route to fetch menu items
app.get('/menu', (req, res) => {
  const query = 'SELECT * FROM menu_items';
  db.query(query, (err, results) => {
    if (err) {
      console.error(err);
      return res.status(500).json({ error: 'Failed to fetch data' });
    }
    res.json(results);
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
