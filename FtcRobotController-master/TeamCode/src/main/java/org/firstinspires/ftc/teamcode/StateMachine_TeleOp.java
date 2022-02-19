package org.firstinspires.ftc.teamcode;

import android.transition.Slide;

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


@TeleOp(name = "State Machine TeleOp")

public class StateMachine_TeleOp extends TeleOp_Abstract {

    public enum LiftState{
        LIFT_READY,
        LIFT_START,
        LIFT_MID,
        LIFT_EXTEND,
        LIFT_AMONGUS,
        LIFT_DUMP,
        LIFT_RETRACT
    };

    public enum DumpState{
        IDLE,
        DUMP
    };

    LiftState liftState = LiftState.LIFT_READY;
    DumpState dumpState = DumpState.IDLE;


    // CRServo topSlide, tape;
    boolean bumperButtonState = true;
    boolean grabIsActive = true;
    boolean inState = false;
    boolean inActive = false;
    boolean override = false;

    // boolean capState = false;
    // boolean capActive = false;
    // int padPressed;
    // int block = 1000;
    // int distance = block * padPressed;
    ElapsedTime liftTimer = new ElapsedTime();
    ElapsedTime dumpTimer = new ElapsedTime();

    final double DUMP_IDLE = .2; // the idle position for the dump servo
    final double DUMP_DEPOSIT = .635; // the dumping position for the dump servo

    // the amount of time the dump servo takes to activate in seconds
    final double DUMP_TIME = 1.25;


    final int LIFT_LOW = 0; // the low encoder position for the lift
    final int LIFT_HIGH = 1425;

    LokiHardwareBot robot = new LokiHardwareBot();

    @Override
    public void init() {
        robot.init(hardwareMap);

        liftTimer.reset();
    }

    @Override
    public void loop() {

        liftAuto();
        DumpServo();
        SlideMotor();


//        switch (dumpState){
//            case IDLE:
//                if (gamepad2.a && dumpTimer.seconds() >= .15) {
//                    robot.dumpServo.setPosition(DUMP_IDLE);
//                    dumpState = dumpState.DUMP;
//                    dumpTimer.reset();
//                    telemetry.addLine("Idle");
//                }
//                break;
//
//            case DUMP:
//                if (gamepad2.a && dumpTimer.seconds() >= .15) {
//                    robot.dumpServo.setPosition(DUMP_DEPOSIT);
//                    dumpState = dumpState.IDLE;
//                    dumpTimer.reset();
//                    telemetry.addLine("Dump");
//                }
//                break;
//        }

//        double drive;
//        double strafe;
//        double rotate;
//
//        drive = -gamepad1.left_stick_y * .75;
//        strafe = gamepad1.left_stick_x * .75;
//        rotate = gamepad1.right_stick_x * .75;
//
//        double driveInput = drive;
//        double strafeInput = strafe;
//        double rotateInput = rotate;
//
//        robot.duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        robot.slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        //robot.slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//
//        //telemetry.addData("robot.slideMotor", robot.slideMotor.getCurrentPosition());
//        telemetry.addData("robot.lf", robot.lf.getCurrentPosition());
//        telemetry.addData("robot.rf", robot.rf.getCurrentPosition());
//        telemetry.addData("robot.lb", robot.lb.getCurrentPosition());
//        telemetry.addData("robot.rb", robot.rb.getCurrentPosition());
//
//
//        if (gamepad1.left_bumper) {
//            driveInput = drive / 2;
//            strafeInput = strafe / 2;
//            rotateInput = rotate / 2;
//            robot.lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            robot.rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            robot.lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            robot.rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        } else if (gamepad1.right_bumper) {
//            driveInput = drive * 2;
//            strafeInput = strafe * 2;
//            rotateInput = rotate * 2;
//        } else {
//            driveInput = drive;
//            strafeInput = strafe;
//            rotateInput = rotate;
//        }
//
//
//        robot.lf.setPower(driveInput + strafeInput - rotateInput);
//        robot.lb.setPower(driveInput - strafeInput - rotateInput);
//        robot.rf.setPower(driveInput - strafeInput + rotateInput);
//        robot.rb.setPower(driveInput + strafeInput + rotateInput);

        //robot.slideMotor.setPower(-gamepad2.left_stick_y);

//        if (gamepad2.dpad_left) {
//            robot.duckMotor.setPower(.53);
//        }
//        else if (gamepad2.dpad_right) {
//            robot.duckMotor.setPower(-.53);
//        }
//        else {
//            robot.duckMotor.setPower(0);
//        }

//        if (-gamepad2.right_stick_y < 0){
//            cap.setPosition(cap.getPosition() + .005);
//        }
//        else if (-gamepad2.right_stick_y > 0){
//            cap.setPosition(cap.getPosition() - .005);
//        }
//        else if (gamepad2.dpad_up){
//            cap.setPosition(0.2);
//        }
//        else if (gamepad2.dpad_down){
//            cap.setPosition(.7);
//        }


//        if (gamepad2.left_bumper && !inState) {
//            inActive = !inActive;
//        }
//
//        if (inActive) {
//            robot.harvester1.setPower(-.525);
//            robot.harvester2.setPower(-.525);
//        }
//        else if (gamepad2.right_bumper) {
//            robot.harvester1.setPower(.2);
//            robot.harvester2.setPower(.2);
//        }
//        else if (gamepad2.y) {
//            robot.harvester1.setPower(-.3);
//            robot.harvester2.setPower(-.3);
//        }
//        else if (gamepad2.x) {
//            robot.harvester1.setPower(-.75);
//            robot.harvester2.setPower(-.75);
//        }
//        else {
//            robot.harvester1.setPower(0);
//            robot.harvester2.setPower(0);
//        }

        inState = gamepad2.left_bumper;
        bumperButtonState = gamepad2.a;

    }

    void liftAuto(){
        switch (liftState){

            case LIFT_READY:
                if (gamepad2.b){
                    liftState = LiftState.LIFT_START; //Begin lifting process when press B
                }
                else if (Math.abs(robot.slideMotor.getCurrentPosition()) < 1430){ //If it is within slide bounds...
                    robot.slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.slideMotor.setPower(-gamepad2.left_stick_y);
                }
                else {
                    robot.slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);      //Run only one way? Maybe back down to the bounds?
                    robot.slideMotor.setPower(-(Math.abs(-gamepad2.left_stick_y)));    //FIXME: Add comments
                }
                break;
            case LIFT_START: //Extend fully
                // robot.slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                //Go to highest position
                robot.slideMotor.setTargetPosition(LIFT_HIGH);
                robot.slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.slideMotor.setPower(1);
                liftState = LiftState.LIFT_MID;

                telemetry.addLine("Phase: Lift Start");
                break;
            case LIFT_MID: //GET SERVO IN READY TO DUMP POSITION
                telemetry.addLine("Phase: Lift MID");
                if (Math.abs(robot.slideMotor.getCurrentPosition()) > 100) {
                    robot.dumpServo.setPosition(.43);
                    liftState = LiftState.LIFT_EXTEND;
                }
                break;
            case LIFT_EXTEND: //STOP MOTOR
                if ((Math.abs(robot.slideMotor.getCurrentPosition() - LIFT_HIGH) < 15)) {
                    robot.slideMotor.setPower(0);
                    liftState = LiftState.LIFT_AMONGUS;
                }
                telemetry.addLine("Phase: Lift Extend");

                break;
            case LIFT_AMONGUS: //DUMPING
                telemetry.addLine("Phase: Begin Lift Dumping");
                if (gamepad2.b){
                    robot.dumpServo.setPosition(DUMP_DEPOSIT);
                    liftTimer.reset();
                    liftState = LiftState.LIFT_DUMP;
                }
                break;
            case LIFT_DUMP: //RESET SERVO AFTER "DUMP_IDLE SECONDS
                telemetry.addLine("Phase: Lift Dumping");

                if (liftTimer.seconds() >= DUMP_TIME) {
                    robot.dumpServo.setPosition(DUMP_IDLE);
                    robot.slideMotor.setTargetPosition(0);
                    robot.slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.slideMotor.setPower(-1);
                    liftState = LiftState.LIFT_RETRACT;
                }
                break;
            case LIFT_RETRACT: //STOP MOTOR AFTER RETRACTING
                telemetry.addLine("Phase: Lift Retracting");

                if (Math.abs(robot.slideMotor.getCurrentPosition() - LIFT_LOW) < 15) {
                    robot.slideMotor.setPower(0);
                    liftState = LiftState.LIFT_READY;
                }
                break;

        }

        telemetry.addData("Slide Position: ", robot.slideMotor.getCurrentPosition());

        if (gamepad2.left_stick_y != 0 && liftState != LiftState.LIFT_READY ){
            liftState = LiftState.LIFT_READY;
        }
    }
}