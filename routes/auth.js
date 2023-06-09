const jwt = require("jsonwebtoken");
const express = require("express");
const bcrypt = require("bcrypt");
const pool = require("../sql");

const router = express.Router();

router.post("/", async (req, res) => {
	const email = req.query.email
	const password = req.query.password
	if (email == null || email == '' || password == ''|| password == null){
    res.status(500);
		return res.json({ status: "Error", message: "Fill all fields!"});
	} 
	const query1 = `SELECT * FROM authtb WHERE email = '${email}'`;
	try {
		pool.query(query1, async(error, result)=>{
			const users = result[0];
			if (!result[0]) {  
				res.status(404);
				return res.json({status: "Error", message: "User Not found!"});
			}
			if(await bcrypt.compare(password , result[0].password)) {
				const token = jwt.sign({
					id: result[0]._id,
					roles: result[0].roles,
				}, "capstone1234", { expiresIn: "20d" });

				res.json({status: "Success", token : token});
			} else {
        res.status(401);
				res.json({status: "Error", message: "Email or password are wrong!"});
			}
			});
	} catch {
    res.status(500);
		res.json({status: "Error"});
	}
});

module.exports = router;