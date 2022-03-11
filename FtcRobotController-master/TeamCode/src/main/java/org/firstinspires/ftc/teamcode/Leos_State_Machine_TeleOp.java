package org.firstinspires.ftc.teamcode;

import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.State;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "StateMachineTest")
public class Leos_State_Machine_TeleOp extends LinearOpMode {

    static final double pi = Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 19.2;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.75;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * pi);

    public enum states {
        Idle,
        Hub,
        Hub_Ready,
        Warehouse
    }
    states state = states.Idle;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor rb = null;
    private DcMotor lb = null;

    private double SpeedMod = .5;

    @Override
    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");

        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while(opModeIsActive()) {
           thing();
        }
    }
    void thing()  {
        switch (state) {
            case Idle:
                if (gamepad1.b) {
                    state = states.Hub;
                } else {
                    double lfp;
                    double rfp;
                    double lbp;
                    double rbp;
                    double denominator;

                    double drive = gamepad1.left_stick_y;
                    double turn  =  -gamepad1.right_stick_x;
                    double strafe = -gamepad1.left_stick_x * 1.2;

                    denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn),1);

                    lfp = -(drive + turn + strafe)/denominator;
                    rfp = -(drive - turn - strafe)/denominator;
                    lbp = -(drive - turn + strafe)/denominator;
                    rbp = -(drive + turn - strafe)/denominator;

                    lfp *= SpeedMod;
                    rfp *= SpeedMod;
                    lbp *= SpeedMod;
                    rbp *= SpeedMod;

                    lf.setPower(lfp);
                    rf.setPower(rfp);
                    lb.setPower(lbp);
                    rb.setPower(rbp);
                }
                break;
            case Hub:
                encoderDrive(.4,1,40);
                state = states.Warehouse;
                break;
            case Hub_Ready:
                sleep(3000);

                break;
            case Warehouse:
                sleep(1000);
                encoderDrive(.4,-1,50);
                state = states.Idle;
                break;

        }
        if (gamepad1.y) {
            state = states.Idle;
        }
    }

    public void encoderDrive(double speed, double direction, double Inches) {
        //speed is the speed at which the robot moves
        //direction clarifies whether it is moving forwards or backwards
        //newTargetInches is how far to move
        //This is all new and not tested yet
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        newlfTarget = lf.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH * direction);
        newrfTarget = rf.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH * direction);
        newlbTarget = lb.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH * direction);
        newrbTarget = rb.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH * direction);

        lf.setTargetPosition(newlfTarget);
        rf.setTargetPosition(newrfTarget);
        lb.setTargetPosition(newlbTarget);
        rb.setTargetPosition(newrbTarget);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        lf.setPower(Math.abs(speed) * direction);
        rf.setPower(Math.abs(speed) * direction);
        lb.setPower(Math.abs(speed) * direction);
        rb.setPower(Math.abs(speed) * direction);

        while (lf.isBusy() || rf.isBusy() || lb.isBusy() || rb.isBusy()) {
            if (gamepad1.y) {
                break;
            }
            telemetry.addData("Hello","Da Robot is mooing!");
            telemetry.addData("Lf Encoder: " , lf.getCurrentPosition());
            telemetry.addData("Rf Encoder: ", rf.getCurrentPosition());
            telemetry.addData("Rb Encoder: ", rb.getCurrentPosition());
            telemetry.addData("Lb Encoder: ", lb.getCurrentPosition());
            telemetry.update();
        }
        //stop Motion
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
}