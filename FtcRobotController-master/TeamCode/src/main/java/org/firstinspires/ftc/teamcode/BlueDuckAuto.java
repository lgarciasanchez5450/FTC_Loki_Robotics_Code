package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Duck")
public class BlueDuckAuto extends AutoAbstract{

    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        timeStrafe(.3,1.5);
        timeDrive(-.2, 1);
        turnDuckStart(-.3);
        timeDrive(-.08,5);
        turnDuckStop();
        timeStrafe(.5,1.6);
        timeDrive(-.3,2);
        timeDrive(0.1,1);
    }
}
