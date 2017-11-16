/**
 * This is a class to manage Chat page
 */
package com.client.tools;

import java.util.*;

import com.client.view.*;
public class ManageChat {

	private static HashMap hm=new HashMap<String, Chat>();
	
	//add
	public static void addChat(String loginIdAnFriendId,Chat chat)
	{
		hm.put(loginIdAnFriendId, chat);
	}
	//get
	public static Chat getChat(String loginIdAnFriendId )
	{
		return (Chat)hm.get(loginIdAnFriendId);
	}
	
}
