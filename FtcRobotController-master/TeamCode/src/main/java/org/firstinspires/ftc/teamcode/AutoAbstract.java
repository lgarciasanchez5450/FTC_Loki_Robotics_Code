package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;
//@Disabled

public abstract class AutoAbstract extends LinearOpMode {

    static final double pi = Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 19.2;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.75;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * pi);

    private boolean xdown , ydown , adown , bdown = false;
    private boolean px , py , pa , pb = false;
    private boolean x , y , a , b = false;
    private Gamepad[] controllers = {gamepad1, gamepad2};
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    //private DcMotor extraMotor = null;
    private String Side;
    private int speed;
    public void poll() {
        poll(1);
    }
    public void poll(int controller_type) {
        x = controllers[controller_type].x;
        y = controllers[controller_type].y;
        a = controllers[controller_type].a;
        b = controllers[controller_type].b;
        xdown = x && !px;
        ydown = y && !py;
        adown = a && !pa;
        bdown = b && !pb;
        px = x;
        py = y;
        pa = a;
        pb = b;
    }

    /*
     * Rate limit gamepad button presses to every 500ms.
     */
    private final static int GAMEPAD_LOCKOUT = 500;

    //Deadline gamepadRateLimit  = new Deadline(GAMEPAD_LOCKOUT, TimeUnit.MILLISECONDS);



    public void setupMotors() {
        lf  = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb  = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        //duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);
    }

    public long getDelay() {
        long delay = 0;
        boolean done = false;
        String button;
        while (!done) {
            telemetry.addLine("How long should I wait before starting?");
            if (delay >= 1000) {
                telemetry.addData("Current Delay in Seconds", delay/1000);
            } else {
                telemetry.addData("Current Delay in milliseconds: ",delay);
            }
            telemetry.addLine("X: +500 ms");
            telemetry.addLine("Y: -100 ms");
            telemetry.addLine("B: Reset Delay");
            telemetry.addLine("A: Done!");
            telemetry.update();

            poll();

            if (xdown) {
                delay += 500;
            }
            else if (ydown && delay != 0) {
                delay -= 100;
            }
            else if (adown) {
                done = true;
            }
            else if (bdown) {
                delay = 0;
            }
        }
        return delay;
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

        //angle /= 5.637 ;//5.637 is the magical number that the angle needs to be divided by to have ultimate supremacy over the universe(no joke DONT DELETE THIS NUMBER OR I WILL FIND YOU AND DO SOMETHING BAD LIKE STOMP MY FOOT ON THE FLOOR FLOOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        double inches = ((2*Math.sqrt(56))*Math.PI)/(360/angle);
        int newlfTarget;
        int newrfTarget;
        int newlbTarget;
        int newrbTarget;

        newlfTarget = lf.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH * direction);
        newrfTarget = rf.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH * direction);
        newlbTarget = lb.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH * direction);
        newrbTarget = rb.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH * direction);

        lf.setTargetPosition(newlfTarget);
        rf.setTargetPosition(newrfTarget);
        lb.setTargetPosition(newlbTarget);
        rb.setTargetPosition(newrbTarget);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        lf.setPower(speed * direction);
        rf.setPower(-1*speed * direction);
        lb.setPower(speed * direction);
        rb.setPower(-1*speed * direction);

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
    public String getAutoType() {
        String button;
        telemetry.addLine("What are we going to do?");
        telemetry.addLine("A: Red-Park");
        telemetry.addLine("B: Red-Carousel-Park");
        telemetry.addLine("X: Red-Hub-Park");
        telemetry.addLine("Y: Red-Carousel-Hub-Park");
        telemetry.update();
        button = getPress();
        telemetry.addLine("You pressed: " + button + " in getAutoType()");
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
    //public void turnDuckStart(double power) {
    //    extraMotor.setPower(power);
    //}
    //public void turnDuckStop() {
    //    extraMotor.setPower(0);
    //}

    public int getDuckPos() {
        return 1;
    }
    public String getParkSide() {
        String button;
        telemetry.addLine("Park Place?");
        telemetry.addLine("A: <option_a>");
        telemetry.addLine("B: <option_b>");
        telemetry.update();
        button = getPress();
        telemetry.addLine("You pressed: " + button);
        if (button.equals("X") || button.equals("Y")) {
            telemetry.addLine("R U DUM!?! That's not an expected answer please try again");
            telemetry.update();
            sleep(1000);
            button = getParkSide();
        }
        telemetry.update();
        return button;

    }


}

