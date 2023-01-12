package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="test2")
public class test2 extends LinearOpMode {
    CRServo servo1,servo2;

    @Override
    public void runOpMode() throws InterruptedException {
        servo1=hardwareMap.crservo.get("clawLeft");
        servo2=hardwareMap.crservo.get("clawRight");
        waitForStart();
        while(opModeIsActive()){

            if(gamepad1.a){
                servo2.setPower((double)1.0);
                servo1.setPower((double)-1.0);
            }
            else if(gamepad1.b){
                servo2.setPower((double)-1.0);
                servo1.setPower((double)1.0);
            }
            else{
                servo2.setPower((double)0.0);
                servo1.setPower((double)0.0);
            }
            idle();
        }
    }
}
