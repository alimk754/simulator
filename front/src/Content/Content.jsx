import React, { useState } from "react";
import "./Content.css";
import Queue from "../Queue/Queue";
import Machine from "../Machine/Machine";
import Bar from "../Bar/Bar";

const Content = () => {
  const [queues, setQueues] = useState([]);
  const [machines, setMachines] = useState([]);
  const [isConnectionMode, setIsConnectionMode] = useState(false);
  const [connections, setConnections] = useState([]);
  const [startShape, setStartShape] = useState(null);
  const [Positions, setPositions] = useState([]);


  const addQueue = () => {
    const newQueue = `Q${queues.length}`;
    setQueues([...queues, newQueue]);
  };

  const addMachine = () => {
    const newMachine = `M${machines.length}`;
    setMachines([...machines, newMachine]);
  };

  const selectShape = (e) => {
    if (!isConnectionMode) return;

    console.log(startShape);
    console.log("Connections : ",connections);
    console.log("Positions : ",Positions);

    if (!startShape) {
      if (e.target.dataset.type === "queue") {
        setStartShape(e.target.id);
      }
      else if (e.target.dataset.type === "machine") {
        alert('Arrows can only start from queues!');
      }   
    }else{
      if (e.target.dataset.type === "machine") {
        setConnections([...connections, {id:Date.now(), start: startShape, end: e.target.id }]);
        setStartShape(null);
      }
      else if (e.target.dataset.type === "queue") {
        alert('Arrows can only end on machines!');
      }
    }

  };

  return (
    <div onClick={(e) => selectShape(e)}>
      <Bar isConnectionMode={isConnectionMode} setIsConnectionMode={setIsConnectionMode} onAddQueue={addQueue} onAddMachine={addMachine} />

      <div className="content">
        {queues.map((queue, index) => (
          <Queue id={`queue-${index}`}  key={index} content={queue} isDraggable={!isConnectionMode} type="queue" positions={Positions} setPositions={setPositions}/>
        ))}
      </div>

      <div className="content">
        {machines.map((machine, index) => (
          <Machine id={`machine-${index}`} key={index} content={machine} isDraggable={!isConnectionMode} type="machine" positions={Positions} setPositions={setPositions}/>
        ))}
      </div>

      {connections.map((arrow) => {
  const startPos = Positions[arrow.start];
  const endPos = Positions[arrow.end];
  if (!startPos || !endPos) return null;

  const dx = endPos.x - startPos.x;
  const dy = endPos.y - startPos.y;
  const angle = Math.atan2(dy, dx) * (180 / Math.PI);
  const length = Math.sqrt(dx * dx + dy * dy);

  return (
    <div
      key={arrow.id}
      className="absolute pointer-events-none"
      style={{
        left: `${startPos.x}px`,
        top: `${startPos.y}px`,
        width: `${length}px`,
        height: "2px",
        backgroundColor: "black",
        transformOrigin: "0 0",
        transform: `rotate(${angle}deg)`,
      }}
    >
      <div
        className="absolute"
        style={{
          right: "-6px",
          top: "-4px",
          width: "0",
          height: "0",
          borderTop: "5px solid transparent",
          borderBottom: "5px solid transparent",
          borderLeft: "8px solid black",
        }}
      />
    </div>
  );
})}
    </div>
  );
};

export default Content;
