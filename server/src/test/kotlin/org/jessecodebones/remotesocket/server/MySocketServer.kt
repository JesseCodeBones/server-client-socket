package org.jessecodebones.remotesocket.server

import org.junit.Test
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel

class MySocketServer {

    @Test
    fun createServer(){
        ServerSocketChannel.open().let {
            it.bind(InetSocketAddress(10010))
            while (true) {
                val socketChannel = it.accept()
                if (socketChannel != null) {
                    Thread {
                        SocketClientSpec(socketChannel.socket()).process()
                    }.start()
                }
            }
        }
    }
}