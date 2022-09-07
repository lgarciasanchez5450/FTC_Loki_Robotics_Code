package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Test Opmoad1.1",group= "Down Side")
public class GoCrazyAuto extends AutoAbstract {


    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        //encoderDrive(.2,1,24);
        timeStrafe(-.3, 3);
        timeDrive(-0.3, 3);
        timeDrive(.2,.8);
        timeTurn(-.2,3.2);
        timeStrafe(-.3,2);
        timeDrive(-.3,1.2);
        turnDuckStart(.6);
        timeDrive(-.1, 5);
        turnDuckStop();
        timeDrive(.2, 2);
        //encoderStrafe(.3,1,24);
        //encoderStrafe(.3,-1,24);
        //encoderTurn(.3,1,360);
        //encoderTurn(.3,-1,360);
    }
}
