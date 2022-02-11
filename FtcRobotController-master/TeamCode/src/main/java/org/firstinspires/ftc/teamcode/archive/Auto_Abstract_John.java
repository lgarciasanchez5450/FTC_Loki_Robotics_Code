package org.firstinspires.ftc.teamcode.archive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Auto_Abstract_John extends LinearOpMode {


    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;

    static final double pi =Math.PI;
    static final double countsPerRev = 28;
    static final double driverGearReduction = 20;
    static final double wheelDiameter = 3.97638;
}
