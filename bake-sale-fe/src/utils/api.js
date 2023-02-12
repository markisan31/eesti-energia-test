import axios from 'axios';

export const getItems = () => {
    return axios.get('http://localhost:14441/items');
  };
  
  export const updateItems = (payload) => {
    return axios.put(`http://localhost:14441/items/bulk`, payload, {
    });
  };