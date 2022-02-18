package org.firstinspires.ftc.teamcode.archive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AutoAbstract;

//@Disabled
@Autonomous(name = "Turn 360 v0.2",group = "BLUE")
public class BluePark extends AutoAbstract {

    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        encoderTurn(.3,1,360);


    }
}
