package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class TeleOp_Abstract extends OpMode
{
    public LokiHardwareBot robot = new LokiHardwareBot();
    public double SpeedFactor = 1;
    private ElapsedTime runtime = new ElapsedTime();

    public void initHardwareBot(){
        robot.init(hardwareMap);
    }

    private void speedMod() {
        if (gamepad1.a) {
            SpeedFactor = 1;
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);

        } else if (gamepad1.b) {
            SpeedFactor = 0.5;
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        } else if (gamepad1.x) {
            SpeedFactor = 0.25;
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        }

        telemetry.addData("SpeedMod", SpeedFactor);
    }

    public void nonTrigMecanum(){
        double lfPower;
        double rfPower;
        double rbPower;
        double lbPower;
        double denominator;

        speedMod();
        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn  =  -gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x * 1.2;

        denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn),1);

        lfPower = (drive + turn + strafe)/denominator;
        rfPower = (drive - turn - strafe)/denominator;
        rbPower = (drive - turn + strafe)/denominator;
        lbPower = (drive + turn - strafe)/denominator;

        lfPower *= SpeedFactor;
        rfPower *= SpeedFactor;
        rbPower *= SpeedFactor;
        lbPower *= SpeedFactor;

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        robot.lf.setPower(lfPower);
        robot.rf.setPower(rfPower);
        robot.rb.setPower(rbPower);
        robot.lb.setPower(lbPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", lfPower, rfPower, rbPower, lbPower);
    }

    public void DuckMotor(){
        if (gamepad2.dpad_left){
            robot.duckMotor.setPower(-0.5);
        } else if (gamepad2.dpad_right){
            robot.duckMotor.setPower(0.5);
        }else{
            robot.duckMotor.setPower(0); //FIXME John you need to add auto stops for when not pressing buttons in the future
        }
    }

    public void SlideMotor(){
        robot.slideMotor.setPower(gamepad1.right_stick_y);
    }

    public void DumpServo(){
        if (gamepad2.a) {
            robot.dumpServo.setPosition(1);
        } else if (gamepad2.b) {
            robot.dumpServo.setPosition(0);
        }
    }

    public void HarvesterMotor(){

    }

}
