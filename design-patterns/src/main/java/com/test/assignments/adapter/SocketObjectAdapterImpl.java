package com.test.assignments.adapter;

public class SocketObjectAdapterImpl implements SocketAdapter {

    Socket socket;

    public SocketObjectAdapterImpl(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Volt get120Volt() {
        return socket.getVolt();
    }

    @Override
    public Volt get60Volt() {
        Volt volt = socket.getVolt();
        return convertToVoltage(volt, 2);
    }
    @Override
    public Volt get30Volt() {
        Volt volt = socket.getVolt();
        return convertToVoltage(volt, 4);
    }

    private Volt convertToVoltage(Volt volt, int fraction) {
        return new Volt(volt.getVolt()/fraction);
    }
}
