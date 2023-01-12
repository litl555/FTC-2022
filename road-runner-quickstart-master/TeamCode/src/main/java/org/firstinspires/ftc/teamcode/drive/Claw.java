package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.CRServo;

public class Claw{
    CRServo servoLeft,servoRight;

    public Claw(CRServo servoLeft,CRServo servoRight){
        this.servoRight=servoRight;
        this.servoLeft=servoLeft;
    }
    public void setPower(double power){
        servoLeft.setPower(-power);
        servoRight.setPower(power);
    }

}
