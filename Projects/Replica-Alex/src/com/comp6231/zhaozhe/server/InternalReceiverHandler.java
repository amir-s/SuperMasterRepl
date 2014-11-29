package com.comp6231.zhaozhe.server;

public interface InternalReceiverHandler {
	public void handle(InternalMessage receiveMessage, InternalMessage sendMessage);
}
