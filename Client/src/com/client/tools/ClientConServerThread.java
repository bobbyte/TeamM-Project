/**
 * this is a thread between Client and Server
 */
package com.client.tools;

import java.io.*;
import java.net.*;

import com.client.view.Chat;
import com.client.view.FriendList;
import com.common.model.*;

public class ClientConServerThread extends Thread {

	private Socket s;

	public ClientConServerThread(Socket s) {
		this.s = s;
	}

	public void run() {
		while (true) {
			try {

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				if (m.getMesType().equals(MessageType.message_comm_mes)) {

					// show message which is from server on Chat page
					Chat chat = ManageChat.getChat(m.getGetter() + " " + m.getSender());

					chat.showMessage(m);
				} else if (m.getMesType().equals(MessageType.message_ret_onLineFriend)) {
					String con = m.getCon();
					String friends[] = con.split(" ");
					String getter = m.getGetter();
					System.out.println("getter=" + getter);

					FriendList qqFriendList = ManageFriendList.getQqFriendList(getter);

					// update Friend
					if (qqFriendList != null) {
						qqFriendList.upateFriend(m);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

}
