package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//@Disabled

public abstract class AutoAbstract extends LinearOpMode {

    static final double pi = Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 10;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.75;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * pi);

    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor duckMotor = null;
    private String Side;
    private int speed;
    public void setupMotors() {
        lf  = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb  = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        duckMotor = hardwareMap.get(DcMotor.class, "duck");
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

        newlfTarget = lf.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() + (int)(Inches * COUNTS_PER_INCH);

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
    public void encoderTurn(double speed, double direction, double angle) {
        //speed is the speed at which the robot moves
        //direction clarifies whether it is moving forwards or backwards
        //newTargetInches is how far to move
        //This is all new and not tested yet

        angle /= 5.637 ;//5.637 is the magical number that the angle needs to be divided by to have ultimate supremacy over the universe(no joke DONT DELETE THIS NUMBER OR I WILL FIND YOU AND DO SOMETHING BAD LIKE STOMP MY FOOT ON THE FLOOR FLOOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(angle * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() - (int)(angle * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() + (int)(angle * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() - (int)(angle * COUNTS_PER_INCH);

        lf.setTargetPosition(newlfTarget);
        rf.setTargetPosition(newrfTarget);
        lb.setTargetPosition(newlbTarget);
        rb.setTargetPosition(newrbTarget);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        lf.setPower(Math.abs(speed) *  direction);
        rf.setPower(Math.abs(speed) * -direction);
        lb.setPower(Math.abs(speed) *  direction);
        rb.setPower(Math.abs(speed) * -direction);

        while (lf.isBusy() && rf.isBusy() && lb.isBusy() && rb.isBusy()) {
            telemetry.addData("Hello","Da Robot is mooing!");
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
    public void encoderStrafe(double speed, double direction, double newTargetInches) {
        //speed is the speed at which the robot moves
        //direction clarifies whether it is moving forwards or backwards
        //newTargetInches is how far to move
        //This is all new and not tested yet
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(newTargetInches * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() - (int)(newTargetInches * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() - (int)(newTargetInches * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() + (int)(newTargetInches * COUNTS_PER_INCH);

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
        rf.setPower(Math.abs(speed) * -direction);
        lb.setPower(Math.abs(speed) * -direction);
        rb.setPower(Math.abs(speed) * direction);

        while (lf.isBusy() && rf.isBusy() && lb.isBusy() && rb.isBusy()) {
            telemetry.addData("Hello","Da Robot is mooing!");
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

