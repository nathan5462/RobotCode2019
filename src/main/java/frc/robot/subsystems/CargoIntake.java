package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.lib.MathHelper;
import frc.robot.Constants;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
public class CargoIntake extends Subsystem {
    // Moter
    public WPI_TalonSRX intakeMoter = new WPI_TalonSRX(RobotMap.intakeMoter);

    // DoubleSolenoid
    public DoubleSolenoid posSol;

    // States
    public enum MotorState {
        ON, OFF
    }

    public enum PositionState {
        UP, DOWN
    }

    @Override
    public void initDefaultCommand() {

    }

    public CargoIntake() {
        posSol = new DoubleSolenoid(RobotMap.PCM1, RobotMap.cargoIntake1, RobotMap.cargoIntake2);

        // Method 1
        intakeMoter.configPeakCurrentLimit(2, 10);
        intakeMoter.configPeakCurrentDuration(200, 10);
        intakeMoter.configContinuousCurrentLimit(1, 10);
        intakeMoter.enableCurrentLimit(true);
    }

    public void currentLimit() {
        // Method 2
        // if (intakeMoter.getOutputCurrent() > 1.0) { // If amps is creater than 10
        //     intakeMoter.set(0); // Stop the moter
        //     mstate = moterState.OFF; // Set the state to off
        // }
    }


    /**
     * Gets whether the intake motor is activated
     */
    public MotorState getMoterState() {
        if (MathHelper.areApproxEqual(0, this.intakeMoter.get())) {
            return MotorState.OFF;
        } else {
            return MotorState.ON;
        }
        
    }

    /**
     * Sets the position of the state to the desired state.
     * 
     * @param state the desired state of the cargo intake's arm
     */
    public void setState(PositionState state) {
        if (state == PositionState.UP) {
            posSol.set(Value.kForward);
        } else {
            posSol.set(Value.kReverse);
        }
    }

    public void setMotorState(MotorState state) {
        if (state == MotorState.OFF) {
            intakeMoter.set(0);
        } else {
            intakeMoter.set(Constants.kCargoIntakeSpeed);
        }
    }
    
    /**
     * Gets the position of the intake arm (whether it is up or down)
     */
    public PositionState getPosition() {
        if (posSol.get() == Value.kForward) {
            return PositionState.UP;
        } else {
            return PositionState.DOWN;
        }
    }
}