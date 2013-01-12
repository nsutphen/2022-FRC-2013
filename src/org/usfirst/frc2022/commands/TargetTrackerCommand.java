package org.usfirst.frc2022.commands;

import org.usfirst.frc2022.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2022.subsystems.PWM_Generic;
//PWH
public class TargetTrackerCommand extends CommandBase {

    /*
     * The portion of the image that the box can be in
     */
    public final double middle = .01;

    public TargetTrackerCommand() {
        requires(cam);
        requires(camServos);
        //requires(pwmGeneric);
    }

    protected void initialize() {
    }

    protected void execute() {
        System.out.println("Autoaim doing stuff!");
        ParticleAnalysisReport[] report = cam.analyze();
        //Utils.MecanumDrive(pwmGeneric, 0, .2, 0);

        if (report.length < 1) {
            SmartDashboard.putString("DEBUG", "Nothing greenish found");
            return;
        }
        ParticleAnalysisReport particle = report[0];
        double xpos = particle.center_mass_x_normalized;
        //double ypos = particle.center_mass_y_normalized;
        //burn!!!
        //123/0
        double multiplyer = 2;
        double rotate = Utils.sign(xpos) * multiplyer;
        //double pitch = Utils.sign(ypos) * multiplyer;

        SmartDashboard.putString("DEBUG", "X = " + xpos + /*", Y= " + ypos +*/ "; r=" + rotate/* + ",p=" + pitch*/);
        //double angle = oi.getXbawks().GetBValue()?90:270;
        //Utils.MecanumDrive(pwmGeneric, angle, .1, 0);
        camServos.setRotateAngle(camServos.getRotateAngle() + rotate);
        //camServos.setPitchAngle(camServos.getRotateAngle() + pitch);

        camServos.updateSD();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
