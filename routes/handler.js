const express = require("express")
const bcrypt = require("bcrypt")
const auth = require("../middleware/auth");
const { admin, user } = require("../middleware/roles");
const pool = require("../sql");

const router = express.Router();

//POST CREATE ADMIN DATA TO DB
router.post("/token",  [auth, admin], async (req, res)=>{
	try {
		const hashedPassword = await bcrypt.hash(req.query.password, 10)
		const data = {
			email: req.query.email,
			password: hashedPassword,
			roles: req.query.roles,
		}
		const query1 = "INSERT INTO authtb (email, password, roles) VALUES (?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(409);
					res.json({status: "Error", message : "Email already exists" });
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "User Created!" });
				}
		});
  } catch(error) {
        res.status(500);
	res.json({ status: "Error"});  
    }
});

//POST REGISTER DATA TO DB
router.post("/register", async (req, res)=>{
	try {
		const hashedPassword = await bcrypt.hash(req.query.password, 10)
		const created_at = new Date().toISOString();
		const data = {
			email: req.query.email,
			username: req.query.username,
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

//VALIDATE LOGIN
router.post("/login", async (req, res) => {
    const email = req.query.email
    const password = req.query.password
    if (email == null || email == '' || password == ''|| password == null){
        res.status(400);
        return res.json({ status: "Error", message: "Please input email and password!"});
    } 
        const query1 = `SELECT * FROM usertb WHERE email = '${email}'`;
    try {
            pool.query(query1, async(error, result)=>{
                if (!result[0]) {  
                    res.status(404);
                    return res.json({status: "Error", message: "User Not found!"});
                }
                if(await bcrypt.compare(password , result[0].password)) {
                    res.json({status: "Success", data : result[0]});
                } else {
                    res.status(401);
                    res.json({status: "Error", message: "Incorrect Email or Password!"});
                }
            });
        } catch {
            res.status(500);
            res.json({status: "Error"});
        }
});  


//VALIDATE EMAIL
router.post("/email", [auth, admin], async (req, res) => {
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
	const email = req.query.email
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
router.post("/survey", async (req, res)=>{
	try {
		const data = {
			survey: req.query.survey,
		}
		const query1 = "INSERT INTO surveytb (survey) VALUES (?)";
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
			res.json({status: "Success", message : "ecommendation Topic", data: result[0]});
		} 
	});
});

//POST survey topic
router.post("/topic", async (req, res)=>{
	try {
		const data = {
			topic: req.query.topic,
			id_user: req.query.id_user,
		}
		const query1 = "INSERT INTO topictb (topic, id_user) VALUES (?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "Topic Created!" });
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error" });  
    }
});

//GET recommendation topic in survey
router.get("/topic", async (req, res)=>{
	const query = "SELECT * FROM topictb";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "Topic Selected", data: result });
	});
});

//GET topic BY ID
router.get("/topic/:id", async (req, res)=>{
	const query = "SELECT * FROM topictb WHERE id_topic = ?";
	pool.query(query, [req.params.id], (error, result)=>{
		if (!result[0]) {
            		res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "Topic Selected by Id", data: result[0]});
		} 
	});
});

//POST CONTENT TO DB
router.post("/posting", async (req, res)=>{
	try {
		const data = {
			title: req.query.title,
			description: req.query.description,
			id_topic: req.query.id_topic,
			content: req.query.content,
			id_user: req.query.id_user
		}
		const query1 = "INSERT INTO postingtb (title, description, id_topic, content, id_user) VALUES (?, ?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "Content Created!", data:data});
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
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

//POST FORUM TO DB
router.post("/forum", async (req, res)=>{
	try {
		const data = {
			forumname: req.query.forumname,
			forumdesc: req.query.forumdesc,
			share: req.query.share,
			id_topic: req.query.id_topic
		}
		const query1 = "INSERT INTO forumtb (forumname, forumdesc, share, id_topic) VALUES (?, ?, ?, ?)";
		pool.query(query1, Object.values(data), (error)=>{
				if (error){
                    			res.status(400);
					res.json({status: "Error", message : "Please fill correctly!"});
				} else {
                    			res.status(201);
					res.json({status: "Success", message : "Forum Created!", data:data});
				}
		});
  } catch {
        res.status(500);
	res.json({ status: "Error"});  
    }
});

//GET ALL DATA FORUM
router.get("/forum", async (req, res)=>{
	const query = "SELECT * FROM forumtb";
 	pool.query(query, (error, result)=>{
		res.json({status: "Success", message : "All Forums", data: result });
	});
});

//GET FORUM DATA BY ID
router.get("/forum/:id_forum", async (req, res)=>{
	const query = "SELECT * FROM forumtb WHERE id_forum= ?";
	pool.query(query, [req.params.id_forum], (error, result)=>{
		if (!result[0]) {
            		res.status(404);
			res.json({status: "Error", message: "ID Not found!"});
		} else {
			res.json({status: "Success", message : "registered forums", data: result[0]});
		} 
	});
});

//POST CHAT TO DB
router.post("/forum/:id_forum/chat", async (req, res)=>{
	try {
		const data = {
			text: req.query.text,
			id_forum: req.params.id_forum,
		}
		const query1 = "INSERT INTO chattb (text, id_forum) VALUES (?, ?)";
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
router.get("/forum/:id_forum/chat", async (req, res) => {
	const idForum = req.params.id_forum;
	const query = "SELECT text FROM chattb WHERE id_forum = ?";
	const values = [idForum];
  
	pool.query(query, values, (error, result) => {
	  if (error) {
		console.error('Error fetching chat messages:', error);
		res.status(500).json({ error: 'Failed to fetch chat messages' });
	  } else {
		const messages = result.map((row) => row.text);
		res.status(200).json({ status: 'Success', message: 'Chat', data: messages });
	  }
	});
});  

//DELETE USER DATA FROM DB
router.delete("/users/:id", [auth, admin], async (req, res)=>{
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
