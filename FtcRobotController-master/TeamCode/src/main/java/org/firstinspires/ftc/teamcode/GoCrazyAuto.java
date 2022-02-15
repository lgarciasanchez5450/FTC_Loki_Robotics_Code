package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Test Opload",group= "Down Side")
public class GoCrazyAuto extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor duckMotor = null;

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    //static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lf  = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb  = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        duckMotor = hardwareMap.get(DcMotor.class, "duck");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.FORWARD);
        duckMotor.setDirection(DcMotor.Direction.FORWARD);

        //set motor power to 0
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        duckMotor.setPower(0);




        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("WARNING WARNING WARNING","BE PREPARED TO STOP AUTO IMMEDIATELY!!!");
        waitForStart();

        timeDrive(-.8,2);//if you want time in decimals then add a (long) before the decimal
        timeDrive(1,5);
        timeDrive(-.2,2);
        timeTurn(.5,1);
        sleep(4000);
        timeTurn(1,3);
        telemetry.addData("Done!", "(*_*)/^^^^");
        telemetry.update();




    }

    public void timeDrive(double power, double timeSecs) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);
        sleep((long)(timeSecs * 1000));
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
    public void timeStrafe(double power, double timeSecs) {
        /* if power is positive it will strafe right, if negative it will strafe left */
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
        sleep((long)(timeSecs * 1000));
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
    public void timeTurn(double power, long timeSecs) {
        /* if power is positive it will turn clockwise, if negative turn is counter-clockwise */
        lf.setPower(-power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(power);
        sleep((long)(timeSecs * 1000));
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
    public void turnDuck(double power, double timeSecs) {
        duckMotor.setPower(power);
        sleep((long)(timeSecs * 1000));
        duckMotor.setPower(0);
    }
}