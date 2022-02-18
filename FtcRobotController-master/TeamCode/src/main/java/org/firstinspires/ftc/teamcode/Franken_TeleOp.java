package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="Franken TeleOp", group="Iterative Opmode")
public class Franken_TeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor rb = null;
    private DcMotor lb = null;
    private DcMotor harvester_left = null;
    private DcMotor harvester_right = null;
    private DcMotor duck_motor = null;
    private DcMotor arm = null;
    private Servo dumper =null;
    private double SpeedFactor = 2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "We're reving to go!");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");
        harvester_left = hardwareMap.get(DcMotor.class, "Harvester1");
        harvester_right = hardwareMap.get(DcMotor.class, "Harvester2");
        duck_motor = hardwareMap.get(DcMotor.class, "duckMotor");
        arm = hardwareMap.get(DcMotor.class, "SlideMotor");
        dumper = hardwareMap.get(Servo.class, "dumpServo");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        harvester_left.setDirection(DcMotor.Direction.FORWARD);
        harvester_right.setDirection(DcMotor.Direction.FORWARD);
        duck_motor.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double lfPower;
            double rfPower;
            double rbPower;
            double lbPower;
            double denominator;

            if (gamepad1.a) {
                SpeedFactor = 1;
            } else if (gamepad1.b) {
                SpeedFactor = 0.5;
            } else if (gamepad1.x) {
                SpeedFactor = 0.25;
            }

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x * 1.2;

            denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn),1);

            lfPower = (drive + turn + strafe)/denominator;
            rfPower = (drive - turn - strafe)/denominator;
            rbPower = (drive - turn + strafe)/denominator;
            lbPower = (drive + turn - strafe)/denominator;

            lfPower *= SpeedFactor;
            rfPower *= SpeedFactor;
            rbPower *= SpeedFactor;
            lbPower *= SpeedFactor;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            lf.setPower(lfPower);
            rf.setPower(rfPower);
            rb.setPower(rbPower);
            lb.setPower(lbPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", lfPower, rfPower, rbPower, lbPower);
            telemetry.update();

            if (gamepad2.left_bumper) {
                harvester_left.setPower(0.5);
            } else if (gamepad2.right_bumper) {
                harvester_left.setPower(-0.5);
            }

            if (gamepad2.left_trigger > 0.5) {
                harvester_right.setPower(0.5);
            } else if (gamepad2.right_trigger > 0.5) {
                harvester_right.setPower(-0.5);
            }

            if (gamepad2.dpad_left){
                duck_motor.setPower(-0.5);
            } else if (gamepad2.dpad_right){
                duck_motor.setPower(0.5);
            }

            if (gamepad2.left_stick_y > 0.5) {
                arm.setPower(0.5);
            } else if (gamepad2.left_stick_y < -0.5) {
                arm.setPower(-0.5);
            }

            if (gamepad2.a) {
                dumper.setPosition(1);
            } else if (gamepad2.b) {
                dumper.setPosition(0);
            }

        }
    }
}



