package org.firstinspires.ftc.teamcode;
//John & Leo are great redstone engineers
//Andy is not a redstone engineer
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic Freight OpMode", group="Iterative Opmode")
//@Disabled
public class OpModeLokiFreight extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    private DcMotor duckMotor = null;
    private DcMotor armMotor1 = null;
    private DcMotor harvester = null;
    private Servo ClawServo = null;
    private double clawPos = 0;
    private double SpeedFactor = 2;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lf  = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb  = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        duckMotor = hardwareMap.get(DcMotor.class,"duck");
        armMotor1 = hardwareMap.get(DcMotor.class,"Arm1");
        harvester = hardwareMap.get(DcMotor.class,"Harvester");
        //ClawServo = hardwareMap.get(Servo.class,"claw");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.REVERSE);

        //armMotor2.setDirection(DcMotor.Direction.REVERSE);



        armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "GUD 2 GOU: (Initialized)");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each dr
        // set up vars for each wheel's power

        double lfp;
        double rfp;
        double lbp;
        double rbp;
        double denominator;
        double armP = 0;
        double harvesterP = 0;

        //set up vars for other motors' power
        double duckp;

        //Gamepad 1 Controls
        //Robot Move Speed
        if (gamepad1.a) {
            SpeedFactor = 6;
        } else if (gamepad1.b) {
            SpeedFactor = 2.5;
        } else if (gamepad1.x) {
            SpeedFactor = 1.66666;
        }
        //Duck Spinner
        duckp = 0;
        if (gamepad1.left_bumper) {
            duckp += .55;
        }
        if (gamepad1.right_bumper) {
            duckp -= .55;
        }

        //Gamepad 2 Controls
        //Arm Raising and Lowering

        armP = Math.pow(gamepad2.left_stick_y,2) * .4;


        //Claw Opening and Closing
        harvesterP = gamepad2.right_stick_y *.8;

        //clawPos = Range.clip(clawPos,0,1);
        //ClawServo.setPosition(clawPos);
        telemetry.addData("Claw at Pos:",clawPos);

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double strafe = -gamepad1.left_stick_x * 1.2;
        double drive  = gamepad1.left_stick_y;
        double turn   = -gamepad1.right_stick_x;

        denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn),1);

        lfp = (drive + turn + strafe) / denominator;
        rfp = (drive - turn - strafe) / denominator;
        lbp = (drive + turn - strafe) / denominator;
        rbp = (drive - turn + strafe) / denominator;

        lfp /= SpeedFactor;
        rfp /= SpeedFactor;
        lbp /= SpeedFactor;
        rbp /= SpeedFactor;

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower  = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        lf.setPower(lfp);
        rf.setPower(rfp);
        lb.setPower(lbp);
        rb.setPower(rbp);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", lfp + lbp, rfp + rbp);
        telemetry.update();


        duckMotor.setPower(duckp);
        armMotor1.setPower(armP);
        harvester.setPower(harvesterP);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    public void SpeedThing() {
        boolean running = true;
        while (running) {
            telemetry.addData("Please select each ", "Bop");
            telemetry.update();

        }
    }
    @Override
    public void stop() {
    }

}