import React from "react";
import Machine from "../Machine/Machine";

const MachineRender = ({ machines, isConnectionMode, positions, setPositions, onClick,startShape }) => {
  return (
    <div className="content" onClick={onClick}>
      {machines.map((machine, index) => (
        <Machine
          id={`machine-${index}`}
          key={index}
          content={machine}
          isDraggable={!isConnectionMode}
          type="machine"
          positions={positions}
          setPositions={setPositions}
          startShape={startShape}
        />
      ))}
    </div>
  );
};

export default MachineRender;