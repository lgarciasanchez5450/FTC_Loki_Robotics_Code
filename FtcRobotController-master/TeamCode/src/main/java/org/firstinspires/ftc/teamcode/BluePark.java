package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "Blue Park Auto",group = "BLUE")
public class BluePark extends AutoAbstract{

    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        encoderDrive(.5,1,24);
        encoderStrafe(.5,-1,30);
        encoderDrive(.5,1,24);

    }
}
