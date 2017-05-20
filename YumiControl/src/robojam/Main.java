package robojam;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        MRIConnector leftArm=new MRIConnector("localhost", 30004);
        leftArm.connect();
        MRIConnector rightArm=new MRIConnector("localhost", 30005);
        rightArm.connect();
        Rumpelstielzchen stielzchen=new Rumpelstielzchen(leftArm, rightArm,new Vector3(630, -280, 150));
        stielzchen.pickUpAt(5,2);

        stielzchen.pickUpAt(6,6);

        stielzchen.placeAt(4,2);
        stielzchen.placeAt(7,8);

        stielzchen.leftArmPickUpAt(5,5);
        stielzchen.leftArmPlaceAt(5,5);
        stielzchen.rightArmPickUpAt(6,6);
        stielzchen.rightArmPlaceAt(6,6);
    }
}
