import React, { useState, useEffect } from "react";
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
  const [positions, setPositions] = useState({});

  const addQueue = () => {
    const newQueue = `Q${queues.length}`;
    setQueues([...queues, newQueue]);
  };

  const addMachine = () => {
    const newMachine = `M${machines.length}`;
    setMachines([...machines, newMachine]);
  };

  // Debug logging for positions
  useEffect(() => {
    console.log("Current positions:", positions);
  }, [positions]);

  // Debug logging for connections
  useEffect(() => {
    console.log("Current connections:", connections);
  }, [connections]);

  const selectShape = (e) => {
    if (!isConnectionMode) return;

    const targetElement = e.target.closest('button');
    if (!targetElement) {
      console.log("No button found in click target");
      return;
    }

    const shapeType = targetElement.dataset.type;
    const shapeId = targetElement.id;

    console.log("Clicked shape:", { type: shapeType, id: shapeId });

    if (!startShape) {
      if (shapeType === "queue") {
        console.log("Setting start shape:", shapeId);
        setStartShape(shapeId);
      } else if (shapeType === "machine") {
        alert('Arrows can only start from queues!');
      }
    } else {
      if (shapeType === "machine") {
        console.log("Creating connection from", startShape, "to", shapeId);
        
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

          console.log("New positions:", newPositions);
          setPositions(newPositions);

          const newConnection = {
            id: Date.now(),
            start: startShape,
            end: shapeId
          };

          console.log("Adding new connection:", newConnection);
          setConnections([...connections, newConnection]);
        } else {
          console.log("Could not find elements:", { 
            startElement: !!startElement, 
            endElement: !!endElement 
          });
        }

        setStartShape(null);
      } else if (shapeType === "queue") {
        alert('Arrows can only end on machines!');
      }
    }
  };

  return (
    <div className="relative w-full min-h-screen">
      <Bar 
        isConnectionMode={isConnectionMode} 
        setIsConnectionMode={setIsConnectionMode} 
        onAddQueue={addQueue} 
        onAddMachine={addMachine} 
      />

      <div className="content" onClick={selectShape}>
        {queues.map((queue, index) => (
          <Queue
            id={`queue-${index}`}
            key={index}
            content={queue}
            isDraggable={!isConnectionMode}
            type="queue"
            positions={positions}
            setPositions={setPositions}
          />
        ))}
      </div>

      <div className="content" onClick={selectShape}>
        {machines.map((machine, index) => (
          <Machine
            id={`machine-${index}`}
            key={index}
            content={machine}
            isDraggable={!isConnectionMode}
            type="machine"
            positions={positions}
            setPositions={setPositions}
          />
        ))}
      </div>

      {connections.map((arrow) => {
        console.log("Rendering arrow:", arrow);
        const startPos = positions[arrow.start];
        const endPos = positions[arrow.end];
        
        if (!startPos || !endPos) {
          console.log("Missing positions for arrow:", {
            arrow,
            startPos,
            endPos
          });
          return null;
        }

        const dx = endPos.x - startPos.x;
        const dy = endPos.y - startPos.y;
        const angle = Math.atan2(dy, dx) * (180 / Math.PI);
        const length = Math.sqrt(dx * dx + dy * dy);

        console.log("Arrow calculations:", {
          dx,
          dy,
          angle,
          length
        });

        return (
          <div
            key={arrow.id}
            style={{
              position: 'fixed',
              left: `${startPos.x}px`,
              top: `${startPos.y}px`,
              width: `${length}px`,
              height: '2px',
              backgroundColor: 'black',
              transformOrigin: '0 0',
              transform: `rotate(${angle}deg)`,
              zIndex: 1000,
              pointerEvents: 'none'
            }}
          >
            <div
              style={{
                position: 'absolute',
                right: '-6px',
                top: '-4px',
                width: 0,
                height: 0,
                borderTop: '5px solid transparent',
                borderBottom: '5px solid transparent',
                borderLeft: '8px solid black'
              }}
            />
          </div>
        );
      })}
    </div>
  );
};

export default Content;