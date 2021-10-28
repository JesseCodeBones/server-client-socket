package org.jessecodebones.remotesocket.server

import java.io.*
import java.net.Socket
import java.util.regex.Pattern

class SocketClientSpec(private val socket:Socket) {


    object Configuration {
        private const val specName:String = "BASIC"
        val pattern: Pattern = Pattern.compile("${specName}:(.*)")
        const val token:String = "123321"
    }

    private fun legal():Boolean{
        BufferedReader(InputStreamReader(socket.getInputStream())).readLine().also {
            Configuration.pattern.matcher(it).also {
                if (it.find()) {
                    return it.group(1) == Configuration.token
                }
            }
        }
        return false;
    }

    fun process() {
        if (!legal()) {
            socket.close()
        } else {
            BufferedReader(InputStreamReader(socket.getInputStream())).also { reader ->
                var line = reader.readLine()
                while (line != null && line != "exit") {
                    BufferedWriter(OutputStreamWriter(socket.getOutputStream())).also { writer ->
                        writer.write("from server:$line")
                        writer.flush()
                    }
                    line = reader.readLine()
                }
                println("close socket connection")
                socket.close()
            }
        }
    }
}