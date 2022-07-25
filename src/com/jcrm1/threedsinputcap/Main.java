package com.jcrm1.threedsinputcap;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fazecast.jSerialComm.SerialPort;
import com.sun.net.httpserver.HttpServer;

public class Main {
	private static SerialPort port = null;
	private static ExecutorService pool = Executors.newFixedThreadPool(1);
	private static HashSet<Key> activeKeys = new HashSet<Key>();
	private static int joyX = 0;
	private static int joyY = 0;
	private static int touchX = 0;
	private static int touchY = 0;
	public static boolean running = true;
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: <address> <port>");
			System.exit(1);
		}
		try {
			InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.err.println("Invalid address");
			System.exit(3);
		}
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Invalid port");
			System.exit(2);
		}
		OS os = null;
		String osStr = System.getProperty("os.name").toLowerCase();
		if (osStr.contains("window")) os = OS.WINDOWS;
		else if (osStr.contains("mac")) os = OS.MACOS;
		else if (osStr.contains("linux")) os = OS.LINUX;
		System.out.println("Running on " + os.toString().toLowerCase());
		for (SerialPort pt : SerialPort.getCommPorts()) {
			if (os == OS.MACOS) {
				if (pt.getSystemPortName().contains("usbmodem")) port = pt;
			} else if (os == OS.WINDOWS) {
				if (!pt.getSystemPortName().equals("COM1") && pt.getSystemPortName().contains("COM")) port = pt;
			} else if (os == OS.LINUX) {
				if (pt.getSystemPortName().contains("ttyS")) port = pt;
			}
		}
		if (port == null) {
			System.out.println("No port!");
			throw new Exception("No applicable serial port found (Device not connected/OS or port type unsupported");
		}
		port.openPort();
		port.setBaudRate(115200);
		HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getByName(args[0]), Integer.parseInt(args[1])), 0);
        server.createContext("/c", new CHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Started");
        submitTask(new TaskSendSerial());
        Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				running = false;
				port.closePort();
			}
        	
        });
    }
	public static OutputStream getSerialOutputStream() {
		return port.getOutputStream();
	}
	public static void submitTask(Runnable task) {
		pool.submit(task);
	}
	public static boolean keyDown(Key key) {
		return activeKeys.add(key);
	}
	public static boolean keyUp(Key key) {
		return activeKeys.remove(key);
	}
	public static HashSet<Key> getActiveKeys() {
		return activeKeys;
	}
	public static void setJoyX(int x) {
		joyX = x;
	}
	public static void setJoyY(int y) {
		joyY = y;
	}
	public static int getJoyX() {
		return joyX;
	}
	public static int getJoyY() {
		return joyY;
	}
	public static int getTouchX() {
		return touchX;
	}
	public static void setTouchX(int touchX) {
		Main.touchX = touchX;
	}
	public static int getTouchY() {
		return touchY;
	}
	public static void setTouchY(int touchY) {
		Main.touchY = touchY;
	}
}
