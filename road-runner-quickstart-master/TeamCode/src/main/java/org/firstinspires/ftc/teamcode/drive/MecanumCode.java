package org.firstinspires.ftc.teamcode.drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(group = "drive")
public class MecanumCode extends LinearOpMode {
    CRServo servoRight,servoLeft,clawRight,clawLeft,arm1,arm2;
    DcMotor lift,spinner;

    boolean clawOpen=true;
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() {
        servoLeft=hardwareMap.crservo.get("s0");
        servoRight=hardwareMap.crservo.get("s1");
        clawRight=hardwareMap.crservo.get("clawRight");
        clawLeft=hardwareMap.crservo.get("clawLeft");
        arm1=hardwareMap.crservo.get("s3");


        arm2=hardwareMap.crservo.get("s4");

        lift=hardwareMap.dcMotor.get("lift");
        spinner=hardwareMap.dcMotor.get("leftEncoder");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Claw armClaw=new Claw(clawLeft,clawRight);
        Claw driverClaw=new Claw(servoLeft,servoRight);
        //we treat the arm as a claw don't question it
        Claw arm=new Claw(arm1,arm2);
        waitForStart();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //spinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (!isStopRequested()) {
            if ((gamepad1.left_stick_x > .5f || gamepad1.left_stick_x < -.5f )|| (gamepad1.left_stick_y < -.5 || gamepad1.left_stick_y > .5f)||(gamepad1.right_stick_x > .5f || gamepad1.right_stick_x < -.5f )) {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                -gamepad1.left_stick_y*.75,
                                (-gamepad1.left_stick_x*.75),
                                (-gamepad1.right_stick_x*.75)
                        )
                );
            } else{
                drive.setWeightedDrivePower(new Pose2d(0,0,0));
            }
            //for sharing controls so that Hudson can retract lift
            if(gamepad1.y){
                arm.setPower(1);
                sleep(500);
                arm1.setPower(0);
                arm2.setPower(0);
            }
            if(gamepad1.x){
                drive.setWeightedDrivePower(new Pose2d(-.5,0,0));

            }
            if(gamepad2.a){openClaw(armClaw);}
            else if(gamepad2.b){closeClaw(armClaw);}
            else{idleClaw(armClaw);}
            if (gamepad1.a) driverClaw.setPower(1);
            if (gamepad1.b) driverClaw.setPower(-1);
            else{idleClaw(driverClaw);}
            if(gamepad2.left_trigger>.1)lowerLift();
            else if(gamepad2.right_trigger>0)lift.setPower(gamepad2.right_trigger);
            else idleLift();
            //Keybind for automatically dropping the cone

            if(gamepad2.y){
                lift.setPower(-.05);
                sleep(200);
                openClaw(armClaw);
                raiseLift();
                sleep(500);
                idleClaw(armClaw);
                idleLift();
            }
            spinner.setPower(exponize(gamepad2.left_stick_x));
            if(Math.abs(gamepad2.left_stick_y)>.02){
                arm.setPower(-gamepad2.left_stick_y*.75);
            }
            else{
                idleClaw(arm);
            }

            drive.update();
            idle();
        }
    }public void openClaw(Claw claw){
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
    public void idleClaw(Claw claw){claw.setPower(0);}
    public void lowerLift(){lift.setPower(-.1);}
    public void idleLift(){lift.setPower(.1);}
    public void raiseLift(){lift.setPower(1);}
    public void idleDrive(){drive.setWeightedDrivePower(new Pose2d(0.0,0.0,0.0));}
    public double exponize(double input){return(Math.signum(input))*Math.pow(input,2);}
}