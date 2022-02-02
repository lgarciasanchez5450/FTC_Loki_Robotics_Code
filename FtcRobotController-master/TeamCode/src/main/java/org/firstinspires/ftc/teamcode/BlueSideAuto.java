package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "Blue Side Auto",group = "BLUE")
public class BlueSideAuto extends LinearOpMode {

    static  final double LF_MULTIPLIER = 1;
    static final double RF_MULTIPLIER = 1;
    static final double RB_MULTIPILER = 1;
    static final double LB_MULTIPLIER = 1;

    static final boolean RIGHT = true;
    static final boolean LEFT = false;

    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor duckMotor = null;
    private String Side;
    private int speed;

    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    //static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        //getSide();

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
        telemetry.addData("Status", "Praise the encoders V1");
        telemetry.update();

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Side = getSide();
        waitForStart();
        estrafe(60,false, .3);

        duckMotor.setPower(-0.3);
        estrafe(84,true,.3);



        if (Side.equals("A")) {
            telemetry.addData("Current Auto" , "Blue Side Ducks");
            //timeStrafe(-.2, 1);//if you want time in decimals then add a (long) before the decimal
            timeDrive(.4, 1.3);
            timeStrafe(.2,1);
            turnDuckStart(-.4);
            timeDrive(.05, 7);
            turnDuckStop();
            //timeStrafe(.15, 1);
            timeStrafe(-.4, 2.25);
            //timeDrive(.1, 1);
        } else if (Side.equals("B")) {
            timeStrafe( -.2,1);//if you want to put time in decimals then add a (long) before the num
            timeStrafe(.2,2.5);
            timeDrive (.5,2);
            timeDrive (.2,2);
        }

    }
    public String getSide() {
        String button;
        telemetry.addLine("What are we closer to?");
        telemetry.addLine("A: THE DUCK");
        telemetry.addLine("B: THE Warehouse");
        telemetry.update();
        button = getPress();
        telemetry.addLine("You pressed: " + button);
        telemetry.update();
        return button;

        }
    public String getPress() {
        String whatReturned = "None";
        while(whatReturned.equals("None")) {
            if (gamepad1.a) {
                whatReturned = "A";
            }
            else if (gamepad1.b) {
                whatReturned = "B";
            }
            else if (gamepad1.x) {
                whatReturned = "X";
            }
            else if (gamepad1.y) {
                whatReturned = "Y";

            }
        }
        return whatReturned;

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
    public void eDrive (double inches, boolean Direction, double power){
        int direction = 0;
        if (Direction) {
             direction = 1;
        } else if (!Direction) {
             direction = -1;
        }
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);

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
    public void eturn (double degree, boolean Direction, double power){

        int direction = 0;
        if (Direction) {
            direction = 1;
        } else if (!Direction) {
            direction = -1;
        }
        //degree /= 5.637 ;//5.637 is the magical number that the angle needs to be divided by to have ultimate supremacy over the universe(no joke DONT DELETE THIS NUMBER OR I WILL FIND YOU AND DO SOMETHING BAD LIKE STOMP MY FOOT ON THE FLOOR FLOOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(degree * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() - (int)(degree * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() + (int)(degree * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() - (int)(degree * COUNTS_PER_INCH);

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
            telemetry.addData("Hello","Da Robot is moving!");
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
    public void estrafe(double inches, boolean Direction, double power){

        int direction = 0;
        if (Direction) {
            direction = 1;
        } else if (!Direction) {
            direction = -1;
        }

        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        newrfTarget = rf.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        newlbTarget = lb.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        newrbTarget = rb.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);

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
    public void turnDuckStart(double power) {
        duckMotor.setPower(power);
    }
    public void turnDuckStop() {
        duckMotor.setPower(0);
    }
}
