package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "Blue Side Auto",group = "BLUE")
public class BlueSideAuto extends AutoAbstract{

    @Override
    public void runOpMode() {
        setupMotors();
        String Side = getAutoType();
        String ParkSide = getParkSide();
        long Delay = getDelay();
        waitForStart();
        sleep(Delay);
        if (Side.equals("A")) {//Blue-Park
            //Assume we start ~1 tile from the warehouse
            if (ParkSide.equals("A")) {
                encoderDrive(.5,1,24);
                encoderStrafe(.5,1,30);
                encoderDrive(.5,1,24);
            } else if (ParkSide.equals("B")) {
                encoderDrive(.5,-1,48);
                encoderStrafe(.5,1,30);
                encoderDrive(.5,-1,24);
            }

        } else if (Side.equals("B")) {//Blue-Carousel-Park
            //Assume we start ~1 tile from the Carousel
            encoderDrive(.5,-1,24);
            turnDuckStart(.5);
            timeDrive(-.1,6);
            turnDuckStop();
            if (ParkSide.equals("A")) {
                timeStrafe(.5,1.5);
                encoderDrive(.5,1,96);
            } else if (ParkSide.equals("B")) {
                encoderStrafe(.3,1,24);
                encoderDrive(.3,-1,22);
            }

        } else if (Side.equals("X")) {//Blue-Hub-Park
            //Assume we are with the ducks close to the warehouse
            int DuckPos = getDuckPos();
            encoderTurn(.1,1,30);
            encoderDrive(.3,1,30);
            //make arm go up
            //release our thingy onto the hub
            //make arm go down
            sleep(1500);
            //*do things*
            encoderDrive(.3,-1,30);
            encoderTurn(.1,-1,30);
            if (ParkSide.equals("A")) {
                encoderTurn(.1,-1,90);
                encoderDrive(.5,1,66);
            } else if (ParkSide.equals("B")) {
                encoderTurn(.1,1,90);
                encoderDrive(.4,1,24);
                encoderStrafe(.4,-1,30);
                encoderDrive(.3,1,30);
            }


        } else if (Side.equals("Y")) {//Blue-Carousel-Hub-Park
            //Assume we start with the ducks close to the warehouse
            int DuckPos = getDuckPos();
            encoderTurn(.1,1,30);
            encoderDrive(.3,1,30);
            //make arm go up
            //release our thingy onto the hub
            //make arm go down
            sleep(1500);
            encoderDrive(.3,-1,30);
            encoderTurn(.1,-1,30);
            encoderStrafe(-4,1,50);
            turnDuckStart(.5);
            timeStrafe(.1,6);
            turnDuckStop();
            if (ParkSide.equals("A")) {
                encoderDrive(.4,1,5);
                encoderTurn(.2,-1,90);
                timeStrafe(-.2,1.5);
                encoderDrive(.8,1,94);
            } else if (ParkSide.equals("B")) {
                encoderDrive(.3,1,24);
                encoderTurn(.2,-1,90);
                encoderDrive(.3,-1,20);
            }
        }
    }
}