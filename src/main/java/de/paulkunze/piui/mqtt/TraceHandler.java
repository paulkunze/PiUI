package de.paulkunze.piui.mqtt;

import android.util.Log;

import org.eclipse.paho.android.service.MqttTraceHandler;

public class TraceHandler implements MqttTraceHandler{

    @Override
    public void traceDebug(String arg0, String arg1) {
        Log.i(arg0, arg1);
    }

    @Override
    public void traceError(String arg0, String arg1) {
        Log.e(arg0, arg1);
    }

    @Override
    public void traceException(String arg0, String arg1, Exception arg2) {
        Log.e(arg0, arg1, arg2);
    }
}
