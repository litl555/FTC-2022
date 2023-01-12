package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class bruteDrive extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor m0 = hardwareMap.dcMotor.get("leftFront");
        DcMotor m1=hardwareMap.dcMotor.get("leftBack");
        DcMotor m2=hardwareMap.dcMotor.get("rightFront");
        DcMotor m3=hardwareMap.dcMotor.get("rightBack");
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.a) {
                m0.setPower(-1);
                m1.setPower(1);
                m2.setPower(1);
                m3.setPower(-1);
            }
            else{
                m0.setPower(0);
                m1.setPower(0);
                m2.setPower(0);
                m3.setPower(0);
            }
        }
    }
}
