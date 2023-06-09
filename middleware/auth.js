const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {
    const token = req.header("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IiIsImlhdCI6MTY4NjI3NTY2NCwiZXhwIjoxNjg4MDAzNjY0fQ.CYAZ23OMXN5uNSa3jbFH0yBalwElMdZzQS7NjvZRYzc");
    if (!token){
        res.status(401);
        return res.json({status: "Error", message : "Access denied, No token provided"});
    } 
    try {
        const decoded = jwt.verify(token, "capstone1234");
        req.users = decoded;
    } catch {
        res.status(401);
        return res.json({status: "Error", message: "Token Expire!"});
    }

    next();
}
