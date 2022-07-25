package com.jcrm1.threedsinputcap;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class TaskSendSerial implements Runnable {
	private static final Collection<Key> down = Arrays.asList(new Key[] {Key.KEY_DDOWN});
	private static final Collection<Key> downleft = Arrays.asList(new Key[] {Key.KEY_DDOWN, Key.KEY_DLEFT});
	private static final Collection<Key> left = Arrays.asList(new Key[] {Key.KEY_DLEFT});
	private static final Collection<Key> upleft = Arrays.asList(new Key[] {Key.KEY_DUP, Key.KEY_DLEFT});
	private static final Collection<Key> up = Arrays.asList(new Key[] {Key.KEY_DUP});
	private static final Collection<Key> upright = Arrays.asList(new Key[] {Key.KEY_DUP, Key.KEY_DRIGHT});
	private static final Collection<Key> right = Arrays.asList(new Key[] {Key.KEY_DRIGHT});
	private static final Collection<Key> downright = Arrays.asList(new Key[] {Key.KEY_DDOWN, Key.KEY_DRIGHT});
	@Override
	public void run() {
		try {
			HashSet<Key> keys = Main.getActiveKeys();
			String str = "";
			if (keys.contains(Key.KEY_A)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_B)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_X)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_Y)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_L)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_R)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_SELECT)) str += "1,";
			else str += "0,";
			if (keys.contains(Key.KEY_START)) str += "1,";
			else str += "0,";
			if (keys.containsAll(downleft)) str += "224,";
			else if (keys.containsAll(upleft)) str += "315,";
			else if (keys.containsAll(upright)) str += "45,";
			else if (keys.containsAll(downright)) str += "135,";
			else if (keys.containsAll(up)) str += "0,";
			else if (keys.containsAll(down)) str += "180,";
			else if (keys.containsAll(left)) str += "270,";
			else if (keys.containsAll(right)) str += "90,";
			else str += "-1,";
			str += Main.getJoyX() + ",";
			str += Main.getJoyY() + ",";
			str += Main.getTouchX() + ",";
			str += Main.getTouchY() + "\n";
			System.out.print(str);
			Main.getSerialOutputStream().write(str.getBytes());
			Thread.sleep(1);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if (Main.running) Main.submitTask(new TaskSendSerial());
	}
}
