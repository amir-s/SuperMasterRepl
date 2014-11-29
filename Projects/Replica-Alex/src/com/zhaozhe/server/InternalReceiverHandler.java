package com.zhaozhe.server;

public interface InternalReceiverHandler {
	public void handle(InternalMessage receiveMessage, InternalMessage sendMessage);
}
