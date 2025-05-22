import { useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useAuth } from '../context/AuthContext';

const WebSocketNotifs = () => {
  const { user } = useAuth();
  const clientRef = useRef(null);

  useEffect(() => {
    if (!user?.userid) {
      console.log("Current user:", user);
      console.log("WebSocket not connected: user ID is not available yet.");
      return;
    }
    console.log("Current user:", user);
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('WebSocket connected');

        stompClient.subscribe(`/topic/notifications/${user.userid}`, (message) => {
          const body = JSON.parse(message.body);
          alert(`🔔 New Order Notification:\n${body.message}`);
        });
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame);
      },
    });

    clientRef.current = stompClient;
    stompClient.activate();

    return () => {
      clientRef.current?.deactivate();
    };
  }, [user?.userid]); // re-run only when userId is available

  return null;
};

export default WebSocketNotifs;
