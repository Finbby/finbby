const jwt = require("jsonwebtoken");
const express = require("express")
const bcrypt = require("bcrypt")
const pool = require("../sql");
const upload = require('../middleware/uploadMiddleware');

const router = express.Router();

//POST REGISTER DATA TO DB
router.post("/register", async (req, res)=>{
	try {
		const hashedPassword = await bcrypt.hash(req.body.password, 10)
		const created_at = new Date().toISOString();
		const data = {
			email: req.body.email,
			username: req.body.username,
			password: hashedPassword,
			created_at: created_at
		}
		const query1 = "INSERT INTO usertb ( email, username, password, created_at) VALUES (?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
					res.status(409);
					res.json({status: "Error", message : "Email already exists" });
				} else {
					res.status(201);
					res.json({status: "Success", message : "User Created!" });
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
});  

// Store the token in a variable in your backend server
let storedToken;

// VALIDATE LOGIN
router.post("/login", async (req, res) => {
  const email = req.query.email;
  const password = req.query.password;
  
  if (email == null || email == '' || password == '' || password == null) {
    res.status(400);
    return res.json({ status: "Error", message: "Please input email and password!" });
  } 

  const query1 = `SELECT * FROM usertb WHERE email = '${email}'`;
  
  try {
    pool.query(query1, async (error, result) => {
      if (!result[0]) {  
        res.status(404);
        return res.json({ status: "Error", message: "User Not found!" });
      }
      
      if (await bcrypt.compare(password, result[0].password)) {
        const token = jwt.sign(
          { email: result[0].email, id_user: result[0].id_user, username: result[0].username },
          'capstone',
          { expiresIn: '20h' }
        );

        // Store the token on the backend
        storedToken = token;

        res.json({ status: "Success", data: result[0], token: token });
      } else {
        res.status(401);
        res.json({ status: "Error", message: "Incorrect Email or Password!" });
      }
    });
  } catch {
    res.status(500);
    res.json({ status: "Error" });
  }
});

const authenticateUser = (req, res, next) => {
  try {
    if (!storedToken) {
      throw new Error('Token not found');
    }

    const secretKey = 'capstone';
    const decodedToken = jwt.verify(storedToken, secretKey);
    req.userData = { email: decodedToken.email, id_user: decodedToken.id_user, username: decodedToken.username };
    next();
  } catch (error) {
    res.status(401).json({ status: "Error", message: "Authentication failed" });
  }
};


//VALIDATE EMAIL
router.post("/email", async (req, res) => {
	const email = req.query.email
	if (email == null || email == ''){
        res.status(400);
		return res.json({ status: "Error", message: "Please input email!"});
	} 
	const query1 = `SELECT * FROM usertb WHERE email = '${email}'`;
	try {
			pool.query(query1, async(error, result)=>{
				if (!result[0]) {  
                    			res.status(404);
					return res.json({status: "Error", message: "Email Not found!"});
				} else {
					res.json({ status: "Success", data : result[0]});
				}
			});
	} catch {
		res.status(500);
		res.json({status: "Error"});
	}
});

//GET ALL DATA USER
router.get("/users", async (req, res)=>{
	const query = "SELECT * FROM usertb";
 	pool.query(query, (error, result)=>{
		res.json(result); 
	});
});

//GET USER DATA BY EMAIL
router.get("/users/email", async (req, res)=>{
	const email = req.body.email
	const query = `SELECT * FROM usertb WHERE email = '${email}'`;
	pool.query(query, [req.params.email], (error, result)=>{
		if (!result[0]) {
            		res.status(404);
			res.json({status: "Error", message: "User Not found!"});
		} else {
			res.json({status: "Success", message : "registered user e-mail", data: result[0]});
		} 
	});
});

//UPDATE PASSWORD TO DB
router.put("/users/changePassword", async (req, res)=>{
	const email = req.body.email
	if (email == null || email == '' || req.body.password == ''|| req.body.password == null){
    		res.status(400);
		return res.json({status: "Error", message: "Please input email and password"});
	}
	const hashedPassword = await bcrypt.hash(req.query.password, 10)
	let password = hashedPassword;
	let updated_at = new Date().toISOString();
	const data = {
		password: password,
		updated_at: updated_at,
	}
	const query1 = `UPDATE usertb SET password ='${password}', updated_at='${updated_at}' WHERE email = '${email}'`;
	const query2 = `SELECT * FROM usertb WHERE email = '${email}'`;
	try {
		pool.query(query2, (error, result)=>{
			if (!result[0]) {  
	 			res.status(404);
				return res.json({status: "Error", message: "User Not found!"});
			}	
		pool.query(query1, (error, result)=>{
		res.json({status: "Succes", message: "Update password Succes!", data : data});		
		})	
		});
	} catch {
		res.json({status: "Error", reason: 500});
	}
});

//POST datarecommendation TO DB
router.post("/survey", authenticateUser, async (req, res)=>{
	try {
		const data = {
			topic: req.query.topic,
		}
		const query1 = "INSERT INTO surveytb (topic) VALUES (?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "datarecommendations Created!" });
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
});

//GET ALL recommendation topic in survey
router.get("/survey", async (req, res)=>{
	const query = "SELECT * FROM surveytb";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "All Recommendation Topic", data: result });
	});
});

//GET recommendation BY ID
router.get("/survey/:id", async (req, res)=>{
	const query = "SELECT * FROM surveytb WHERE id_survey = ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            		res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "recommendation Topic", data: result[0]});
		} 
	});
});

//POST CONTENT TO DB
router.post("/posting/create", authenticateUser, async (req, res)=>{
  upload.single('image')(req, res, (err) => {
    if (err) {
      res.status(400).json({ status: 'Error', message: 'Failed to upload file' });
    } else {
      try {
        const data = {
          title: req.body.title,
          description: req.body.description,
          topic: req.body.topic,
          content: req.body.content,
          image: req.file ? req.file.filename : null,
          sender: req.userData.username,
        };

        const query =
          'INSERT INTO postingtb (title, description, topic, content, image, sender) VALUES (?, ?, ?, ?, ?, ?)';
        pool.query(query, Object.values(data), (error) => {
          if (error) {
            res.status(400).json({ status: 'Error', message: 'Please fill correctly!' });
          } else {
            res.status(201).json({ status: 'Success', message: 'Content Created!', data: data });
          }
        });
      } catch {
        res.status(500).json({ status: 'Error' });
      }
    }
  });
});

//GET ALL POSTING CONTENT 
router.get("/posting", async (req, res)=>{
	const query = "SELECT * FROM postingtb";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "All Contents", data: result }); 
	});
});

//GET POSTING DATA BY ID
router.get("/posting/:id", async (req, res)=>{
	const query = "SELECT * FROM postingtb WHERE id_posting= ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
      			res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "post sent", data: result[0]});
		} 
	});
});

// POST FORUM TO DB
router.post("/forum/create", authenticateUser, async (req, res) => {
	try {
	  const data = {
		forumname: req.body.forumname,
		forumdesc: req.body.forumdesc,
		share: req.body.share,
		topic: req.body.topic,
		admin: req.userData.username // Use userId from authenticated user
	  };
	  const query1 = "INSERT INTO forumtb (forumname, forumdesc, share, topic, admin) VALUES (?, ?, ?, ?, ?)";
	  pool.query(query1, Object.values(data), (error) => {
		if (error) {
		  res.status(400);
		  res.json({ status: "Error", message: "Please fill correctly!" });
		} else {
		  res.status(201);
		  res.json({ status: "Success", message: "Forum Created!", data: data });
		}
	  });
	} catch {
	  res.status(500);
	  res.json({ status: "Error" });
	}
});

// GET all forums with members
router.get("/forum", async (req, res) => {
	const query = "SELECT * FROM forumtb";
	
	pool.query(query, (error, forumsResult) => {
	  if (error) {
		res.status(500).json({ status: "Error", message: "Failed to retrieve forum data" });
	  } else {
		const forumList = forumsResult;
  
		const memberQuery = "SELECT username FROM userforum WHERE forumname = ?";
		const getMembers = (forumIndex) => {
		  if (forumIndex >= forumList.length) {
			res.status(200).json({ status: "Success", message: "All Forums with Members", data: forumList });
			return;
		  }
  
		  const forumname = forumList[forumIndex].forumname;
  
		  pool.query(memberQuery, [forumname], (error, membersResult) => {
			if (error) {
			  res.status(500).json({ status: "Error", message: "Failed to retrieve members" });
			} else {
			  const members = membersResult.map(row => row.username);
			  forumList[forumIndex].members = members;
  
			  getMembers(forumIndex + 1);
			}
		  });
		};
  
		getMembers(0);
	  }
	});
});  

//GET FORUM DATA BY NAME
router.get("/forum/:forumname", async (req, res)=>{
	const query = "SELECT * FROM forumtb WHERE forumname= ?";
	pool.query(query, [req.params.forumname], (error, result)=>{
		if (!result[0]) {
            		res.status(404);
			res.json({status: "Error", message: "Forum Not found!"});
		} else {
			res.json({status: "Success", message : "registered forums", data: result[0]});
		} 
	});
});  

// POST JOIN FORUM
router.post("/forum/join/:forumname", authenticateUser, async (req, res) => {
	try {
	  const forumname = req.params.forumname;
	  const username = req.userData.username; // Use userId from authenticated user
  
	  // Check if the user is already a member of the forum
	  const query = "SELECT * FROM userforum WHERE forumname = ? AND username = ?";
	  pool.query(query, [forumname, username], (error, result) => {
		if (error) {
		  res.status(500);
		  res.json({ status: "Error" });
		} else {
		  if (result.length > 0) {
			res.status(409);
			res.json({ status: "Error", message: "User is already a member of the forum" });
		  } else {
			// Add the user as a member of the forum
			const insertQuery = "INSERT INTO userforum (forumname, username) VALUES (?, ?)";
			pool.query(insertQuery, [forumname, username], (error) => {
			  if (error) {
				res.status(400);
				res.json({ status: "Error", message: "Failed to join the forum" });
			  } else {
				res.status(201);
				res.json({ status: "Success", message: "User joined the forum" });
			  }
			});
		  }
		}
	  });
	} catch {
	  res.status(500);
	  res.json({ status: "Error" });
	}
});

// GET list members of a forum
// router.get("/forums/members/:forumname", async (req, res) => {
//	try {
//	  const forumname = req.params.forumname;
//  
//	  const query = "SELECT username FROM userforum WHERE forumname = ?";
//	  pool.query(query, [forumname], (error, results) => {
//		if (error) {
//		  res.status(400).json({ status: "Error", message: "Failed to retrieve members" });
//		} else {
//		  const members = results.map(row => row.username);
  //
	//	  res.status(200).json({ status: "Success", members: members });
	//	}
//	  });
//	} catch {
//	  res.status(500).json({ status: "Error" });
//	}
// });
  

//POST CHAT TO DB
router.post("/forum/chat/:forumname", authenticateUser, async (req, res)=>{
	try {
		const data = {
			text: req.body.text,
			forumname: req.params.forumname,
			sender: req.userData.username,
		}
		const query1 = "INSERT INTO chattb (text, forumname, sender) VALUES (?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "Chat send!" });
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
});

//GET CHAT FROM GROUP
router.get("/forum/chat/:forumname", async (req, res) => {
	const forumname = req.params.forumname;
	const query = "SELECT text, sender FROM chattb WHERE forumname = ?";
	const values = [forumname];
  
	pool.query(query, values, (error, result) => {
	  if (error) {
		console.error('Error fetching chat messages:', error);
		res.status(500).json({ error: 'Failed to fetch chat messages' });
	  } else {
		const messages = result.map((row) => ({
			text: row.text,
			sender: row.sender
		  }));
		res.status(200).json({ status: 'Success', message: 'Chat', data: messages });
	  }
	});
});  

//DELETE USER DATA FROM DB
router.delete("/users/:id", async (req, res)=>{
	const id = req.params.id
	const query1 = `SELECT * FROM usertb WHERE id= ${id}`;
	const query = `DELETE FROM usertb WHERE id= ${id}`;
		try {
			pool.query(query1, (error, result)=>{
				if (!result.length) {  
                    			res.status(404);
					return res.json({status: "Error", message: "User Not found!"});
				} else {
					pool.query(query, (error, result)=>{
						res.json({status: "Succes!"});
					})
				}
			});
		} catch {
		 	res.status(500);
			res.json({status: "Error"});
		}
});

module.exports = router;
