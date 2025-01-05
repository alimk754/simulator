import React, { useRef } from "react";
import Draggable from "react-draggable";

const Machine = ({id,key, content, isDraggable, type, positions, setPositions}) => {
  const buttonRef = useRef(null);

  const handleDragStop = (e, data) => {
    setPositions((prevPositions) => {
      // Check if the id exists in the positions object
      if (prevPositions[id]) {
        // Update the existing entry
        return {
          ...prevPositions,
          [id]: { x: data.x, y: data.y },
        };
      } else {
        // Add a new entry
        return {
          ...prevPositions,
          [id]: { x: data.x, y: data.y },
        };
      }
    });
    console.log(positions);
  };
  return (
    <Draggable disabled={!isDraggable} nodeRef={buttonRef} onStop={handleDragStop}>
      <div ref={buttonRef}>
        <button
          id={id}
          data-type={type}
          
          style={{
            width: "70px",
            height: "70px",
            borderRadius: "50%",
            border: "none",
            backgroundColor: "black",
            color: "white",
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
