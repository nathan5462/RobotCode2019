package frc.robot.commands.grabber;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber.GrabberPosition;
import frc.robot.subsystems.Grabber.MotorState;

public class EjectBall extends Command{

    public EjectBall() {
        requires(Robot.grabber);
    }

    @Override
    protected void initialize() {
        if(!Robot.grabber.getBallVelPID().isEnabled()){
            Robot.grabber.getBallVelPID().enable();
        }
        if (Robot.grabber.getState() == GrabberPosition.EXTENDED) {
            Robot.grabber.setBallMotorState(MotorState.EJECT_BALL);
            this.setTimeout(Constants.kGrabberEjectionTime);
        } else {
            this.cancel();
        }
    }

    @Override
    protected void end() {
        Robot.grabber.getBallVelPID().disable();
        Robot.grabber.setBallMotorState(MotorState.OFF);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}