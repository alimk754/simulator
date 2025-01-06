import React, { useRef, useEffect, useState } from "react";
import Draggable from "react-draggable";

const Queue = ({id, content, isDraggable, type, positions, setPositions}) => {
  const buttonRef = useRef(null);
  const [QueueState,setQueueState]=useState(0); 
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
  useEffect(() => {
    let ws = null;
    let reconnectAttempt = 0;
    const maxReconnectAttempts = 5;
  
    const connect = () => {
      try {
        console.log('Attempting WebSocket connection...');
        ws = new WebSocket('ws://localhost:8080/qs');
        
        ws.onopen = () => {
          console.log('WebSocket Connected');
          reconnectAttempt = 0; // Reset attempt counter on successful connection
        };
  
        ws.onmessage = (event) => {
          try {
            const data = JSON.parse(event.data);
             // Convert to number
            if (data.id === Number(id.split('-')[1]) + 1) {
              console.log(data);
              setQueueState(data.capacity)
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
  }, [QueueState]);

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
            width: "50px",
            height: "50px",
            backgroundColor: "#ffa500",
            borderRadius: "10%",
            marginRight: "5px",
            color: "white",
            border: "none",
            fontSize: "14px",
            fontWeight: "bold",
            cursor: "grab",
          }}
        >
          {content +"|"+QueueState|| "Queue"}
        </button>
      </div>
    </Draggable>
  );
};

export default Queue;