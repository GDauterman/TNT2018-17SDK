package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "Teleop For Nick (1 GP)", group = "TeleOp")

public class TeleopV1 extends LinearOpMode {

    DcMotor motorBR;
    DcMotor motorBL;
    DcMotor motorTR;
    DcMotor motorTL;
    DcMotor ballCollector;
    DcMotor flyWheel;
    DcMotor capBall;
    Servo ballLoader;
    CRServo buttonPusher;


    public void runOpMode() throws InterruptedException {

        motorTR = hardwareMap.dcMotor.get("motorTR");
        motorTL = hardwareMap.dcMotor.get("motorTL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        ballCollector = hardwareMap.dcMotor.get("ball collector");
        flyWheel = hardwareMap.dcMotor.get("fly wheel");
        capBall = hardwareMap.dcMotor.get("cap ball");
        ballLoader = hardwareMap.servo.get("ball loader");
        buttonPusher = hardwareMap.crservo.get("button pusher");
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        buttonPusher.setDirection(DcMotorSimple.Direction.REVERSE);
        capBall.setDirection(DcMotorSimple.Direction.REVERSE);

        buttonPusher.setPower(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double joyX = gamepad1.right_stick_x;
            double joyY = gamepad1.right_stick_y;


            if (gamepad1.dpad_up) {
                joyX = 0;
                joyY = -1;
            } else if (gamepad1.dpad_right) {
                joyX = 1;
                joyY = 0;
            } else if (gamepad1.dpad_left) {
                joyX = -1;
                joyY = 0;
            } else if (gamepad1.dpad_down) {
                joyX = 0;
                joyY = 1;
            }

            double TR = Range.clip(scaleInput(joyY) + scaleInput(joyX) - scaleInput(gamepad1.left_stick_x), -1, 1);//power for Top Right Motor
            double TL = Range.clip(-scaleInput(joyY) + scaleInput(joyX) - scaleInput(gamepad1.left_stick_x), -1, 1);//power for Top Left Motor
            double BR = Range.clip(scaleInput(joyY) - scaleInput(joyX) - scaleInput(gamepad1.left_stick_x), -1, 1);//power for Bottom Right Motor
            double BL = Range.clip(-scaleInput(joyY) - scaleInput(joyX) - scaleInput(gamepad1.left_stick_x), -1, 1);//power for Bottom Left Motor

            if(gamepad1.right_trigger!=0){
                TR*=0.23;
                TL*=0.23;
                BR*=0.23;
                BL*=0.23;
            }

            motorTR.setPower(Range.clip(TR, -1, 1));
            motorTL.setPower(Range.clip(TL, -1, 1));
            motorBR.setPower(Range.clip(BR, -1, 1));
            motorBL.setPower(Range.clip(BL, -1, 1));

            if (gamepad1.b)//If it is pressed
                ballLoader.setPosition(0);
            else//if it is not
                ballLoader.setPosition(1);
            if (gamepad1.right_bumper)
                flyWheel.setPower(1);
            else
                flyWheel.setPower(0);
            if (gamepad1.left_bumper)
                ballCollector.setPower(1);
            else if (gamepad1.left_trigger!=0)
                ballCollector.setPower(-1);
            else
                ballCollector.setPower(0);
            if(gamepad1.y)
                buttonPusher.setPower(1);
            else
                buttonPusher.setPower(0);
            if(gamepad2.left_stick_y<0)
                capBall.setPower(gamepad2.left_stick_y);
            else
                capBall.setPower(gamepad2.left_stick_y/2);
        }
        stopMotors();
    }

    void stopMotors() {
        flyWheel.setPower(0);
        ballCollector.setPower(0);
        capBall.setPower(0);
        motorTR.setPower(0);
        motorTL.setPower(0);
        motorBR.setPower(0);
        motorBL.setPower(0);
        buttonPusher.setPower(0);
    }

    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    public double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}