import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;
const subscribers = {};

export const connectWebsocket = (onConnect) => {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
            console.log('WebSocket connected');
            if (onConnect) onConnect();
        },
        onDisconnect: () => {
            console.log('WebSocket disconnected');
        },
        debug: (str) => {
            console.log(str);
        }
    });

    stompClient.activate();
    return stompClient;
};

export const subscribeToOrderUpdates = (orderId, callback) => {
    if (!stompClient || !stompClient.connected) {
        console.error('WebSocket not connected');
        return false;
    }

    const subscription = stompClient.subscribe(`/topic/orders/${orderId}`, message => {
        const orderUpdate = JSON.parse(message.body);
        callback(orderUpdate);
    });

    subscribers[orderId] = subscription;
    return true;
};

export const unsubscribeFromOrderUpdates = (orderId) => {
    if (subscribers[orderId]) {
        subscribers[orderId].unsubscribe();
        delete subscribers[orderId];
    }
};

export const disconnectWebsocket = () => {
    if (stompClient) {
        stompClient.deactivate();
        stompClient = null;
    }
};