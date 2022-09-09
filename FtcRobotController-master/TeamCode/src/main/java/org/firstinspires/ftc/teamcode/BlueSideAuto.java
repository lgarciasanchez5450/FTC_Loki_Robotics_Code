package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name = "Blue Side Auto",group = "BLUE")
public class BlueSideAuto extends AutoAbstract {

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
                //pass
            } else if (ParkSide.equals("B")) {
                //pass
            }
        } else if (Side.equals("B")) {//Blue-Carousel-Park
            //Blue Side
            if (ParkSide.equals("A")) {
                //pass
            } else if (ParkSide.equals("B")) {
                //pass
            }

        } else if (Side.equals("X")) {//Blue-Hub-Park
            //Blue Side
            if (ParkSide.equals("A")) {
                //pass
            } else if (ParkSide.equals("B")) {
                //pass
            }


        } else if (Side.equals("Y")) {//Blue-Carousel-Hub-Park
            //Blue Side
            if (ParkSide.equals("A")) {
                //pass
            } else if (ParkSide.equals("B")) {
                //pass
            }
        }
    }
}