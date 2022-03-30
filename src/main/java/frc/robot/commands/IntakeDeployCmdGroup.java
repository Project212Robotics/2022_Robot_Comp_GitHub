// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeDeployCmdGroup extends SequentialCommandGroup {
  /** Creates a new IntakeDeploy. */
  public IntakeDeployCmdGroup(IntakeSubsystem intakeSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new FunctionalCommand(onInit, onExecute, onEnd, isFinished, requirements),
      new PrintCommand("IntakeDeployCmdGroup started!"),

      new ParallelCommandGroup(
        new SequentialCommandGroup( 
          new InstantCommand(() -> intakeSubsystem.setPiston(Value.kForward)),
          new WaitCommand(0.3),
          new InstantCommand(() -> intakeSubsystem.setPiston(Value.kOff))
        ),
        new SequentialCommandGroup(
          new WaitCommand(IntakeConstants.INTAKE_SPEED),
          new RunCommand(() -> intakeSubsystem.intakeWithTriggers(RobotContainer.gunnerJoystick, IntakeConstants.INTAKE_SPEED), intakeSubsystem)
        )
      ),
      new PrintCommand("IntakeDeployCmdGroup ended!")
    );
  }
}