package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
@TeleOp(name="cameraTest")
public class camerTest extends LinearOpMode {
    WebcamName camera;

    @Override
    public void runOpMode() throws InterruptedException {
        camera=hardwareMap.get(WebcamName.class,"Webcam 1");
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("camera",camera);
            telemetry.update();
        }
    }
}
