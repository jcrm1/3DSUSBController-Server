package com.jcrm1.threedsinputcap;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class TaskSendRobotEvents implements Runnable {
	private Robot robot;
	public TaskSendRobotEvents(Robot robot) {
		this.robot = robot;
	}
	@Override
	public void run() {
		int touchX = Main.getTouchX();
		int touchY = Main.getTouchY();
		HashSet<Key> keys = Main.getActiveKeys();
		robot.mouseMove(touchX, touchY);
		if (keys.contains(Key.KEY_R)) robot.mousePress(1);
		else robot.mouseRelease(1);
		if (keys.contains(Key.KEY_L)) robot.mousePress(2);
		else robot.mouseRelease(2);
		if (keys.contains(Key.KEY_A)) robot.keyPress(KeyEvent.VK_SPACE);
		else robot.keyRelease(KeyEvent.VK_SPACE);
		if (keys.contains(Key.KEY_B)) robot.keyPress(KeyEvent.VK_R);
		else robot.keyRelease(KeyEvent.VK_R);
		if (keys.contains(Key.KEY_X) || keys.contains(Key.KEY_Y)) robot.keyPress(KeyEvent.VK_X);
		else robot.keyRelease(KeyEvent.VK_X);
		if (keys.contains(Key.KEY_X)) robot.keyPress(KeyEvent.VK_X);
		else robot.keyRelease(KeyEvent.VK_X);
		if (keys.contains(Key.KEY_CPAD_UP)) robot.keyPress(KeyEvent.VK_W);
		else robot.keyRelease(KeyEvent.VK_W);
		if (keys.contains(Key.KEY_CPAD_LEFT)) robot.keyPress(KeyEvent.VK_A);
		else robot.keyRelease(KeyEvent.VK_A);
		if (keys.contains(Key.KEY_CPAD_DOWN)) robot.keyPress(KeyEvent.VK_S);
		else robot.keyRelease(KeyEvent.VK_S);
		if (keys.contains(Key.KEY_CPAD_RIGHT)) robot.keyPress(KeyEvent.VK_D);
		else robot.keyRelease(KeyEvent.VK_D);
		if (Main.running) Main.submitTask(new TaskSendRobotEvents(robot));
	}
}
