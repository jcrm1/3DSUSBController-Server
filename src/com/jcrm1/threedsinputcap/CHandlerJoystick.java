package com.jcrm1.threedsinputcap;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CHandlerJoystick implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
//			System.out.println("Got");
			String response = "Y";
	        exchange.sendResponseHeaders(200, response.length());
	        OutputStream os = exchange.getResponseBody();
	        os.write(response.getBytes());
	        os.close();
//	        System.out.println("Wrote");
			String[] req = exchange.getRequestURI().toString().split("\\?");
			String[] keys = req[1].split(",");
			for (int i = 0; i < keys.length - 4; i++) {
				if (!keys[i].equals("") && Key.valueOf(keys[i].substring(1)) != null) {
					if (keys[i].substring(0, 1).equals("U")) Main.keyUp(Key.valueOf(keys[i].substring(1)));
					else if (keys[i].substring(0, 1).equals("D")) Main.keyDown(Key.valueOf(keys[i].substring(1)));
				}
			}
//			System.out.println(keys[keys.length - 2] + ":" + keys[keys.length - 1]);
			Main.setJoyX(Integer.parseInt(keys[keys.length - 4]));
			Main.setJoyY(Integer.parseInt(keys[keys.length - 3]));
			Main.setTouchX(Integer.parseInt(keys[keys.length - 2]));
			Main.setTouchY(Integer.parseInt(keys[keys.length - 1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
