const axios = require('axios');

// Fetch all posts from JSONPlaceholder
async function fetchPosts() {
  try {
    const response = await axios.get('https://jsonplaceholder.typicode.com/posts');
    return response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
    throw error;
  }
}

// Fetch a single post by ID from JSONPlaceholder
async function fetchPostById(id) {
  try {
    const response = await axios.get(`https://jsonplaceholder.typicode.com/posts/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching post with ID ${id}:`, error);
    throw error;
  }
}

// Create a new post on JSONPlaceholder
async function createPost(post) {
  try {
    const response = await axios.post('https://jsonplaceholder.typicode.com/posts', post);
    return response.data;
  } catch (error) {
    console.error('Error creating post:', error);
    throw error;
  }
}

module.exports = {
  fetchPosts,
  fetchPostById,
  createPost,
};
