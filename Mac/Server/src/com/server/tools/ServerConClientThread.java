/**
 * this is a thread between Server and Client
 */
package com.server.tools;

import java.util.*;

import com.common.model.*;

import java.net.*;
import java.io.*;

public class ServerConClientThread extends Thread {

	Socket s;

	public ServerConClientThread(Socket s) {
		this.s = s;
	}

	public void notifyOther(String iam) {
		// get all online user threads
		HashMap hm = ManageServerConClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			Message m = new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);

			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageServerConClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void run() {

		while (true) {

			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				// check the message from a client, then make response
				if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// get thread from getter
					ServerConClientThread sc = ManageServerConClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				} else if (m.getMesType().equals(MessageType.message_get_onLineFriend)) {
					String res = ManageServerConClientThread.getAllOnLineUserid();
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
