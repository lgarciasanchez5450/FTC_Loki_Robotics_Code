package org.firstinspires.ftc.teamcode.archive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
@TeleOp(name = "SimpleMotorLED")
public class SimpleMotorLED extends OpMode
{
    public DcMotorSimple ledLeft;
    public DcMotorSimple ledRight;



    public void assignLedStatus(double ledStatusPosition) {
        ledLeft.setPower(ledStatusPosition);
        //ledRight.setPower(ledStatusPosition);
    }

    public void ledStatus(String color) {
        if (color == "Green") {
            assignLedStatus(0.39);
        } else if (color == "Blue") {
            assignLedStatus(0.44);
        } else if (color == "Red") {
            assignLedStatus(0.31);
        } else if (color == "White") {
            assignLedStatus(0.47);
        } else if (color == "Off") {
            assignLedStatus(0.5);
        } else if (color == "Orange") {
            assignLedStatus(-0.01);
        } else if (color == "Pink") {
            assignLedStatus(-0.48);
        } else if (color == "Yellow") {
            assignLedStatus(-0.49);
        } else if (color == "Glowing Blue") {
            assignLedStatus(-0.07);
        } else if (color == "Glowing Red") {
            assignLedStatus(-0.08);
        }
    }

    @Override
    public void init() {
        ledLeft  = hardwareMap.get(DcMotorSimple.class, "lights");
        //ledRight  = hardwareMap.get(DcMotorSimple.class, "ledRight");
    }

    @Override
    public void loop() {
        ledStatus("Green");
    }
}
