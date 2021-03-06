package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.Rumble.ControllerSide;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.CargoIntake.MotorState;
import frc.robot.subsystems.CargoIntake.PositionState;
import frc.robot.subsystems.Grabber.GrabberPosition;

public class ChangeCargoIntakeState extends Command {
    MotorState desiredMotorStateRaw;
    PositionState desiredPositionStateRaw;

    MotorState desiredMotorState;
    PositionState desiredPositionState;

    boolean forceFinish = false;

    public ChangeCargoIntakeState(PositionState desiredPositionState, MotorState desiredMotorState) {
        requires(Robot.cargoIntake);
        this.desiredPositionStateRaw = desiredPositionState;
        this.desiredMotorStateRaw = desiredMotorState;
    }
    
    @Override
    public void initialize() {
        this.setTimeout(0);
        this.desiredMotorState = this.desiredMotorStateRaw;
        this.desiredPositionState = this.desiredPositionStateRaw;
        
        if (Robot.grabber.hasCargo() && desiredMotorState == MotorState.ON) {
            desiredMotorState = MotorState.OFF;
            Robot.cargoIntake.setMotorState(MotorState.OFF);
        } else {
            Robot.cargoIntake.setMotorState(desiredMotorState);
        }
        
        if (Robot.grabber.getState() != GrabberPosition.RETRACTED) {
            if (this.desiredPositionState != Robot.cargoIntake.getPosition()) {
                this.setTimeout(0.5);
            }
            Robot.cargoIntake.setState(desiredPositionState);
        } else {
            new Rumble(0.25, ControllerSide.BOTH).start();
        }
    }

    @Override
    public void execute() {
    }
    @Override
    public void end() {
        System.out.println("ended changing cargo intake");
    }

    protected boolean isFinished() {
        return this.isTimedOut() && Robot.cargoIntake.getMoterState() == this.desiredMotorState && Robot.cargoIntake.getPosition() == this.desiredPositionState;
    }
}