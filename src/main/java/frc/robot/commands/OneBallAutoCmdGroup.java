// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.HangSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OneBallAutoCmdGroup extends SequentialCommandGroup {
  /** Creates a new AutonomousCmdGroup. */

  public OneBallAutoCmdGroup(DrivetrainSubsystem drivetrainSubsystem, StorageSubsystem storageSubsystem, 
  HangSubsystem hangSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, TurretSubsystem turretSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //addRequirements(drivetrainSubsystem);
    addCommands(
      new PrintCommand("AutonomousCmdGroup started!"),
      
      /**
       * Fix "DriveForwardCmd":
       * 
       * You cannot go backwards
       * 
       */
      new DriveForwardCmd(drivetrainSubsystem, storageSubsystem, hangSubsystem, 4),
      
      /**
       * Figure out how to do a ParallelDeadlineCommandGroup
       * 
       * So I can get out of shooting a ball once
       */
      new ParallelCommandGroup(
        new RunCommand(turretSubsystem::turretWithLimelight, turretSubsystem),
        new EnableShooterCmd(shooterSubsystem), 
        .until(condition)
        .andTeh
        new EnableFeederCmd(storageSubsystem)
        ).withInterrupt(condition),
        
      new ParallelCommandGroup(
        new RunCommand(shooterSubsystem::disable, shooterSubsystem),
        new RunCommand(storageSubsystem::stopMotors , storageSubsystem)
      ),
      new RunCommand(shooterSubsystem::stopMotors),
      
      new PrintCommand("AutonomousCmdGroup ended!")
    );
  }
}