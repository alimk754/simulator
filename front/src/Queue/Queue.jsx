import React, { useRef } from "react";
import Draggable from "react-draggable";

const Queue = (props) => {
  const buttonRef = useRef(null);

  return (
    <Draggable nodeRef={buttonRef}>
      <div ref={buttonRef}>
        <button
          style={{
            width: "70px",
            height: "35px",
            backgroundColor: "#ffa500",
            color: "white",
            border: "none",
            fontSize: "14px",
            cursor: "grab",
          }}
        >
          {props.content || "Queue"}
        </button>
      </div>
    </Draggable>
  );
};

export default Queue;
