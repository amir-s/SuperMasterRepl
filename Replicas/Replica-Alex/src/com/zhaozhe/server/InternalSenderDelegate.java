package com.zhaozhe.server;

public interface InternalSenderDelegate {
	public void internalSenderDidReceiveMessage(ServerInfo serverInfo, ServerInfo fromServerInfo, InternalMessage receiveMessage);
	public void internalSenderDidReceiveAllMessages(ServerInfo serverInfo);
}
