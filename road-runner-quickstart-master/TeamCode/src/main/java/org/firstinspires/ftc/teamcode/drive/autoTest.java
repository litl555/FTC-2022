package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutoTest")
public class autoTest extends LinearOpMode {
    SampleMecanumDrive drive=new SampleMecanumDrive(hardwareMap);
    DcMotor spinner;

    @Override
    public void runOpMode() throws InterruptedException {
        spinner=hardwareMap.dcMotor.get("leftEncoder");
        spinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while(!isStopRequested()){
            telemetry.addData("yes: " ,spinner.getCurrentPosition());
            telemetry.update();
            //drive.setWeightedDrivePower(new Pose2d(1,0,0));
            //sleep(500);
            //drive.setWeightedDrivePower(new Pose2d(0,0,0));
            //drive.update();

        }
    }

}
