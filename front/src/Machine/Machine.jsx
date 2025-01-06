import React, { useRef, useEffect,useState } from "react";
import Draggable from "react-draggable";

const Machine = ({id, content, isDraggable, type, positions, setPositions}) => {
  const buttonRef = useRef(null);

  // Update position whenever drag occurs
  const handleDrag = (e, data) => {
    const rect = buttonRef.current.getBoundingClientRect();
    setPositions(prev => ({
      ...prev,
      [id]: { 
        x: rect.left + rect.width / 2,
        y: rect.top + rect.height / 2
      }
    }));
  };

  // Initialize position on mount
  useEffect(() => {
    if (buttonRef.current) {
      const rect = buttonRef.current.getBoundingClientRect();
      setPositions(prev => ({
        ...prev,
        [id]: { 
          x: rect.left + rect.width / 2,
          y: rect.top + rect.height / 2
        }
      }));
    }
  }, []);
  const [machineState, setMachineState] = useState({
    color: '#000000',
    isWorking: false
  });
useEffect(() => {
  let ws = null;
  let reconnectAttempt = 0;
  const maxReconnectAttempts = 5;

  const connect = () => {
    try {
      console.log('Attempting WebSocket connection...');
      ws = new WebSocket('ws://localhost:8080/ws');

      ws.onopen = () => {
        console.log('WebSocket Connected');
        reconnectAttempt = 0; // Reset attempt counter on successful connection
      };

      ws.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data);
           // Convert to number
          if (data.machineId === Number(id.split('-')[1]) + 1) {
            console.log(data);
            setMachineState({
              color: data.color,
              isWorking: data.isWorking
            });
          }
        } catch (e) {
          console.error('Error parsing WebSocket message:', e);
        }
      };

      ws.onerror = (error) => {
        console.error('WebSocket Error:', error);
      };

      ws.onclose = (e) => {
        console.log('WebSocket Closed:', e.reason);
        if (reconnectAttempt < maxReconnectAttempts) {
          reconnectAttempt++;
          console.log(`Reconnecting attempt ${reconnectAttempt}...`);
          setTimeout(connect, 2000); // Retry after 2 seconds
        }
      };
    } catch (error) {
      console.error('WebSocket connection error:', error);
    }
  };

  connect();

  // Cleanup function
  
}, [machineState]);

  return (
    <Draggable 
      disabled={!isDraggable} 
      nodeRef={buttonRef}
      onDrag={handleDrag}
      onStop={handleDrag}
    >
      <div ref={buttonRef}>
        <button
          id={id}
          data-type={type}
          style={{
            width: "60px",
            height: "60px",
            borderRadius: "50%",
            marginRight: "5px",
            border: "none",
            backgroundColor: machineState.color,
            color: "white",
            fontWeight: "bold",
            fontSize: "14px",
            cursor: "grab",
          }}
        >
          {content || "Machine"}
        </button>
      </div>
    </Draggable>
  );
};

export default Machine;