package robojam;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try {
            MRIConnector leftArm = new MRIConnector("localhost", 30004);
            leftArm.connect();
            MRIConnector rightArm = new MRIConnector("localhost", 30005);
            rightArm.connect();
            Rumpelstielzchen stielzchen = new Rumpelstielzchen(leftArm, rightArm, new Vector3(630, -280, 150));

//        //place kater
            stielzchen.pickUpAt(1, 0);
            stielzchen.placeAt(0, 3);
//        //place Shirkan
            stielzchen.pickUpAt(4, 1);
            stielzchen.placeAt(2, 5);
//        //place Scar (location G5 ???)
            stielzchen.pickUpAt(3, 10);
            stielzchen.placeAt(4, 8);
            System.in.read();
            //Schatztruhe
            stielzchen.pickUpAt(2, 11);
            stielzchen.placeAt(3, 9);
            //coin1
            stielzchen.pickUpAt(7, 0);
            stielzchen.placeAt(3, 5);

            System.in.read();
            // coin 2
            stielzchen.pickUpAt(6, 1);
            stielzchen.placeAt(6, 4);
            System.in.read();
            //coin 3
            stielzchen.pickUpAt(7, 10);
            stielzchen.placeAt(7, 6);
            System.in.read();

            //truhe zurück
            stielzchen.pickUpAt(3, 9);
            stielzchen.placeAt(2, 11);


            //place fiona at H4
            stielzchen.pickUpAt(1, 10);
            stielzchen.placeAt(3, 9);
            System.in.read();
            //shrek
            stielzchen.pickUpAt(5, 10);
            stielzchen.placeAt(7, 6);
            //wolf
            stielzchen.pickUpAt(5, 0);
            stielzchen.placeAt(2, 5);
            System.in.read();
            stielzchen.leftArmPickUpAt(2,5);
            stielzchen.leftArmPlaceAt(3,6);
            System.in.read();
            stielzchen.pickUpAt(3, 6);
            stielzchen.placeAt(4, 6);
            System.in.read();
            //phase 4
            //esel
            stielzchen.pickUpAt(0,1);
            stielzchen.placeAt(4,2);
            System.in.read();

            //hindernis
            stielzchen.leftArmPickUpAt(6,11);
            stielzchen.leftArmPlaceAt(6,5);
            System.in.read();
            //hindernis
            stielzchen.leftArmPickUpAt(4,11);
            stielzchen.leftArmPlaceAt(5,4);
            System.in.read();
            //hindernis
            stielzchen.pickUpAt(3,0);
            stielzchen.placeAt(4,3);
            System.in.read();
            //hindernis
            stielzchen.pickUpAt(2,1);
            stielzchen.placeAt(5,2);
            System.in.read();

        } catch (Exception ex) {

        }
    }
}
