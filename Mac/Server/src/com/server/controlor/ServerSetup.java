/**
 * this is server, it is monitoring....
 * waiting one client to connect 
 */
package com.server.controlor;

import com.common.model.*;
import com.server.tools.ManageServerConClientThread;
import com.server.tools.ServerConClientThread;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerSetup {

	public ServerSetup() {

		try {

			// port 9999 monitoring 
			System.out.println("I am a server, I'm monitoring 9999 port....");
			ServerSocket ss = new ServerSocket(9999);

			while (true) {
				Socket s = ss.accept();

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				Message m = new Message();
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				
				if (u.getPasswd().equals("123456")) {
					m.setMesType("1");
					oos.writeObject(m);

					ServerConClientThread scct = new ServerConClientThread(s);
					ManageServerConClientThread.addClientThread(u.getUserId(), scct);

					scct.start();

					scct.notifyOther(u.getUserId());
				} else {
					m.setMesType("2");
					oos.writeObject(m);
					s.close();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
	
}
