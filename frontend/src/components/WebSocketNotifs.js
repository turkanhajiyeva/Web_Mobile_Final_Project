import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const WebSocketClient = () => {
  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('Connected to WebSocket');

        stompClient.subscribe('/topic/notifications', (message) => {
          const body = JSON.parse(message.body);
          alert(`New Order Notification:\n${body.message}`);
        });
      },
      onStompError: (frame) => {
        console.error('WebSocket error', frame);
      }
    });

    stompClient.activate();

    return () => {
      if (stompClient) stompClient.deactivate();
    };
  }, []);

  return null; 
};

export default WebSocketClient;
