package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "Red Side Auto",group = "RED")
public class RedSideAuto extends AutoAbstract {


    @Override
    public void runOpMode() {

        //
        String Side = getAutoType();
        waitForStart();

        if (Side.equals("A")) {//Red-Park
            encoderDrive(.5,1,24);
            encoderStrafe(.5,-1,30);
            encoderDrive(.5,1,24);

        } else if (Side.equals("B")) {//Red-Carousel-Park
            encoderDrive(.5,-1,24);
            turnDuckStart(.5);
            timeDrive(-.1,6);
            turnDuckStop();
            encoderStrafe(.3,-1,24);
            encoderDrive(.3,-1,22);

        } else if (Side.equals("X")) {//Red-Hub-Park
            int DuckPos = getDuckPos();
            encoderTurn(.1,-1,30);
            encoderDrive(.3,1,30);
            //make arm go up
            //release our thingy onto the hub
            //make arm go down
            sleep(1500);
            encoderDrive(.3,-1,30);
            encoderTurn(.1,1,30);
            encoderTurn(.1,1,90);
            encoderDrive(.5,1,66);

        } else if (Side.equals("Y")) {//Red-Carousel-Hub-Park
            int DuckPos = getDuckPos();
            encoderTurn(.1,-1,30);
            encoderDrive(.3,1,30);
            //make arm go up
            //release our thingy onto the hub
            //make arm go down
            sleep(1500);
            encoderDrive(.3,-1,30);
            encoderTurn(.1,1,30);
            encoderDrive(.5,-1,48);
            turnDuckStart(.5);
            timeDrive(-.1,6);
            turnDuckStop();
            timeStrafe(.5,2);
            encoderDrive(.8,1,94);
        }

    }

}
