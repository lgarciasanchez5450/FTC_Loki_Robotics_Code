package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.tools.javac.tree.DCTree;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//imports


public abstract class Auto_Abstract extends LinearOpMode {
    //variables
    public DcMotor lf, rf, lb, rb, ls, duckMotor, harvester; //Define Motors In Code
    public Gamepad g1, g2;
    //ColorSensor colorRev, colorLineRev;
    //DistanceSensor sensorDistance, distanceLineRev;

    private BNO055IMU imu;

    //NormalizedRGBA colors = colorRev.getNormalizedColors();
    private float[] hsvValues = new float[3];
    //private final float values[] = hsvValues;
    //float hsvRev[] = {0F, 0F, 0F};
    //final float valuesRev[] = hsvValues;
    private final double SCALE_FACTOR = 255;





    private ElapsedTime runtime = new ElapsedTime();
    //Directions
    public static final int FORWARD = 0;
    public static final int BACKWARDS = 1;
    public static final int STRAFE_RIGHT = 2;
    public static final int STRAFE_LEFT = 3;
    //static final int FREEFORM = 4;
    public static final int UP = 0;
    public static final int DOWN = 1;
    static final int CLOSE = 0;
    static final int OPEN = 1;
    static final int PART = 2;
    private static final int SUP_PART = 3;

    static final int RED = 0;
    private static final int GREEN = 1;
    static final int BLUE = 2;
    private static final int LUM = 3;

    private static final int DUCK = 0;
    private static final int WAREHOUSE = 1;

    static final int YES = 0;
    private static final int NO = 1;

    private double gray = 0;
    private double yellow = 0;

    //Gyro Setup
    private Orientation lastAngles = new Orientation();
    private double globalAngle = .30;

    //private double Orient = 0;


    static boolean LEFT = false;
    static boolean RIGHT = true;

    static boolean GLIDE = true;
    static boolean BREAK = false;

    public static final int NO_RING = 0;
    public static final int ONE_RING = 1;
    public static final int FOUR_RING = 2;

    public DcMotor[] driveMotors = {lf, lb, rf, rb};



    //Encoder Setup
    static final
    double PI = Math.PI;
    private static final double COUNTS_PER_MOTOR_REV = 537.6;    // eg: TETRIX Motor Encoder
    private static final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 4;     // For figuring circumference
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * PI);
    //initStuff (claw, hook, wheels)

    //other functions
    /* todo: Reduce safety stop so robot stops on Red Line
       Fix Auto-correct strafe drive auto
       Implement delay for autonomi





     */
    /*Think about:
     * One autonomous for each half, then button press into the halves
     * f
     * */

    //Robot Info
    public static final double ROBOTLENGTH = 13;
    public static final double ROBOTWIDTH = 12;

    //Field Info
    public static final double TILE_WIDTH_NO_TEETH = 22.75;
    public static final double TILE_WIDTH_YES_TEETH = 23.5;


    public void delay(){
        int delay = 0;
        while (!opModeIsActive()){
            telemetry.addData("Controlls","-----");
            telemetry.addData("Gamepad 1 a:","increase delay");
            telemetry.addData("Gamepad 1 b:","decrease delay");
            if(gamepad1.a){
                delay++;
                while (gamepad1.a){
                    telemetry.addData("Delay",delay);
                }
            } else if(gamepad1.b){
                delay--;
                if(delay<0){
                    delay=0;
                } while (gamepad1.b){
                    telemetry.addData("Delay",delay);
                }
            }
            telemetry.update();
        }
    }



    private double getHeadingRad()
    {
        //get's current angle/way you are facing
        final Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return angles.firstAngle;
    }

    private double getHeadingDeg(){
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }



    int buttonsParkv2(){
        telemetry.addLine("Parking Side? (Wait 1 Second)");
        telemetry.addLine("A:Duck Side");
        telemetry.addLine("B: Warehouse Side");
        telemetry.update();
        sleep(500);
        while ((!gamepad1.a && !gamepad1.b) && !isStopRequested()){
            idle();
        }
        //while ((!gamepad1.a && !gamepad1.b) && !isStopRequested()){
        if (gamepad1.a){
            telemetry.addLine("Press Recieved");
            telemetry.update();
            sleep(1000);
            return DUCK;
        } else if (gamepad1.b){
            telemetry.addLine("Press Recieved");
            telemetry.update();
            sleep(1000);
            return WAREHOUSE;
        }
        //}
        telemetry.addLine("Press Not Recieved");
        telemetry.update();
        sleep(1000);
        return -1;
    }

    int buttonSample(){
        telemetry.addLine("Sample? (Wait 1 Second)");
        telemetry.addLine("A: Yes");
        telemetry.addLine("B: No");
        telemetry.update();
        sleep(500);
        while ((!gamepad1.a && !gamepad1.b) && !isStopRequested()){
            idle();
        }
        if (gamepad1.a){
            telemetry.addLine("Press Recieved");
            telemetry.update();
            sleep(1000);
            return YES;
        }else if (gamepad1.b){
            telemetry.addLine("Press Recieved");
            telemetry.update();
            sleep(1000);
            return NO;
        }

        telemetry.addLine("Press Not Recieved");
        telemetry.update();
        sleep(1000);
        return -1;
    }

    int startingPosition(){
        boolean pressed = false;
        telemetry.addLine("Duck or Warehouse Side? (Wait 1 Second)");
        telemetry.addLine("A: Duck");
        telemetry.addLine("B: Warehouse");
        telemetry.update();
        sleep(500);
        while ((!gamepad1.a && !gamepad1.b) && !isStopRequested()){
            while (!pressed) {
                if (gamepad1.a) {
                    telemetry.addLine("Press Recieved");
                    telemetry.update();
                    sleep(1000);
                    pressed=true;
                    return DUCK;
                } else if (gamepad1.b) {
                    telemetry.addLine("Press Recieved");
                    telemetry.update();
                    sleep(1000);
                    pressed=true;
                    return WAREHOUSE;
                }
            }
        }
        telemetry.addLine("Press Not Recieved");
        telemetry.update();
        sleep(1000);
        return -1;

    }

    double buttonDelay(){  //returns delay in miliseconds
        boolean done = false;
        double delay = 0.0;
        boolean aPressed = false; //is a pressed?
        boolean bPressed = false;
        boolean xPressed = false;
        boolean yPressed = false;

        while(!done && !isStopRequested()){
            telemetry.addLine("Press A to add 5 seconds");  //Telemetry for drivers
            telemetry.addLine("Press B to add 1 second");
            telemetry.addLine("Press X to reset timer");
            telemetry.addLine("Press Y to continue");
            telemetry.addData("Current Delay: ", delay);
            telemetry.addLine(" milliseconds");
            telemetry.update();

            if(gamepad1.a && aPressed ==false){ //Checks to see if button wasn't previously pressed
                delay += 5000;
                aPressed=true;
            }
            if (gamepad1.b && bPressed ==false){
                delay += 1000;
                bPressed=true;
            }
            if (gamepad1.x && xPressed ==false){
                delay = 0;
                xPressed=true;
            }
            if (gamepad1.y && yPressed ==false){
                done=true;
                yPressed=true;
            }

            if(!gamepad1.a){ //Resets button booleans once button is released
                aPressed=false;
            }
            if(!gamepad1.b){
                bPressed=false;
            }
            if(!gamepad1.x){
                xPressed=false;
            }
            if(!gamepad1.y){
                yPressed=false;
            }

        }
        telemetry.addLine("Press Received");
        telemetry.update();
        return delay;
    }
    

    public void generalDefine(){
        //Motor Define
        boolean bLedOn = true;
        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lb = hardwareMap.dcMotor.get("lb");
        rb = hardwareMap.dcMotor.get("rb");
        ls = hardwareMap.dcMotor.get("ls");
        //Motor Define In Phone
        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lb = hardwareMap.dcMotor.get("lb");
        rb = hardwareMap.dcMotor.get("rb");
        ls = hardwareMap.dcMotor.get("ls");

        duckMotor = hardwareMap.dcMotor.get("duck");
        harvester = hardwareMap.dcMotor.get("Harvester");



        //------------------------------------------------------------------------------------------------------//
        //Gyro Stuff
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


        //--------------------------------------------------------------------------------------------------------//
        //color_sensor = hardwareMap.colorSensor.get("color");

        lf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.REVERSE);


        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ls.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Encoder Stuff
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

        duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //----------------------------------------------------------------------------------------------------------------//
        //Foundation Only
        //Move 120 Inches
    }

    public void slideTime(double power, int time, int direction){
        if(direction == UP){
            ls.setPower(power);
        }else{
            ls.setPower(-power);
        }
        sleep(time);
        ls.setPower(0);
    }
    static int countMotorsBusy(DcMotor... motors) {
        int count = 0;
        for(final DcMotor motor : motors) {
            if (motor.isBusy()) {
                ++count;
            }
        }
        return count;
    }

    public void resetEncoders(DcMotor[] temp){
        for (int i = 0; i < temp.length; i++){
            temp[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            temp[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void setMotorPower(DcMotor[] temp, double power){
        for (int i = 0; i < temp.length; i++){
            temp[i].setPower(power);
            temp[i].setPower(power);
        }
    }

    public void setZeroPowerBehavior(DcMotor[] temp, boolean glide){
        if (glide){
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }else{
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


    }

    public void setRunMode(DcMotor.RunMode runMode, DcMotor... temp){
        for (int i = 0; i < temp.length; i++){
            temp[i].setMode(runMode);
        }
    }

    public void setRunMode(DcMotor[] temp, DcMotor.RunMode runMode){
        for (int i = 0; i < temp.length; i++){
            temp[i].setMode(runMode);
        }
    }

    public void drive(double power, double distance, int direction, boolean glide, boolean part) { //parameters: When you use the function, the code will ask for these two variables

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (glide){
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }else{
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        double sPower = 0.4;
        double lfPower = 0;
        double rfPower = 0;
        double lbPower = 0;
        double rbPower = 0;

        double slowDist = 4 * COUNTS_PER_INCH;

        if (!part){
            distance += (slowDist/COUNTS_PER_INCH);
        }

        switch(direction){
            case FORWARD:
                lf.setTargetPosition((int) ((COUNTS_PER_INCH * -distance) + slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist)); //For some reason, going forward gives negative encoder values
                lb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));

                lfPower = power;
                rfPower = power;
                lbPower = power;
                rbPower = power;
                break;

            case BACKWARDS:
                lf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lfPower = -power;
                rfPower = -power;
                lbPower = -power;
                rbPower = -power;
                break;
            case STRAFE_RIGHT: // What is case?
                lf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));

                lfPower = power;
                rfPower = -power;
                lbPower = -power;
                rbPower = power;



                break;
            case STRAFE_LEFT:
                lf.setTargetPosition((int) (COUNTS_PER_INCH * distance - slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * -distance +slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));

                lfPower = -power;
                rfPower = power;
                lbPower = power;
                rbPower = -power;
                break;
        }

        //Sign of setPower does not matter for RUN_TO_POSITION

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("****begining loop****1");
        telemetry.update();
        //sleep(1000);

        while (opModeIsActive()&& (countMotorsBusy(lf,lb,rf,rb) > 3)) {
            idle();
            telemetry.addData("rb",rb.getCurrentPosition());
            telemetry.addData("rf",rf.getCurrentPosition());
            telemetry.addData("lf",lf.getCurrentPosition());
            telemetry.addData("lb",lb.getCurrentPosition());
            telemetry.update();
        }

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

        telemetry.addLine("****ended loop****1");
        telemetry.update();
        //sleep(1000);
        //Phase 2 ---------------------------------------------
        if (part) {

            power /= 2;

            switch (direction) {
                case FORWARD:
                    lf.setTargetPosition((int) (-slowDist)); //distance needs to be in inches
                    rf.setTargetPosition((int) (-slowDist)); //For some reason, going forward gives negative encoder values
                    lb.setTargetPosition((int) (-slowDist));
                    rb.setTargetPosition((int) (-slowDist));


                    lfPower = power;
                    rfPower = power;
                    lbPower = power;
                    rbPower = power;
                    break;

                case BACKWARDS:
                    lf.setTargetPosition((int) (slowDist)); //distance needs to be in inches
                    rf.setTargetPosition((int) (slowDist));
                    lb.setTargetPosition((int) (slowDist));
                    rb.setTargetPosition((int) (slowDist));
                    lfPower = -power;
                    rfPower = -power;
                    lbPower = -power;
                    rbPower = -power;
                    break;
                case STRAFE_RIGHT: // What is case?
                    lf.setTargetPosition((int) (-slowDist)); //distance needs to be in inches
                    rf.setTargetPosition((int) (slowDist));
                    lb.setTargetPosition((int) (slowDist));
                    rb.setTargetPosition((int) (-slowDist));

                    lfPower = power;
                    rfPower = -power;
                    lbPower = -power;
                    rbPower = power;


                    break;
                case STRAFE_LEFT:
                    lf.setTargetPosition((int) (slowDist)); //distance needs to be in inches
                    rf.setTargetPosition((int) (-slowDist));
                    lb.setTargetPosition((int) (-slowDist));
                    rb.setTargetPosition((int) (slowDist));

                    lfPower = -power;
                    rfPower = power;
                    lbPower = power;
                    rbPower = -power;
                    break;
            }


            lf.setPower(lfPower);
            rf.setPower(rfPower);
            lb.setPower(lbPower);
            rb.setPower(rbPower);

            lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addLine("****begining loop****2");
            telemetry.update();
            //sleep(1000);

            while (opModeIsActive() && (countMotorsBusy(lf,lb,rf,rb) > 3)) {
                idle();
                telemetry.addData("rb", rb.getCurrentPosition());
                telemetry.addData("rf", rf.getCurrentPosition());
                telemetry.addData("lf", lf.getCurrentPosition());
                telemetry.addData("lb", lb.getCurrentPosition());
                telemetry.addData("rb Tar", rb.getTargetPosition());
                telemetry.addData("rf Tar", rf.getTargetPosition());
                telemetry.addData("lf Tar", lf.getTargetPosition());
                telemetry.addData("lb Tar", lb.getTargetPosition());
                telemetry.addLine("Part 2 Running");
                telemetry.update();
            }
            telemetry.addLine("****Ending loop****2");
            telemetry.update();
            //sleep(1000);
        }


        //End Phase 2 -----------------------------------------
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(double power, double distance, int direction, boolean glide, boolean part, double time){

        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.reset();

        resetEncoders(driveMotors);
        setMotorPower(driveMotors, 0);
        setZeroPowerBehavior(driveMotors, glide);

        double lfPower = 0;
        double rfPower = 0;
        double lbPower = 0;
        double rbPower = 0;

        double slowDist = 4 * COUNTS_PER_INCH;

        if (!part){
            distance += (slowDist/COUNTS_PER_INCH);
        }

        switch(direction){
            case FORWARD:
                lf.setTargetPosition((int) ((COUNTS_PER_INCH * -distance) + slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist)); //For some reason, going forward gives negative encoder values
                lb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));

                lfPower = power;
                rfPower = power;
                lbPower = power;
                rbPower = power;
                break;

            case BACKWARDS:
                lf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lfPower = -power;
                rfPower = -power;
                lbPower = -power;
                rbPower = -power;
                break;
            case STRAFE_RIGHT: // What is case?
                lf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));

                lfPower = power;
                rfPower = -power;
                lbPower = -power;
                rbPower = power;



                break;
            case STRAFE_LEFT:
                lf.setTargetPosition((int) (COUNTS_PER_INCH * distance - slowDist)); //distance needs to be in inches
                rf.setTargetPosition((int) (COUNTS_PER_INCH * -distance + slowDist));
                lb.setTargetPosition((int) (COUNTS_PER_INCH * -distance +slowDist));
                rb.setTargetPosition((int) (COUNTS_PER_INCH * distance- slowDist));

                lfPower = -power;
                rfPower = power;
                lbPower = power;
                rbPower = -power;
                break;
        }

        //Sign of setPower does not matter for RUN_TO_POSITION

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

        setRunMode(driveMotors, DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("****begining loop****1");
        telemetry.update();
        //sleep(1000);

        elapsedTime.reset();
        while (opModeIsActive()&& (countMotorsBusy(lf,lb,rf,rb) > 3) && elapsedTime.seconds() < time) {
            idle();

            lf.setPower(lfPower * (elapsedTime.seconds()/ time) );
            rf.setPower(rfPower * (elapsedTime.seconds()/ time) );
            lb.setPower(lbPower * (elapsedTime.seconds()/ time));
            rb.setPower(rbPower * (elapsedTime.seconds()/ time));




            telemetry.addData("rb",rb.getCurrentPosition());
            telemetry.addData("rf",rf.getCurrentPosition());
            telemetry.addData("lf",lf.getCurrentPosition());
            telemetry.addData("lb",lb.getCurrentPosition());
            telemetry.update();
        }

        while (opModeIsActive()&& (countMotorsBusy(lf,lb,rf,rb) > 3)) {
            idle();

            lf.setPower(lfPower);
            rf.setPower(rfPower);
            lb.setPower(lbPower);
            rb.setPower(rbPower);




            telemetry.addData("rb",rb.getCurrentPosition());
            telemetry.addData("rf",rf.getCurrentPosition());
            telemetry.addData("lf",lf.getCurrentPosition());
            telemetry.addData("lb",lb.getCurrentPosition());
            telemetry.update();
        }

        resetEncoders(driveMotors);

        setMotorPower(driveMotors, 0);

        telemetry.addLine("****ended loop****1");
        telemetry.update();
    }

    public void encoderTurn180(double power){
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        double sPower = 0.4;
        double lfPower = 0;
        double rfPower = 0;
        double lbPower = 0;
        double rbPower = 0;

        lf.setTargetPosition(-1725); //distance needs to be in inches
        rf.setTargetPosition(1612);  // What is setTargetPosition?
        lb.setTargetPosition(-1723); // It tells the wheel how far to go.
        rb.setTargetPosition(1613);
        lfPower = power;
        rfPower = -power;
        lbPower = power;
        rbPower = -power;

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (opModeIsActive()&& countMotorsBusy(lf, lb, rf, rb) > 1) {
            idle();
            telemetry.addData("rb",rb.getCurrentPosition());
            telemetry.addData("rf",rf.getCurrentPosition());
            telemetry.addData("lf",lf.getCurrentPosition());
            telemetry.addData("lb",lb.getCurrentPosition());
            telemetry.update();
        }
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    public void turnDegGyro(boolean dir, int deg, double power){
        telemetry.addData("Doing", "turnDeg");
        telemetry.update();
        resetAngle();

        //left is true
        double lpwr = dir ? -power : power; // if true: if false
        //ternary operators
        double rpwr = -lpwr;
        lf.setPower(lpwr);
        rf.setPower(rpwr);
        lb.setPower(lpwr);
        rb.setPower(rpwr);//just setPower

        while((Math.abs(getHeadingDeg()) < Math.abs(deg) - 20) && opModeIsActive()){
            telemetry.addData("Angle:", getHeadingDeg());
            telemetry.update();
            idle();
        }
        lpwr /= 4;
        rpwr /= 4;
        lf.setPower(lpwr);
        rf.setPower(rpwr);
        lb.setPower(lpwr);
        rb.setPower(rpwr);
        while(Math.abs(getHeadingDeg()) < 20 && opModeIsActive()){
            telemetry.addData("Angle 2:", getHeadingDeg());
            telemetry.update();
            idle();
        }

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);


    }








    //----------Other--------------------Other---------------Other







    private double getHeading()
    {
        //get's current angle/way you are facing
        final Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return angles.firstAngle;

    }

    public void freeform(int distance, int angle){ //angle in radians
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setTargetPosition((int) (COUNTS_PER_INCH * distance)); //distance needs to be in inches
        rf.setTargetPosition((int) (COUNTS_PER_INCH * distance));
        lb.setTargetPosition((int) (COUNTS_PER_INCH * distance));
        rb.setTargetPosition((int) (COUNTS_PER_INCH * distance));


        double startOrient = 0;
        double lfPower = 0;
        double rfPower = 0;
        double lbPower = 0;
        double rbPower = 0;



        final double currentHeading = getHeading();


        double angleDiff = startOrient - currentHeading;


        /*
        Add Holonomic Drive Components
        Need separate mechanism from encoders (wheels move different amounts when move other than

         */



        //Trig
        //double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        //returns hypotonuse (C value in triangle)

        double robotAngle = angle - Math.PI / 4;
        //return angle x (next to center of circle)

        //double rightX = -gamepad1.right_stick_x;
        //rotiation

        final double lfPow =  Math.sin(angle);
        final double rfPow =  Math.cos(angle);
        final double lbPow =  Math.cos(angle);
        final double rbPow =  Math.sin(angle);
        //determines wheel power

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }





}

