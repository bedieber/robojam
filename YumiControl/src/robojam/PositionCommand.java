package robojam;

import robojam.*;

import javax.swing.text.Position;


public class PositionCommand extends Transform implements IRobotMessage
    {
        /// <summary>
        /// Initializes a new Instance of the robojam.PositionCommand
        /// </summary>
        /// <seealso cref="Messaging.IRobotMessage" />
        /// <seealso cref="Messaging.Transform" />
        public PositionCommand(Vector3 position, Vector3 rotation)
        {
			super(position, rotation);
        }

        public PositionCommand(Transform transform)
        {
            super(transform.getPosition(), transform.getRotation());
        }

    }