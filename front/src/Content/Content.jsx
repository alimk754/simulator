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
        console.log("Setting start shape:", shapeId);
        setTypeOFStart(shapeType);
        setStartShape(shapeId);
    } else {
      if (typeOFStart !== shapeType) {
        console.log("Creating connection from", startShape, "to", shapeId);
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
            endPos,
          });
          return null;
        }

        // Fetch the start and end elements dynamically
        const startElement = document.getElementById(arrow.start);
        const endElement = document.getElementById(arrow.end);

        if (!startElement || !endElement) {
          console.log("Could not find start or end element for arrow:", {
            arrow,
            startElement: !!startElement,
            endElement: !!endElement,
          });
          return null;
        }

        // Check if both are Machines
        const isMachineToMachine =
          startElement.dataset.type === "machine" && endElement.dataset.type === "machine";

        // Calculate dimensions for both start and end elements
        const startRect = startElement.getBoundingClientRect();
        const endRect = endElement.getBoundingClientRect();

        // Calculate radii for start and end elements
        const startRadius = Math.min(startRect.width, startRect.height) / 2;
        const endRadius = Math.min(endRect.width, endRect.height) / 2;

        // Define margin offsets
        const defaultMarginOffset = 10; // Default margin for all connections
        const machineMarginOffset = 5; // Closer margin for Machine-to-Machine connections
        const marginOffset = isMachineToMachine ? machineMarginOffset : defaultMarginOffset;

        const dx = endPos.x - startPos.x;
        const dy = endPos.y - startPos.y;
        const angle = Math.atan2(dy, dx); // In radians

        // Adjust start and end positions
        const adjustedStart = {
          x: startPos.x + Math.cos(angle) * (startRadius + marginOffset),
          y: startPos.y + Math.sin(angle) * (startRadius + marginOffset),
        };
        const adjustedEnd = {
          x: endPos.x - Math.cos(angle) * (endRadius + marginOffset),
          y: endPos.y - Math.sin(angle) * (endRadius + marginOffset),
        };

        // Recalculate line length after adjustment
        const adjustedDx = adjustedEnd.x - adjustedStart.x;
        const adjustedDy = adjustedEnd.y - adjustedStart.y;
        const adjustedLength = Math.sqrt(adjustedDx * adjustedDx + adjustedDy * adjustedDy);

        return (
          <div
            key={arrow.id}
            style={{
              position: 'fixed',
              left: `${adjustedStart.x}px`,
              top: `${adjustedStart.y}px`,
              width: `${adjustedLength}px`,
              height: '2px',
              backgroundColor: 'black',
              transformOrigin: '0 0',
              transform: `rotate(${angle * (180 / Math.PI)}deg)`,
              pointerEvents: 'none',
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
                borderLeft: '8px solid black',
              }}
            />
          </div>
        );
      })}
    </div>
  );
};

export default Content;