import React from 'react'
import './Bar.css'

export const Bar = () => {
  return (
    <div className="toolbar">
        <button className="QButton">Add Q</button>
        <button className="MButton">Add M</button>
        <button className="connectionButton">Connection</button>
        <button className="runButton">Run</button>
        <button className="stopButton">Stop</button>
        <button className="resetButton">Reset</button>

        <div className="input">
        <input type="number" id="products"  min="0" placeholder="Enter number" />
        <label htmlFor="products">Number Of Products</label>
         </div>
         <button className="addButton">Add</button>
    </div>
  )
}
export default Bar

