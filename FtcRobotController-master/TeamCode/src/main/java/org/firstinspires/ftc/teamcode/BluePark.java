package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "Turn 360 v0.1",group = "BLUE")
public class BluePark extends AutoAbstract{

    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        encoderTurn(.3,1,600);
        sleep(1000);
        encoderDrive(.3,1,20);
        sleep(1000);
        encoderStrafe(.3,1,20);

    }
}
