const mysql = require("mysql")

const pool = mysql.createPool({
  host: process.env.IP,
  user: process.env.DB_USER,
  database: process.env.DB_NAME,
  password: process.env.DB_PASS
});

module.exports = pool;