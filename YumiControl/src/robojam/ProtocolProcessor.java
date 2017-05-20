package robojam;

import robojam.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class ProtocolProcessor
    {
        /// <summary>
        /// Deserializes the specified data into an <see cref="IRobotMessage"/> instance.
        /// </summary>
        /// <param name="data">The data.</param>
        /// <returns></returns>
        /// <exception cref="System.NotImplementedException"></exception>
/*       
	   public static IRobotMessage Deserialize(byte[] data)
        {
            if (data == null)
                throw new ArgumentException();
            var commandByte = data[0];
            switch (commandByte)
            {
                case 1:
                    //7 joint angles
                    if (data.Length < (57))
                        throw new ArgumentException("Data too short for joint command");
                    JointCommand jointCommand = new JointCommand(7);
                    for (int joint = 0; joint < 7; joint++)
                    {
                        var angle = NetworkByteOrderBytesToDouble(data, joint * 8 + 1);
                        jointCommand[joint] = angle;
                    }
                    return jointCommand;
                case 2:
                    //6 doubles for position and rotation
                    if (data.Length < (49))
                        throw new ArgumentException("Data too short for joint command");
                    robojam.PositionCommand positionCommand = new robojam.PositionCommand();
                    int i = 0;
                    positionCommand.Position.setX( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    positionCommand.Position.setY( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    positionCommand.Position.setZ( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    positionCommand.Rotation.setX( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    positionCommand.Rotation.setY( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    positionCommand.Rotation.setZ( NetworkByteOrderBytesToDouble(data, i++ * 8 + 1));
                    return positionCommand;

                case 4:
                    //robot state (seven joint angles, six doubles for position and value)
                    if (data.Length < (105))
                        throw new ArgumentException("Data too short for joint command");
                    RobotState state = new RobotState(7);
                    for (int joint = 0; joint < 7; joint++)
                    {
                        var angle = NetworkByteOrderBytesToDouble(data, joint * 8 + 1);
                        state[joint] = angle;
                    }
                    int j = 7;
                    state.Position.setX( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));
                    state.Position.setY( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));
                    state.Position.setZ( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));
                    state.Rotation.setX( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));
                    state.Rotation.setY( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));
                    state.Rotation.setZ( NetworkByteOrderBytesToDouble(data, j++ * 8 + 1));

                    if (data[data.Length - 1] == 1)
                        state.Gripper.Grip = GripperState.CLOSED;
                    else
                        state.Gripper.Grip = GripperState.OPEN;
                    return state;
                case 3:
                    if (data.Length < (2))
                        throw new ArgumentException("Data too short for gripper command");
                    GripperCommand gripperCommand = new GripperCommand();
                    if (data[1] == 1)
                        gripperCommand.Grip = GripperState.OPEN;
                    else
                        gripperCommand.Grip = GripperState.CLOSED;
                    return gripperCommand;
                case 5:
                    return new StopCommand();
                case 6:
                    if (data.Length < (33))
                        throw new ArgumentException("Data too short for jog command");
                    int d = 0;
                    JogCommand jog = new JogCommand();
                    jog.setX( NetworkByteOrderBytesToDouble(data, d++ * 8 + 1);
                    jog.setY( NetworkByteOrderBytesToDouble(data, d++ * 8 + 1);
                    jog.setZ( NetworkByteOrderBytesToDouble(data, d++ * 8 + 1);
                    jog.Speed = NetworkByteOrderBytesToDouble(data, d++ * 8 + 1);
                    return jog;
                    break;
                case 7:
                    if(data.Length <(2))
                        throw new ArgumentException("Data too short for Impedance command");
                    ImpedanceCommand impCommand = new ImpedanceCommand();
                    if ((data[1]) == 1)
                        impCommand.State = ImpedanceState.ON;
                    else
                        impCommand.State = ImpedanceState.OFF;
                    return impCommand;
                default:
                    Log.Write(LoggingLevel.WARN,"Command Byte unknown. First byte has to be 1, 2 or 3");
                    throw new ArgumentException("Unknown command byte");
                    break;
            }
        }
		*/

        /// <summary>
        /// Serializes the specified message into its binary representation.
        /// </summary>
        /// <param name="msg">The MSG.</param>
        /// <returns></returns>
        /// <exception cref="System.NotImplementedException"></exception>
        public static byte[] Serialize(IRobotMessage msg)
        {
            if (msg == null)
                return null;
            byte[] buffer=null;

            if (msg instanceof PositionCommand)
                buffer= SerializePositionCommand((PositionCommand) msg,1);
            
            if (msg instanceof GripperCommand)
                buffer=SerializeGripperCommand((GripperCommand) msg,0);
            
            return buffer;
        }

 

        /// <summary>
        /// Serializes Position Commands.
        /// </summary>
        private static byte[] SerializePositionCommand(PositionCommand command,int startIndex)
        {
            byte[] buffer=null;
            if (buffer == null)
            {
                buffer = new byte[49];
                buffer[0] = 2;
            }

            byte[] tmp = new byte[8];
            

            int i = 0;

            System.arraycopy(toByteArray(command.getPosition().getX()), 0, buffer, startIndex + i++ * 8, 8);
            System.arraycopy(toByteArray(command.getPosition().getY()), 0, buffer, startIndex + i++ * 8, 8);
            System.arraycopy(toByteArray(command.getPosition().getZ()), 0, buffer, startIndex + i++ * 8, 8);
            System.arraycopy(toByteArray(command.getRotation().getX()), 0, buffer, startIndex + i++ * 8, 8);
            System.arraycopy(toByteArray(command.getRotation().getY()), 0, buffer, startIndex + i++ * 8, 8);
            System.arraycopy(toByteArray(command.getRotation().getZ()), 0, buffer, startIndex + i++ * 8, 8);
            return buffer;
        }

        private static byte[] SerializeGripperCommand(GripperCommand cmd, int startIndex)
        {
            byte[] buffer=null;
            if (buffer == null)
            {
                buffer = new byte[2];
                buffer[0] = 3;
            }
            if (cmd.get_state()== GripperState.OPEN)
                buffer[1] = 1;
            else
                buffer[1] = 0;

            return buffer;
        }
        /// <summary>
        /// Returns a binary representation of a double in network byte order. (Big-Endian)
        /// </summary>
    /*    private static byte[] DoubleToNetworkOrderBytes(double value)
        {
            byte[] bytes = BitConverter.GetBytes(value);
            if (BitConverter.IsLittleEndian)
                Array.Reverse(bytes);

            return bytes;
        }


        /// <summary>
        /// Takes a network byte order binary (Big-Endian) representation of a double \n
        /// and returns the double-precision value.
        /// </summary>
        private static double NetworkByteOrderBytesToDouble(byte[] bytes, int index)
        {
            if (bytes.Length < 8)
                throw new ArgumentException("Byte array has to be at least of length 8");
            if (BitConverter.IsLittleEndian)
                Array.Reverse(bytes,index,8);

            return BitConverter.ToDouble(bytes, index);
        }
        */

        public static byte[] toByteArray(double value) {
            byte[] bytes = new byte[8];
            ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).putDouble(value);
            return bytes;
        }
    }