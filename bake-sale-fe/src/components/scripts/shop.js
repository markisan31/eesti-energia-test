import React, { useState, useEffect } from "react";
import { getItems, updateItems } from '../../utils/api';
import '../styles/App.css';

const shop = () => {
  const [items, setItems] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [total, setTotal] = useState(0);
  const [paid, setPaid] = useState(0);
  const [change, setChange] = useState(0);
  const [error, setError] = useState(null);


  useEffect(() => {
    getItems().then((response) => {
      setItems(response.data);
    });
  }, []);

  const handleItemClick = (item) => {
    if (item.quantity > 0) {
      setSelectedItems((prevItems) => [...prevItems, item]);
      setItems((prevItems) =>
        prevItems.map((prevItem) =>
          prevItem.id === item.id
            ? { ...prevItem, quantity: prevItem.quantity - 1 }
            : prevItem
        )
      );
      setTotal((prevTotal) => prevTotal + item.price);
    }
  };

  const handleReset = () => {
    setSelectedItems([]);
    setTotal(0);
    setPaid(0);
    setChange(0);
    setError(null);
    getItems().then((response) => {
      setItems(response.data);
    });
  };

  const handleCheckout = () => {
    if (paid < total) {
      setError("Error: Paid amount is less than total");
    } else if (total === 0) {
      setError("Error: Shopping cart is empty");
    } else {
      const updatedItems = selectedItems.map((item) => ({
        id: item.id,
        quantity: item.quantity - 1,
        name: item.name,
        price: item.price,
      }));

      updateItems(updatedItems).then(() => {
        getItems().then((response) => {
          setItems(response.data);
          setSelectedItems([]);
          setTotal(0);
          setPaid(0);
          setChange(paid - total);
        });
      });
    }
  };

  const renderThumbnails = () => {
    const thumbnailList = [];
    for (let i = 0; i < items.length; i += 3) {
      thumbnailList.push(
        <div key={i} className="thumbnail-grid">
          {items
            .slice(i, i + 3)
            .map((item) => (
              <div
                key={item.id} className="thumbnail"
              >
                <img
                  src={require('../../assets/' + item.image)}
                  alt={item.name}
                  onClick={() => handleItemClick(item)}
                  className="thumbnail-img"
                  style={{
                    opacity: item.quantity === 0 ? 0.5 : 1,
                    cursor: item.quantity === 0 ? "not-allowed" : "pointer"
                  }}
                />
                <p style={{ fontWeight: "bold", marginTop: "10px" }}>{item.name}</p>
                <p style={{ fontWeight: "bold" }}>{item.price} €</p>
                <p>Quantity: {item.quantity}</p>
                <style jsx>{`
                  .thumbnail:hover img {
                    transform: scale(1.1);
                  }
                `}</style>
              </div>
            ))}
        </div>
      );
    }
    return thumbnailList;
  };

  const SingleSelectList = () => {
    const [quantities, setQuantities] = useState({});
    const [showList, setShowList] = useState(false);

    useEffect(() => {
      let shouldShow = false;
      items.forEach(item => {
        if (["Toy", "Shirt", "Pants", "Jacket"].includes(item.name) && item.quantity === null) {
          shouldShow = true;
        }
      });
      setShowList(shouldShow);
    }, [items]);

    const handleChange = (item) => (e) => {
      setQuantities({ ...quantities, [item.id]: e.target.value });
    };

    const handleSubmit = () => {
      setShowList(false);
      const updatedItems = items.map((item) => ({
        id: item.id,
        quantity: quantities[item.id] ? parseInt(quantities[item.id]) : item.quantity,
        name: item.name,
        price: item.price,
      }));
      updateItems(updatedItems).then(() => {
        getItems().then((response) => {
          setItems(response.data);
        });
      });
    };

    const filteredItems = items.filter(item => ["Toy", "Shirt", "Pants", "Jacket"].includes(item.name));
    return showList ? (
      <form onSubmit={handleSubmit}>
        <table>
          <thead>
            <tr>
              <th>Item Name</th>
              <th>Quantity</th>
            </tr>
          </thead>
          <tbody>
            {filteredItems.map((item) => (
              <tr key={item.id}>
                <td>{item.name}</td>
                <td>
                  <input
                    type="number"
                    value={quantities[item.id] || item.quantity}
                    onChange={handleChange(item)}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button type="submit">Submit</button>
      </form>
    ) : null;
  };


  const groupBy = (array, key) =>
  array.reduce((result, currentValue) => {
    (result[currentValue[key]] = result[currentValue[key]] || []).push(currentValue);
    return result;
  }, {});

  return (
    <div>
      <h1 style={{
        fontSize: '36px',
        fontWeight: 'bold',
        textAlign: 'center',
        backgroundColor: '#333',
        color: '#fff',
        padding: '20px'
      }}>Bake Sale and Second Hand Outlet</h1>
      <div>
        {renderThumbnails()}
      </div>
      <div style={{ display: "flex", flexDirection: "column", paddingTop: "50px"}} >
      <SingleSelectList />
      </div>
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", fontSize: "18px" }}>
        <h2>Shopping cart:</h2>
        {selectedItems.length > 0 && (
        <ul style={{ listStyle: "none", padding: "1em", border: "solid"}}>
          {Object.values(groupBy(selectedItems, 'id')).map((groupedItems) => (
            <li key={groupedItems[0].id} style={{ display: "flex", justifyContent: "space-between", margin: "10px 0" }}>
              {groupedItems[0].name}
              <span style={{ fontWeight: "bold", marginLeft: '.5rem', fontSize: "18px" }}>
                {Math.round(groupedItems[0].price * 100) / 100} € x {groupedItems.length} pcs
              </span>
            </li>
          ))}
        </ul>
        )}
        <div style={{ display: "flow-root"}}>
        <p>Total: <span style={{ fontWeight: "bold", fontSize: "20px" }}>{Math.round(total * 100) / 100} €</span></p>
        {change !== null && (
          <div>
            <p>Change: <span style={{ fontWeight: "bold", fontSize: "20px" }}>{Math.round(change * 100) / 100} €</span></p>
          </div>
        )}
        </div>
        <div style={{ display: "flow-root", marginTop: "20px" }}>
          <p> <span style={{ fontWeight: "bold", fontSize: "24px", marginRight: "10px" }}>Enter amount:</span><input
            type="text"
            id="paid"
            value={paid}
            onChange={(e) => setPaid(e.target.value)}
            onFocus={() => setPaid("")}
            onKeyPress={(event) => {
              const key = event.key;
              if (!/[0-9\.]/.test(key)) {
                event.preventDefault();
              }
            }}
            style={{ width: "100px", padding: "10px", fontSize: "24px", textAlign: "right",  fontWeight: "bold"}}
            pattern="[0-9]+([\.][0-9]+)?"
            required
          /> <span style={{ fontWeight: "bold", fontSize: "24px" }}>€</span> </p>
          {error && <p style={{ color: "red", marginTop: "10px", fontSize: "24px", fontWeight: "bold" }}>{error}</p>}
        </div>
        <div style={{ display: "flex", justifyContent: "center", marginTop: "20px" }}>
          <button onClick={paid ? handleCheckout : () => {}} className="button">Checkout</button>
          <button onClick={handleReset} className="button">Reset</button>
        </div>
      </div>
    </div>
  );
};

export default shop;