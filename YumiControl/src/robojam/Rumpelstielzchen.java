package robojam;

/**
 * Created by dbe on 20.05.2017.
 */
public class Rumpelstielzchen {

    private Vector3 _gameRoot;

    private MRIConnector _leftArm;
    private MRIConnector _rightArm;

    private Transform _leftArmIdle = new Transform(new Vector3(280, 270, 150), new Vector3(3.14, 0, 135));
    private Transform _rightArmIdle = new Transform(new Vector3(280, -270, 150), new Vector3(3.14, 0, 0));

    public Rumpelstielzchen(MRIConnector leftArm, MRIConnector rightArm, Vector3 gameRoot) {
        _leftArm = leftArm;
        _rightArm = rightArm;
        _gameRoot = gameRoot;
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief picks up an object at the specified location with auto arm select
     */
    public void pickUpAt(int row, int column) {

        try {
            moveArms(row, column, false, false, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief places an object at the specified location with auto arm select
     */
    public void placeAt(int row, int column) {

        try {
            moveArms(row, column, true, false, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief picks up an object at the specified location with the left arm
     */
    public void leftArmPickUpAt(int row, int column) {

        try {
            moveArms(row, column, false, true, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief picks up an object at the specified location with the right arm
     */
    public void rightArmPickUpAt(int row, int column) {

        try {
            moveArms(row, column, false, false, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief places an object at the specified location with the left arm
     */
    public void leftArmPlaceAt(int row, int column) {

        try {
            moveArms(row, column, true, true, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row    zero-based row
     * @param column zero-based column
     * @brief places an object at the specified location with the right arm
     */
    public void rightArmPlaceAt(int row, int column) {

        try {
            moveArms(row, column, true, false, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveArms(int row, int column, boolean openGripper, boolean forceLeft, boolean forceRight) throws InterruptedException {

        //check if left or right arm
        boolean moveLeft = column > 5;
        System.out.println("Using left arm? " + moveLeft);

        if (!forceLeft && !forceRight) {
            MRIConnector activeArm = !moveLeft ? _rightArm : _leftArm;

            //move other arm out of the way
            System.out.println("Making space");

            if (moveLeft)
                _rightArm.sendCommand(new PositionCommand(_rightArmIdle));
            else
                _leftArm.sendCommand(new PositionCommand(_leftArmIdle));

            calculatePosition(row, column, openGripper, activeArm, moveLeft ? _leftArmIdle.getRotation() : _rightArmIdle.getRotation());
        } else {
            if (forceLeft) {
                System.out.println("Forcing leftArm");
                _rightArm.sendCommand(new PositionCommand(_rightArmIdle));
                calculatePosition(row, column, openGripper, _leftArm, _leftArmIdle.getRotation());
                return;
            }
            if (forceRight) {
                System.out.println("Forcing right arm");
                _leftArm.sendCommand(new PositionCommand(_leftArmIdle));
                calculatePosition(row, column, openGripper, _rightArm, _rightArmIdle.getRotation());
                return;
            }
        }
        //calculate position

    }

    private void calculatePosition(int row, int column, boolean openGripper, MRIConnector activeArm, Vector3 rotation) throws InterruptedException {
        double x, y;
        x = _gameRoot.getX() - row * 50;
        y = _gameRoot.getY() + column * 50;
        Vector3 target = new Vector3(x, y, 150);
        System.out.println("Moving to " + target.getX() + "/" + target.getY() + "/" + target.getZ());
        //hover above
        System.out.println("Approaching target position");
        activeArm.sendCommand(new PositionCommand(target, rotation));
        Thread.sleep(8000);
        if (!openGripper) {
            activeArm.sendCommand(new GripperCommand(GripperState.OPEN));
        }
        //go down
        System.out.println("Going down");
        if (activeArm == _rightArm)
            target.setZ(27);
        else
            target.setZ(33);
        activeArm.sendCommand(new PositionCommand(target, rotation));
        Thread.sleep(3000);
        //perform grip action
        System.out.println("Grip");
        GripperCommand gripperCommand = new GripperCommand();
        gripperCommand.set_state(openGripper ? GripperState.OPEN : GripperState.CLOSED);
        activeArm.sendCommand(gripperCommand);
        Thread.sleep(500);

        //go up
        System.out.println("Going up");
        target.setZ(150);
        activeArm.sendCommand(new PositionCommand(target, rotation));
        Thread.sleep(2000);
    }


}
