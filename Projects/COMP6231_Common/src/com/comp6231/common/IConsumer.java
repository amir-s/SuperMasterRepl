package com.comp6231.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public interface IConsumer {
	public void consume(InterMessage receiveMessage, DatagramPacket socket) throws IOException;
}
