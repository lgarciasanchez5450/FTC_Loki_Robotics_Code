package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LokiHardwareBot;


@TeleOp(name="LED Test 1",group= "Test Scripts")
public class LEDTest extends OpMode {

    LokiHardwareBot robot = new LokiHardwareBot();

    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    @Override
    public void loop() {

        //robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);

        if (gamepad1.a){
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);

            telemetry.addData("A pressed", robot.lights.getConnectionInfo());
        }
        if (gamepad1.b){
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            telemetry.addData("B pressed", robot.lights.getConnectionInfo());

        }
        if(gamepad1.x){
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            telemetry.addData("X pressed", robot.lights.getConnectionInfo());
            //telemetry.addData("Current Lights: ", RevBlinkinLedDriver.BlinkinPattern.values());
        }
        if(gamepad1.y){
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
            telemetry.addData("Y pressed", robot.lights.getConnectionInfo());
        }

        telemetry.update();
    }
}
