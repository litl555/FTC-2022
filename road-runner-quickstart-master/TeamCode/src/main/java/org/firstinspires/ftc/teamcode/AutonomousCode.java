package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.Claw;
import org.firstinspires.ftc.teamcode.drive.MecanumCode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name="Autonomous")
public class AutonomousCode extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    DcMotor lift;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 1.42380404e+03;
    double fy = 1.42076388e+03;
    double cx = 6.58410203e+02;
    double cy = 3.83122919e+02;
    long time = 0;
    long time2=0;
    boolean go = false;
    // UNITS ARE METERS
    double tagsize = 0.166;
    boolean clawOpen=true;

    // tags
    int tag1 = 2, tag2 = 7, tag3 = 12;

    AprilTagDetection tagOfInterest = null;
    MecanumCode mecanumCode=new MecanumCode();

    @Override
    public void runOpMode() {
        CRServo servoLeft=hardwareMap.crservo.get("s0");
        CRServo servoRight=hardwareMap.crservo.get("s1");
        CRServo clawRight=hardwareMap.crservo.get("clawRight");
        CRServo clawLeft=hardwareMap.crservo.get("clawLeft");
        CRServo arm1=hardwareMap.crservo.get("s3");
        CRServo arm2=hardwareMap.crservo.get("s4");
        lift=hardwareMap.dcMotor.get("lift");
        DcMotor spinner=hardwareMap.dcMotor.get("leftEncoder");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift = hardwareMap.dcMotor.get("lift");
        Claw armClaw=new Claw(arm1,arm2);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"),
                cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == tag1 || tag.id == tag2 || tag.id == tag3) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);

                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if (tagOfInterest != null) {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }
        //TODO UNCOMMENT THIS SHIT



        /*
         * You wouldn't have this in your autonomous, this is just to prevent the sample
         * from ending
         */
        while (opModeIsActive()) {



            /*


             */
            clawLeft.setPower(-1);
            clawRight.setPower(1);
            drive.setWeightedDrivePower(new Pose2d(.5,0,0));
            sleep(1060);//1220
            drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
            sleep(500);
            drive.setWeightedDrivePower(new Pose2d(-.5, 0, 0));
            sleep(250);
            drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
            sleep(500);
            if (tagOfInterest.id == tag1) {
                drive.setWeightedDrivePower(new Pose2d(0, .5, -20/180*Math.PI));//LEFT
                sleep(1310);//1640
                drive.setWeightedDrivePower(new Pose2d(0, 0, 0));


            }
            if (tagOfInterest.id == tag3) {
                drive.setWeightedDrivePower(new Pose2d(0, -.5, 20/180*Math.PI));
                sleep(1310);//1640
                drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
            }

//1680 1170


            telemetry.addData("time2", time2);
            telemetry.addData("time", time);
            telemetry.update();
            break;
        }
    }

    void tagToTelemetry(AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
    public void idleClaw(Claw claw){claw.setPower(0);}
    public void lowerLift(){lift.setPower(-.2);}
    public void idleLift(){lift.setPower(.1);}
    public void raiseLift(){lift.setPower(1);}
    public void openClaw(Claw claw){
        if(!clawOpen) {
            claw.setPower(-1);
            sleep(250);
            claw.setPower(0);
        }
        clawOpen=true;
    }public void closeClaw(Claw claw){
        claw.setPower(1);
        clawOpen=false;
    }
    //public void idleDrive(){drive.setWeightedDrivePower(new Pose2d(0.0,0.0,0.0));}
}
