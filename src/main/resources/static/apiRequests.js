// apiRequests.js
import axios from 'axios';

async function fetchData() {
  try {
    const response = await axios.get('http://localhost:9090/api/boats');
    console.log('Data received:', response.data);
    // Process response data here
  } catch (error) {
    // Handle network error
    if (error.response) {
      // The request was made and the server responded with a status code
      console.log('Server Error:', error.response.data);
    } else if (error.request) {
      // The request was made but no response was received
      console.log('Network Error:', error.message);
    } else {
      // Something else happened while setting up the request
      console.log('Error:', error.message);
    }
  }
}

// Export the fetchData function to use it elsewhere if needed
export { fetchData };