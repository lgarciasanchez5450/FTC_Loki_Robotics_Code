package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled

@Autonomous(name="Blue Warehouse Auto",group= "Blue Side")
public class BlueWarehouseAuto extends LinearOpMode {


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
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.REVERSE);
        duckMotor.setDirection(DcMotor.Direction.FORWARD);

        //set motor power to 0
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        duckMotor.setPower(0);



        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "F*** the Encoders");
        telemetry.update();

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//apparently not needed but just in case the encoders decide to bully the robot
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "GUD 2 GOU: (Initialized)");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        timeStrafe( -.2,1);//if you want to put time in decimals then add a (long) before the num
        timeStrafe(.2,2);
        timeDrive (.5,3);
        timeDrive (.2,2);

    }

    public void timeDrive(double power, double timeSecs) {
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
    public void timeStrafe(double power, double timeSecs) {
        /* if power is positive it will strafe right, if negative it will strafe left */
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
    public void timeTurn(double power, double timeSecs) {
        /* if power is positive it will turn clockwise, if negative turn is counter-clockwise */
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(-power);
        sleep((long)(timeSecs * 1000));
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }
    public void turnDuckStart(double power) {
        duckMotor.setPower(power);
    }
    public void turnDuckStop() {
        duckMotor.setPower(0);
    }
}