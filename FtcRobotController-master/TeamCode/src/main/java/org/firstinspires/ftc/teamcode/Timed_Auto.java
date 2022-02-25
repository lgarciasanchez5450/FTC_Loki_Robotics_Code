package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Backup Auto",group="Time")
public class Timed_Auto extends AutoAbstract {

    public void runOpMode() {
        setupMotors();
        String side = getAutoType();
        String ParkSide = getParkSide();
    }
}
