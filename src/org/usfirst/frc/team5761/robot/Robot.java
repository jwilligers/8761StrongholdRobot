package org.usfirst.frc.team5761.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    static final Logger LOG = LoggerFactory.getLogger(Robot.class);

    //RobotDrive myRobot;
    Joystick stick;
    Spark leftMC;
    Spark rightMC;
    int autoLoopCounter;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        LOG.info("robotInit: BEGIN");

        // load the DriverStation SmartDashboard
        new DriverStationSmartDashboard();  // ToDo: perhaps this should be static

        //myRobot = new RobotDrive(0, 1);
        leftMC = new Spark(0);
        rightMC = new Spark(1);
        stick = new Joystick(0);

        LOG.info("robotInit: END");
    }

    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
        autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if (autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
        {
            //myRobot.drive(-0.5, 0.0);    // drive forwards half speed
            autoLoopCounter++;
        } else {
            //myRobot.drive(0.0, 0.0);    // stop robot
        }
    }

    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit() {

        LOG.info("teleopInit: BEGIN");
        LOG.info("teleopInit: END");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        //myRobot.arcadeDrive(stick);
        double side = stick.getX();
        double speed = stick.getY();
        double throttle = stick.getThrottle();
        //LOG.debug("teleopPeriodic: raw [side:" + side + "][speed:" + speed + "][throttle:" + throttle + "]");

        throttle = (throttle - 1)/2;
        speed = speed * throttle;
        side = side * throttle;

        double appliedSpeed = speed * throttle;

        LOG.debug("teleopPeriodic: [side:" + side + "][speed:" + speed + "][throttle:" + throttle + "]");
        SmartDashboard.putString("side", ""+side);
        SmartDashboard.putString("speed", ""+speed);
        SmartDashboard.putString("throttle", ""+throttle);



        double leftMotorPower = -speed + -side ;
        double rightMotorPower = speed + -side;


        drive(leftMotorPower, rightMotorPower);

        //This is a TEST - WILL REMOVE UNLESS THIS IS THE BEST OPTION
       // if (button() )
    }

    /**
     * This method will drive the motors of the robot based on the inputs.
     *
     * @param leftPower
     * @param rightPower
     */
    private void drive(double leftPower, double rightPower)
    {
        LOG.debug("drive: [leftPower:" + leftPower + "][rightPower:" + rightPower + "]");

        SmartDashboard.putString("leftPower", ""+leftPower);
        SmartDashboard.putString("rightPower", ""+rightPower);

        leftMC.set(leftPower);
        rightMC.set(rightPower);

    }

    private void stop()
    {
        leftMC.set(0);
        rightMC.set(0);

        LOG.debug("Stop");

    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

}
