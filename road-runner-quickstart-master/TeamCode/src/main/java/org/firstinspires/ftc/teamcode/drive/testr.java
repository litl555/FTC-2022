package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="stuff")
public class testr extends LinearOpMode {

    DcMotor leftFront,leftRear,rightFront,rightRear;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while(!isStopRequested()) {
            leftFront = hardwareMap.dcMotor.get("leftFront");
            rightFront = hardwareMap.dcMotor.get("rightFront");
            leftRear = hardwareMap.dcMotor.get("rightRear");
            rightRear = hardwareMap.dcMotor.get("leftRear");
            if (gamepad1.dpad_up) {
                leftFront.setPower(1);

            } else if (gamepad1.dpad_right) {
                leftRear.setPower(1);
            } else if (gamepad1.dpad_down) {
                rightFront.setPower(1);
            } else if (gamepad1.dpad_left) {
                rightRear.setPower(1);
            } else {
                rightRear.setPower(0);
                rightFront.setPower(0);
                leftFront.setPower(0);
                leftRear.setPower(0);
            }
        }
    }
}
