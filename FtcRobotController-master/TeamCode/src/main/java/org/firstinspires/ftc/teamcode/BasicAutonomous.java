package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name="Basic Auto",group= "stuff")
public class BasicAutonomous extends Auto_Abstract {

    static  final double LF_MULTIPLIER = 1;//(523/556) * 1;
    static final double RF_MULTIPLIER = 1;//(523/523) * 1;
    static final double RB_MULTIPILER = 1;//(523/536) * (546/548);
    static final double LB_MULTIPLIER = 1;//(523/540) * (546/555);

    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;


    //private DcMotor duckMotor = null;
    static final double pi = Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 20;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.97638;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * pi);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.2;
    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lf  = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb  = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        //duckMotor = hardwareMap.get(DcMotor.class, "duck");
        //motors are 20:1
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.REVERSE);
        //duckMotor.setDirection(DcMotor.Direction.FORWARD);

        //set motor power to 0
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        //duckMotor.setPower(0);



        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.addData("Build Version: ","Thousand Winds V4");

        //telemetry.update();

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "GUD 2 GOU: (Initialized)");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //encoderDrive(TURN_SPEED,1,20); //theoretically should move forwards at DRIVE_SPEED 20 inches

        encoderDrive(0.1, -1, 48 );

        //encoderDrive(0.4, 1, 32);



        sleep(10000);

        //encoderStrafe(TURN_SPEED,1,20);










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
        //rf.setTargetPosition(newrfTarget);
        //lb.setTargetPosition(newlbTarget);
        rb.setTargetPosition(newrbTarget);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        lf.setPower(Math.abs(speed) * direction);
        //rf.setPower(Math.abs(speed) * direction);
        //lb.setPower(Math.abs(speed) * direction);
        rb.setPower(Math.abs(speed) * direction);

        while (lf.isBusy() || rb.isBusy()) {
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

        lf.setPower(Math.abs(speed) * direction * LF_MULTIPLIER);
        rf.setPower(Math.abs(speed) * -direction * RF_MULTIPLIER);
        lb.setPower(Math.abs(speed) * direction * LB_MULTIPLIER);
        rb.setPower(Math.abs(speed) * -direction * RB_MULTIPILER);

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