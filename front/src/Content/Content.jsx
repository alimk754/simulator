import React, { useState } from "react";
import "./Content.css";
import Queue from "../Queue/Queue";
import Machine from "../Machine/Machine";
import Bar from "../Bar/Bar";

const Content = () => {
  const [queues, setQueues] = useState([]);
  const [machines, setMachines] = useState([]);

  const addQueue = () => {
    const newQueue = `Q${queues.length}`;
    setQueues([...queues, newQueue]);
  };

  const addMachine = () => {
    const newMachine = `M${machines.length}`;
    setMachines([...machines, newMachine]);
  };

  return (
    <div>
      <Bar onAddQueue={addQueue} onAddMachine={addMachine} />

      <div className="content">
        {queues.map((queue, index) => (
          <Queue key={index} content={queue} />
        ))}
      </div>

      <div className="content">
        {machines.map((machine, index) => (
          <Machine key={index} content={machine} />
        ))}
      </div>
    </div>
  );
};

export default Content;
