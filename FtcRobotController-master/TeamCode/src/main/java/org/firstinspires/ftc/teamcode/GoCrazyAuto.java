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
    encoderDrive(.2,1,25);
    //timeDrive(0.3, 3);
    //encoderDrive(1,-1,48);
    //encoderStrafe(.3,1,24);
    //encoderStrafe(.3,-1,24);
    //encoderTurn(.3,1,360);
    //encoderTurn(.3,-1,360);
    }
}