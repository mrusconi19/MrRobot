package pack;

import java.io.File;
import java.util.Random;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class BumperTest {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	Random ran;
	TouchSensor touch;
	
	public static void main(String[] args) {
		
		new BumperTest();
		
	}
	
	public BumperTest() {
		
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 4).offset(-7.3);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 4).offset(7.3);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL); 
		MovePilot pilot = new MovePilot(chassis);
		ran = new Random();
		Brick brick = BrickFinder.getDefault();
		Port s2 = brick.getPort("S2");
		EV3TouchSensor sensor = new EV3TouchSensor(s2);
		touch = new TouchSensor(sensor);
		Sound.playSample(new File("loud-Walle1.wav"), Sound.VOL_MAX);
		
		pilot.setLinearSpeed(5);
		pilot.setAngularSpeed(45);
		pilot.forward();
		while (true) {
			Delay.msDelay(100);
			if (touch.pressed()) {
				Sound.playSample(new File("loud-Whoa.wav"), Sound.VOL_MAX);
				pilot.stop();
				pilot.travel(-10);
				if (ran.nextBoolean()) {
					pilot.rotate(90);
				} else {
					pilot.rotate(-90);
				}
				pilot.forward();
			}
			if (Button.ESCAPE.isDown()) {
				pilot.stop();
				Sound.playSample(new File("loud-Tada.wav"), Sound.VOL_MAX);
				sensor.close();
				System.exit(0);
			}
		}
	}

}
