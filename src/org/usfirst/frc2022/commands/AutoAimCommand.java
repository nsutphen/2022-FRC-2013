package org.usfirst.frc2022.commands;

import org.usfirst.frc2022.Joysticks.Xbox360;

public class AutoAimCommand extends CommandBase {

    /*
     * VARS
     */
    public final double middle = .01;
    public final double flat = 0.0;
    private double rotation;
    private double pitch;
    private double goal;
    private boolean on;
    private Xbox360 xboz;

    /**
     * requires cam, shooter, shooterPitch, and shooterRotation
     */
    public AutoAimCommand() {
        requires(cam);
        requires(shooter);
        requires(shooterPitch);
        requires(shooterRotation);
    }

    /**
     * enables pid for shooter; disables pid for shooterpitch and shooterrotation; 
     * sets everything to default location. Also, get the controller.
     */
    protected void initialize() {
        shooterPitch.disable();
        shooterRotation.disable();
        shooterPitch.setPitch(0);
        shooterRotation.setRotation(0);
        shooter.stop();
        xboz = oi.getXbawks();
        
    }
    
    /**
     * change the rotation, pitch, and speed using the autoaim when it is on; check if the
     * B button has been pressed and if so toggle the state of the autoaim
     */
    protected void execute() {
        process(cam.analyze());
        
            if((goal == 0.0) || (goal == 1.0) || (goal == 2.0)){
                shooter.setShooter(1); //Was originally 30 (ft/sec). Is this right?
                shooterPitch.setPitch(pitch);
                shooterRotation.setRotation(rotation);
            } else{
                shooter.stop();
            }
    }
    
    
    /**
     * see every other command
     */
    protected void end() {
        shooter.stop();
        shooterPitch.disable();
        shooterRotation.disable();
    }

    /**
     * see every other command
     */
    protected void interrupted() {
        shooter.stop();
        shooterPitch.disable();
        shooterRotation.disable();
    }

    /**
     * Convert the data from an array into variables (rotation,pitch,goal.
     * @param analyze the array to be analyzed
     */
    private void process(double[] analyze) {
        rotation = analyze[1];
        pitch = analyze[2];
        goal = analyze[0];
    }
    
    /**
     * Is the thing finished?
     * @return NO!
     */
    protected boolean isFinished() {
        return false;
    }
}
