/*
 * Copyright (C) 2011-2017 ARM Limited. All rights reserved.
 */
package com.mbed.coap.observe;

import com.mbed.coap.exception.CoapCodeException;
import com.mbed.coap.exception.CoapException;
import com.mbed.coap.packet.Code;
import com.mbed.coap.server.CoapExchange;
import com.mbed.coap.server.CoapServer;

/**
 * @author szymon
 */
public class SimpleObservableResource extends AbstractObservableResource {

    private String body;

    public SimpleObservableResource(String body, CoapServer coapServer) {
        super(coapServer);
        this.body = body;
    }

    public SimpleObservableResource(String body, CoapServer coapServer, boolean includeObservableFlag) {
        super(coapServer, includeObservableFlag);
        this.body = body;
    }

    @Override
    public void get(CoapExchange exchange) throws CoapCodeException {
        exchange.setResponseBody(body);
        exchange.setResponseCode(Code.C205_CONTENT);
        exchange.sendResponse();
    }

    /**
     * Changes body for this resource, sends notification to all subscribers.
     *
     * @param body new payload
     * @throws CoapException coap exception
     */
    public void setBody(String body) throws CoapException {
        this.body = body;
        notifyChange(body.getBytes(), null);
    }

    public void setBody(String body, NotificationDeliveryListener deliveryListener) throws CoapException {
        this.body = body;
        notifyChange(body.getBytes(), null, null, null, deliveryListener);
    }

    public String getBody() {
        return body;
    }

    public void setConfirmNotification(boolean confirmNotification) {
        this.setConNotifications(confirmNotification);
    }

    public void terminateObservations() throws CoapException {
        this.notifyTermination();
    }

    public int getObservationsAmount() {
        return this.obsRelations.size();
    }
}