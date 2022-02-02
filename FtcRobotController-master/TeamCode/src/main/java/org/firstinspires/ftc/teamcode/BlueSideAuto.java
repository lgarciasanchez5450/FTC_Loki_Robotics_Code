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

        String Side = getAutoType();
        String ParkSide = getParkSide();
        long Delay = getDelay();


    }
}