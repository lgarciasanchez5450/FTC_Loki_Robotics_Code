package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.checkerframework.checker.units.qual.Speed;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp(name="Franken TeleOp 2.1", group="Linear Opmode")
public class Main_TeleOp extends LinearOpMode {

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
    public RevBlinkinLedDriver lights;
    private static final double STRAFE_ASSISTANCE = 1.2;
    //Strafe assistance helps when trying to strafe to strafe correctly,
    //not advised when trying to move at a non cardinal direction strafing.
    private static final float SPEED_TRIGGER_THRESHOLD = 0.5f;
    //Trigger Threshold manipulates the base speed and how much speedFactor can be affected by left trigger.

    // Setup a variable for each drive wheel to save power level for telemetry
    double lfPower;
    double rfPower;
    double rbPower;
    double lbPower;
    double denominator;

    private double abs(double a) {
        if (a < 0) {
            return -a;
        } else {
            return a;
        }
    }



    @Override
    public void runOpMode() {


        telemetry.addData("Status", "We're reving to go!");
        telemetry.update();


        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
        lights.resetDeviceConfigurationForOpMode();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);

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
        duck_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm = hardwareMap.get(DcMotor.class, "SlideMotor");
        dumper = hardwareMap.get(Servo.class, "dumpServo");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);

        harvester_left.setDirection(DcMotor.Direction.FORWARD);
        harvester_right.setDirection(DcMotor.Direction.REVERSE);
        duck_motor.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            //Control Speed Mod and Related Lights
            /*
            if (gamepad1.a) {
                SpeedFactor = 1;
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            } else if (gamepad1.b) {
                SpeedFactor = 0.5;
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            } else if (gamepad1.x) {
                SpeedFactor = 0.25;
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }


            telemetry.addData("SpeedMod", SpeedFactor);
            */
            SpeedFactor = Math.max(gamepad1.left_trigger,1.0f-SPEED_TRIGGER_THRESHOLD);
            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive  = Math.pow(-gamepad1.left_stick_y,STRAFE_ASSISTANCE);
            double turn   =  gamepad1.right_stick_x;
            double strafe =  gamepad1.left_stick_x * STRAFE_ASSISTANCE;

            denominator = Math.max(abs(strafe) + abs(drive) + abs(turn),1);

            lfPower = SpeedFactor * (drive + turn + strafe) / denominator;
            rfPower = SpeedFactor * (drive - turn - strafe) / denominator;
            rbPower = SpeedFactor * (drive - turn + strafe) / denominator;
            lbPower = SpeedFactor * (drive + turn - strafe) / denominator;

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

            if (gamepad2.dpad_left){
                //duck_motor.setPower(-0.5);
            } else if (gamepad2.dpad_right) {
                //duck_motor.setPower(0.5);
            }
        }
    }
}



