package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "MotorTest")
public class MotorTest extends OpMode {
    LokiHardwareBot robot = new LokiHardwareBot();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            robot.lf.setPower(1);
            telemetry.addLine("LF Running");
        }else{
            robot.lf.setPower(0);
        }

        if (gamepad1.b){
            robot.lb.setPower(1);
            telemetry.addLine("LB Running");

        }else{
            robot.lb.setPower(0);
        }

        if (gamepad1.x){
            robot.rf.setPower(1);
            telemetry.addLine("RF Running");
        }else{
            robot.rf.setPower(0);
        }

        if(gamepad1.y){
            robot.rb.setPower(1);
            telemetry.addLine("RB Running");
        }else{
            robot.rb.setPower(0);
        }

        robot.slideMotor.setPower(gamepad2.left_stick_y);
        telemetry.addData("SlideMotor Position", robot.slideMotor.getCurrentPosition());


        if(gamepad2.b){
            robot.duckMotor.setPower(1);
            telemetry.addLine("DuckMotor Running");
        }else{
            robot.duckMotor.setPower(0);
        }

        if(gamepad2.x){
            robot.harvester2.setPower(1);
            telemetry.addLine("Harvester2 Running");
        }else{
            robot.harvester2.setPower(0);
        }

        if (gamepad2.y){
            robot.harvester1.setPower(1);
            telemetry.addLine("Harvester1 Running");
        }else{
            robot.harvester1.setPower(0);
        }

    }
}
