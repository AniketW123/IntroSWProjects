package frc.robot.subsystems;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Flywheel extends Subsystem{
    private static Flywheel mInstance;

    public static Flywheel getInstance() {
        if (mInstance == null) {
            mInstance = new Flywheel();
        }

        return mInstance;
    }

    public class PeriodicIO {
        double velocity_ticks = 0;
        double velocity_demand = 0;
    }

    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private TalonFX mFlywheelMaster, mFlywheelFollower;
    private TalonSRX mFlywheelEncoder = new TalonSRX(3);

    public Flywheel(){
        mFlywheelMaster = new TalonFX(Constants.kFlywheelMasterId);
        mFlywheelFollower = new TalonFX(Constants.kFlywheelFollowerId);
        mFlywheelEncoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
        mFlywheelFollower.set(ControlMode.Follower, 1);
        mFlywheelMaster.config_kP(Constants.kSlotIdx, Constants.kFlywheelKp, Constants.kFlywheelTimeoutMs);
        mFlywheelMaster.config_kI(Constants.kSlotIdx, Constants.kFlywheelKi, Constants.kFlywheelTimeoutMs);
        mFlywheelMaster.config_kD(Constants.kSlotIdx, Constants.kFlywheelKd, Constants.kFlywheelTimeoutMs);
        mFlywheelMaster.config_kF(Constants.kSlotIdx, Constants.kFlywheelKf, Constants.kFlywheelTimeoutMs);
    }   

    public void readPeriodicInput(){
        mPeriodicIO.velocity_ticks = mFlywheelEncoder.getSelectedSensorPosition(0);
    }

    public void writePeriodicOutput(){
        mFlywheelMaster.set(ControlMode.Velocity, mPeriodicIO.velocity_demand);
    }

    public double getRPM() {
        return mPeriodicIO.velocity_ticks * 600;
    }

    public void setRPM(double rpm){
        mPeriodicIO.velocity_demand = rpm / 600;
    }
}
