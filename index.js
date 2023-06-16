const express = require("express")

const app = express();

const http = require('http').createServer(app);
const io = require('socket.io')(http);

app.use(express.json());

// Import routes
const handlerRouter = require("./routes/handler");
const pool = require("./sql");

// Setup all the routes
app.use("/api/handler", handlerRouter);

const port = process.env.port || 3000;
app.listen(port, ()=>{
	console.log(`Server has been started successfully on port ${port}`);
});

app.get("/", async (req, res)=> {
	res.json({ status: "FINBY APP"})
});

pool.query("SELECT 1", (error, result) => {
	if (error) {
	  console.error("Database connection error:", error);
	} else {
	  console.log("Database connected successfully!");
	}
});

io.on('connection', (socket) => {
	socket.on('join', (id_forum) => {
	  socket.join(id_forum);
	});
  
	socket.on('disconnect', () => {
	  console.log('A user disconnected');
	});
});