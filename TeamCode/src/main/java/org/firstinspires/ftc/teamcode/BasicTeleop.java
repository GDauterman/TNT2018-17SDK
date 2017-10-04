package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "Base Bot", group = "TeleOp")

public class BasicTeleop extends LinearOpMode {

    DcMotor motorBR;
    DcMotor motorBL;
    DcMotor motorMR;
    DcMotor motorML;

    public void runOpMode() throws InterruptedException {

        motorMR = hardwareMap.dcMotor.get("motorMR");
        motorML = hardwareMap.dcMotor.get("motorML");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorBL = hardwareMap.dcMotor.get("motorBL");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double speedMR = 0;
            double speedML = 0;
            double speedBR = 0;
            double speedBL = 0;

            if (gamepad1.dpad_up) {
                speedMR = 1;
                speedML = -1;
                speedBR = 1;
                speedBL = -1;
            } else if (gamepad1.dpad_right) {
                speedMR = -1;
                speedML = -1;
                speedBR = 0;
                speedBL = 0;
            } else if (gamepad1.dpad_left) {
                speedMR = 1;
                speedML = 1;
                speedBR = 0;
                speedBL = 0;
            } else if (gamepad1.dpad_down) {
                speedMR = -1;
                speedML = 1;
                speedBR = -1;
                speedBL = 1;
            } else{
                speedMR = gamepad1.right_stick_y;
                speedML = -gamepad1.left_stick_y;
                speedBR = gamepad1.right_stick_y;
                speedBL = -gamepad1.left_stick_y;
            }

            motorMR.setPower(Range.clip(speedMR, -1, 1));
            motorML.setPower(Range.clip(speedML, -1, 1));
            motorBR.setPower(Range.clip(speedBR, -1, 1));
            motorBL.setPower(Range.clip(speedBL, -1, 1));

        }
        stopMotors();
    }

    void stopMotors() {

        motorMR.setPower(0);
        motorML.setPower(0);
        motorBR.setPower(0);
        motorBL.setPower(0);
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