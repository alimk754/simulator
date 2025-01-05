import React from "react";
import "./Bar.css";
import { useState } from "react";

export const Bar = () => {
  const [numberOfProducts, setNumberOfProducts] = useState(0);
  const [error, SetError] = useState(null);

  function addClick() {
    const item = document.getElementById("products");
    if (item.value === "" || item.value <= 0) {
      SetError("Please enter a valid number");
      return;
    }
    setNumberOfProducts(item.value);
    item.value = "";
  }

  return (
    <div className="toolbar">
      <button className="QButton">Add Q</button>
      <button className="MButton">Add M</button>
      <button className="connectionButton">Connection</button>
      <button className="runButton">Run</button>
      <button className="stopButton">Stop</button>
      <button className="resetButton">Reset</button>

      <div className="input">
        <input type="number" id="products" min="0" placeholder="Enter number" onChange={()=>SetError(null)} />
        {error && <div className="error-message">{error}</div>}
        <label htmlFor="products">Number Of Products</label>
      </div>

      <button className="addButton" onClick={addClick}>
        Add
      </button>
    </div>
  );
};
export default Bar;
