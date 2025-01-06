import React from "react";
import Queue from "../Queue/Queue";

const QueueRender= ({ queues, isConnectionMode, positions, setPositions, onClick,startShape }) => {
  return (
    <div className="content" onClick={onClick}>
      {queues.map((queue, index) => (
        <Queue
          id={`queue-${index}`}
          key={index}
          content={queue}
          isDraggable={!isConnectionMode}
          type="queue"
          positions={positions}
          setPositions={setPositions}
          startShape={startShape}
        />
      ))}
    </div>
  );
};

export default QueueRender;