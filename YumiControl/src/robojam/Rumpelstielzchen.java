package robojam;

/**
 * Created by dbe on 20.05.2017.
 */
public class Rumpelstielzchen {

    private Vector3 _gameRoot;

    private MRIConnector _leftArm;
    private MRIConnector _rightArm;

    private Transform _leftArmIdle = new Transform(new Vector3(280, 270, 150), new Vector3(3.14 , 0, 0 ));
    private Transform _rightArmIdle = new Transform(new Vector3(280, -270, 150), new Vector3(3.14,  0, 0   ));

    public Rumpelstielzchen(MRIConnector leftArm, MRIConnector rightArm, Vector3 gameRoot) {
        _leftArm = leftArm;
        _rightArm = rightArm;
        _gameRoot=gameRoot;
    }

    public void pickUpAt(int row, int column) {

        try {
            moveArms(row,column, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void placeAt(int row, int column) {

        try {
            moveArms(row,column, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveArms(int row, int column, boolean openGripper) throws InterruptedException {

        //check if left or right arm
        boolean moveLeft = column > 5;
        System.out.println("Using left arm? "+moveLeft);
        MRIConnector activeArm = !moveLeft ? _rightArm : _leftArm;

        //move other arm out of the way
        System.out.println("Making space");
        if (moveLeft)
            _rightArm.sendCommand(new PositionCommand(_rightArmIdle));
        else
            _leftArm.sendCommand(new PositionCommand(_leftArmIdle));
        //calculate position
        double x, y;
        x = _gameRoot.getX() - row * 50;
        y = _gameRoot.getY() + column * 50;
        Vector3 target=new Vector3(x, y, 150);
        System.out.println("Moving to "+target.getX()+"/"+target.getY()+"/"+target.getZ());
        //hover above
        System.out.println("Approaching target position");
        activeArm.sendCommand(new PositionCommand(target, _leftArmIdle.getRotation()));
        //Thread.sleep(8000);
        if(!openGripper)
        {
            activeArm.sendCommand(new GripperCommand(GripperState.OPEN));
        }
        //go down
        System.out.println("Going down");
        target.setZ(30);
        activeArm.sendCommand(new PositionCommand(target, _leftArmIdle.getRotation()));
        //Thread.sleep(2000);
        //perform grip action
        System.out.println("Grip");
        GripperCommand gripperCommand=new GripperCommand();
        gripperCommand.set_state(openGripper?GripperState.OPEN:GripperState.CLOSED);
        activeArm.sendCommand(gripperCommand);
        //Thread.sleep(2000);

        //go up
        System.out.println("Going up");
        target.setZ(150);
        activeArm.sendCommand(new PositionCommand(target, _leftArmIdle.getRotation()));
        //Thread.sleep(5000);
    }


}
