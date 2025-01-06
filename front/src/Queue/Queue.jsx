import React, { useRef, useEffect, useState } from "react";
import Draggable from "react-draggable";

const Queue = ({ id, content, isDraggable, type, positions, setPositions }) => {
  const buttonRef = useRef(null);
  const [queueState, setQueueState] = useState(0); 
  const [products, setProducts] = useState([]); 
  const [showProducts, setShowProducts] = useState(false);

  const handleDrag = (e, data) => {
    const rect = buttonRef.current.getBoundingClientRect();
    setPositions((prev) => ({
      ...prev,
      [id]: {
        x: rect.left + rect.width / 2,
        y: rect.top + rect.height / 2,
      },
    }));
  };
  
  useEffect(() => {
    if (buttonRef.current) {
      const rect = buttonRef.current.getBoundingClientRect();
      setPositions((prev) => ({
        ...prev,
        [id]: {
          x: rect.left + rect.width / 2,
          y: rect.top + rect.height / 2,
        },
      }));
    }
  }, []);

  useEffect(() => {
    let ws = null;
    let reconnectAttempt = 0;
    const maxReconnectAttempts = 5;

    const connect = () => {
      try {
        console.log("Attempting WebSocket connection...");
        ws = new WebSocket("ws://localhost:8080/qs");

        ws.onopen = () => {
          console.log("WebSocket Connected");
          reconnectAttempt = 0; 
        };

        ws.onmessage = (event) => {
          try {
            const data = JSON.parse(event.data);
            
            if (data.id === Number(id.split("-")[1]) + 1) {
              console.log(data);
              setQueueState(data.capacity); 
              setProducts(data.products); 
            }
          } catch (e) {
            console.error("Error parsing WebSocket message:", e);
          }
        };

        ws.onerror = (error) => {
          console.error("WebSocket Error:", error);
        };

        ws.onclose = (e) => {
          console.log("WebSocket Closed:", e.reason);
          if (reconnectAttempt < maxReconnectAttempts) {
            reconnectAttempt++;
            console.log(`Reconnecting attempt ${reconnectAttempt}...`);
            setTimeout(connect, 2000); 
          }
        };
      } catch (error) {
        console.error("WebSocket connection error:", error);
      }
    };

    connect();
  }, [id]);

  const handleClick = () => {
    setShowProducts(!showProducts); 
  };

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
          onDoubleClick={handleClick} 
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
          {content + "|" + queueState || "Queue"}
        </button>

        {showProducts && (
          <div
            style={{
              position: "absolute",
              top: "60px",
              left: "-75px",
              width: "200px",
              backgroundColor: "white",
              border: "1px solid #e4e4e4",
              borderRadius: "8px",
              padding: "12px",
              zIndex: 1000,
              boxShadow: "0 4px 20px rgba(0, 0, 0, 0.15)",
            }}
          >
            <div
              style={{
                position: "absolute",
                top: "-8px",
                left: "96px",
                width: "0",
                height: "0",
                borderLeft: "8px solid transparent",
                borderRight: "8px solid transparent",
                borderBottom: "8px solid white",
                filter: "drop-shadow(0 -1px 1px rgba(0,0,0,0.1))",
              }}
            />

            <h4 style={{
              margin: "0 0 10px 0",
              color: "#333",
              fontSize: "14px",
              fontWeight: "600",
              padding: "0 0 8px 0",
              borderBottom: "1px solid #eee"
            }}>
              Products in {content}
            </h4>
            <h4 style={{
              margin: "0 0 10px 0",
              color: "#333",
              fontSize: "14px",
              fontWeight: "600",
              padding: "0 0 8px 0",
              borderBottom: "1px solid #eee"
            }}>
              size {queueState}
            </h4>

            <div style={{
              maxHeight: "200px",
              overflowY: "auto",
              paddingRight: "4px"
            }}>
              {products.length > 0 ? (
                [...products].reverse().map((product, index) => (
                  <div
                    key={products.length - index - 1}
                    style={{
                      backgroundColor: product.color,
                      padding: "8px 12px",
                      margin: "6px 0",
                      borderRadius: "6px",
                      color: "#333",
                      fontSize: "13px",
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "center",
                      boxShadow: "0 2px 4px rgba(0,0,0,0.05)",
                      transition: "transform 0.2s ease",
                    }}
                    onMouseEnter={(e) => {
                      e.currentTarget.style.transform = "translateX(4px)";
                    }}
                    onMouseLeave={(e) => {
                      e.currentTarget.style.transform = "translateX(0)";
                    }}
                  >
                     <span style={{ fontWeight: "500" }}>Product</span> 
                  </div>
                ))
              ) : (
                <div style={{
                  textAlign: "center",
                  color: "#666",
                  padding: "20px 0",
                  fontSize: "13px"
                }}>
                  No products
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </Draggable>
  );
};

export default Queue;