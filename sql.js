const mysql = require("mysql")

const pool = mysql.createPool({
	host: 'localhost',
  user: 'root',
  password: '',
  database: 'pool',
  port: '3306'
});

module.exports = pool;