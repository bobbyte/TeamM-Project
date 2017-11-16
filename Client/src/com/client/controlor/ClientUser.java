/**
 * this is a class to communicate with server
 * offer method: check user
 * 
 */

package com.client.controlor;

import com.common.model.*;

public class ClientUser {

	public boolean checkUser(User u) {
		return new ClientConServer().sendLoginInfoToServer(u);
	}
}
