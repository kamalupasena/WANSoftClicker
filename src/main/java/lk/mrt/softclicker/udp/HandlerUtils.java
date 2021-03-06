package lk.mrt.softclicker.udp;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;

class HandlerUtils {

    private static InterfaceAddress getLocalHost() throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        try {
            Enumeration list = NetworkInterface.getNetworkInterfaces();
            while (list.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) list.nextElement();
                if (iface == null) continue;
                if (!iface.isLoopback() && iface.isUp()) {
                    Iterator it = iface.getInterfaceAddresses().iterator();
                    while (it.hasNext()) {
                        InterfaceAddress address = (InterfaceAddress) it.next();
                        if (address == null) continue;
                        InetAddress broadcast = address.getBroadcast();
                        if (broadcast != null) {
                            return address;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            throw new Exception("Cannot read local interfaces.", e);
        }
        throw new Exception("Cannot not find local interface address.");
    }

    static InetAddress getBroadcast() throws Exception {
        return getLocalHost().getBroadcast();
    }
}
