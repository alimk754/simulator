import React, { useState, useEffect } from "react";
import "./Content.css";
import Bar from "../Bar/Bar";
import QueueRender from "./QueueRender";
import MachineRender from "./MachineRender";
import ConnectionLine from "./ConnectionLine";

const Content = () => {
  const [queues, setQueues] = useState([]);
  const [machines, setMachines] = useState([]);
  const [isConnectionMode, setIsConnectionMode] = useState(false);
  const [connections, setConnections] = useState([]);
  const [startShape, setStartShape] = useState(null);
  const [typeOFStart, setTypeOFStart] = useState(null);
  const [positions, setPositions] = useState({});

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

    const targetElement = e.target.closest('button');
    if (!targetElement) {
     
      return;
    }

    const shapeType = targetElement.dataset.type;
    const shapeId = targetElement.id;



    if (!startShape) {
    
        setTypeOFStart(shapeType);
        setStartShape(shapeId);
    } else {
      if (typeOFStart !== shapeType) {
     
        if (connections.some(connection => connection.start === startShape && connection.end === shapeId) || connections.some(connection => connection.start === shapeId && connection.end === startShape)) {
          alert("Connection already exists");
          setStartShape(null);
          setTypeOFStart(null);
          return;
        }
        
        const startElement = document.getElementById(startShape);
        const endElement = targetElement;
        
        if (startElement && endElement) {
          const startRect = startElement.getBoundingClientRect();
          const endRect = endElement.getBoundingClientRect();

          const newPositions = {
            ...positions,
            [startShape]: {
              x: startRect.left + startRect.width / 2,
              y: startRect.top + startRect.height / 2
            },
            [shapeId]: {
              x: endRect.left + endRect.width / 2,
              y: endRect.top + endRect.height / 2
            }
          };

          
          setPositions(newPositions);

          const newConnection = {
            id: Date.now(),
            start: startShape,
            end: shapeId
          };

          
          setConnections([...connections, newConnection]);
        }
      } else {
        alert('Arrows can only send from M to Q or from M to Q');
      }
      setStartShape(null);
      setTypeOFStart(null);
    }
  };

  return (
    <div className="relative w-full min-h-screen">
      <Bar 
        isConnectionMode={isConnectionMode} 
        setIsConnectionMode={setIsConnectionMode} 
        onAddQueue={addQueue} 
        onAddMachine={addMachine} 
        setStartShape={setStartShape}
        setTypeOFStart={setTypeOFStart}
        setMachines={setMachines}
        setQueues={setQueues}
        setConnections={setConnections}
      />

      <QueueRender
        queues={queues}
        isConnectionMode={isConnectionMode}
        positions={positions}
        setPositions={setPositions}
        onClick={selectShape}
      />

      <MachineRender
        machines={machines}
        isConnectionMode={isConnectionMode}
        positions={positions}
        setPositions={setPositions}
        onClick={selectShape}
      />

      {connections.map((arrow) => (
        <ConnectionLine key={arrow.id} arrow={arrow} positions={positions} />
      ))}
    </div>
  );
};

export default Content;