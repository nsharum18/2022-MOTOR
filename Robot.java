// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //sets up spark max motors
   CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
   CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
   CANSparkMax right1 = new CANSparkMax(3, MotorType.kBrushless);
   CANSparkMax right2 = new CANSparkMax(4, MotorType.kBrushless);
   CANSparkMax climb = new CANSparkMax(5, MotorType.kBrushless);





  //sets up drivetrain
  private final DifferentialDrive m_drive = new DifferentialDrive(left1,right1);

  //sets up encoders
  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;
  private RelativeEncoder intakeEncoder;
  private RelativeEncoder climbEncoder;


  //xbox controller
  private Joystick xBox;

  //enumerations
  public enum Enumerations{
    
    kZero,
		kShoot,
		kBackup,
		kDone;
	}
Enumerations currentStage = Enumerations.kDone;



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    //setting up joystick
    xBox = new Joystick(0);

    //setting followers for drive motors
    left2.follow(left1);
    right2.follow(right1);

    //finish setup for encoders
    leftEncoder = left1.getEncoder();
    rightEncoder = right1.getEncoder();
    climbEncoder = climb.getEncoder();

    //zeroing encoders and setting stage
    currentStage = Enumerations.kZero;
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
    climbEncoder.setPosition(0);


  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    //setting encoder values to smartdashboard
    SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
    SmartDashboard.putNumber("Climb Encoder", climbEncoder.getPosition());

    

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {

    //zeroing encoders and setting stage
    currentStage = Enumerations.kZero;
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
    intakeEncoder.setPosition(0);
    climbEncoder.setPosition(0);

  
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    
 
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    //setting stage to done
    currentStage = Enumerations.kDone;
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //speeds
    double drivespeed = .5;
    double turnspeed = .5;

    //drivetrain
    m_drive.tankDrive((xBox.getRawAxis(1) * turnspeed * -1), (xBox.getRawAxis(5) * drivespeed *-1));

    //RB Xbox button
    if (xBox.getRawButton(5)) {

      climb.set(-1);

    }
    //LB Xbox button
    else if (xBox.getRawButton(6)) {

      climb.set(1);

    }
    //when no buttons are active
    else {


    }

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
