package org.firstinspires.ftc.teamcode;

public class RedDuckAuto extends AutoAbstract{
    @Override
    public void runOpMode() {
        setupMotors();
        waitForStart();
        timeStrafe(-.4,4);
        encoderTurn(.5,-1,90);
        timeStrafe(-4,8) ;
        timeDrive(-.3, 2);
        turnDuckStart(.5);
        timeDrive(.05,7);
        turnDuckStop();
        timeDrive(.3,3);
    }
}
