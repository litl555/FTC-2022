package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class encdoderTest extends LinearOpMode {
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor=hardwareMap.dcMotor.get("leftFront");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("pos",motor.getCurrentPosition()/8192.0);
            telemetry.update();
        }
    }
}
