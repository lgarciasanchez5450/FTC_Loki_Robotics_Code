package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "Flat Bot motor test 2.0")
public class flatBotMotorTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor lb = null;
    private DcMotor rf = null;
    private DcMotor rb = null;




    @Override
    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class,"lf");
        lb = hardwareMap.get(DcMotor.class,"lb");
        rf = hardwareMap.get(DcMotor.class,"rf");
        rb = hardwareMap.get(DcMotor.class,"rb");

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                lf.setPower(1);
                telemetry.addData("LF running", lf.getCurrentPosition());
            } else {
                lf.setPower(0);
            }

            if (gamepad1.b) {
                lb.setPower(1);
                telemetry.addData("LB running", lb.getCurrentPosition());
            } else {
                lb.setPower(0);
            }

            if (gamepad1.y) {
                rf.setPower(1);
                telemetry.addData("RF running", rf.getCurrentPosition());
            } else {
                rf.setPower(0);
            }

            if (gamepad1.x) {
                rb.setPower(1);
                telemetry.addData("RB running", rb.getCurrentPosition());
            } else {
                rb.setPower(0);
            }

            telemetry.update();
        }
    }
}
