import React, { useRef } from "react";
import Draggable from "react-draggable";

const Machine = (props) => {
  const buttonRef = useRef(null);

  return (
    <Draggable nodeRef={buttonRef}>
      <div ref={buttonRef}>
        <button
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
          {props.content || "Machine"}
        </button>
      </div>
    </Draggable>
  );
};

export default Machine;
