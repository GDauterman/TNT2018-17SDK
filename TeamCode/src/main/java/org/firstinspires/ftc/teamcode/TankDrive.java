/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Basic Teleop", group = "TeleOp")
public class TankDrive extends OpMode {

    DcMotor motorR = null;
    DcMotor motorL = null;
    Servo arm = null;
    double armPos = 0.0;



    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        telemetry.addData("hardwareMap", hardwareMap);

        motorR = hardwareMap.dcMotor.get("Right Motor");
        motorL = hardwareMap.dcMotor.get("Left Motor");
        motorL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorR.setDirection(DcMotorSimple.Direction.REVERSE);
        arm = hardwareMap.servo.get("Arm");
        armPos = 0;

    }


    @Override
    public void init_loop() {
    }

    @Override
    public void loop() {

        double right = gamepad1.right_stick_y;
        double left = gamepad1.left_stick_y;

        if(gamepad1.right_bumper){
            armPos+=0.01;
        }
        else if(gamepad1.left_bumper){
            armPos-=0.01;
        }

        armPos = Range.clip(armPos, 0, 1);

        telemetry.addData("Right", right);
        telemetry.addData("Left", left);

        telemetry.addData("joyX", gamepad1.right_stick_x);
        telemetry.addData("joyY", gamepad1.right_stick_y);

        motorR.setPower(right);
        motorL.setPower(left);
        arm.setPosition(armPos);

    }

    @Override
    public void stop() {

        motorR.setPower(0);
        motorL.setPower(0);

    }

}

/*
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
@Disabled

public class TeleopLinear4NickV2 extends LinearOpMode {

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

            double TR = Range.clip(scaleInput(joyY) + scaleInput(joyX) + scaleInput(gamepad1.left_stick_x), -1, 1);//power for Top Right Motor
            double TL = Range.clip(-scaleInput(joyY) + scaleInput(joyX) + scaleInput(gamepad1.left_stick_x), -1, 1);//power for Top Left Motor
            double BR = Range.clip(scaleInput(joyY) - scaleInput(joyX) + scaleInput(gamepad1.left_stick_x), -1, 1);//power for Bottom Right Motor
            double BL = Range.clip(-scaleInput(joyY) - scaleInput(joyX) + scaleInput(gamepad1.left_stick_x), -1, 1);//power for Bottom Left Motor

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

 */