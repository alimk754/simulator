import React, { useState } from "react";
import "./Bar.css";

export const Bar = ({isConnectionMode, setIsConnectionMode, onAddQueue, onAddMachine }) => {
  const [numberOfProducts, setNumberOfProducts] = useState(0);
  const [error, setError] = useState(null);

  function addClick() {
    const item = document.getElementById("products");
    if (item.value === "" || item.value <= 0) {
      setError("Please enter a valid number");
      return;
    }
    setNumberOfProducts(item.value);
    item.value = "";
  }

  return (
    <div className="toolbar">
      <button className="QButton" onClick={onAddQueue}>
        Add Q
      </button>

      <button className="MButton" onClick={onAddMachine}>
        Add M
      </button>

      <button className="connectionButton" onClick={() => setIsConnectionMode(!isConnectionMode)}>{isConnectionMode ? "Drawing Arrows (Click to Exit)" : "Connect"}</button>
      <button className="runButton">Run</button>
      <button className="stopButton">Stop</button>
      <button className="resetButton">Reset</button>

      <div className="input">
        <input
          type="number"
          id="products"
          min="0"
          placeholder="Enter number"
          onChange={() => setError(null)}
        />
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
