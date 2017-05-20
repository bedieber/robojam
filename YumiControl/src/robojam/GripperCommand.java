package robojam;


public class GripperCommand implements IRobotMessage {
    private GripperState _state;

    public GripperCommand() {
        set_state(GripperState.OPEN);
    }

    public GripperCommand(GripperState state) {
        set_state(state);
    }

    public GripperState get_state() {
        return _state;
    }

    public void set_state(GripperState _state) {
        this._state = _state;
    }
}
;