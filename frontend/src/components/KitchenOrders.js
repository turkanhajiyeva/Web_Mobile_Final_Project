// src/components/KitchenOrders.js
import React, { useState, useEffect } from 'react'
import { Client } from '@stomp/stompjs'

export default function KitchenOrders() {
  const [orders, setOrders] = useState([])

  useEffect(() => {
    //create and configure STOMP client
    const client = new Client({
      // assumes Spring is exposing ws on /ws
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      debug: (msg) => console.debug('[STOMP]', msg),
    })

    client.onConnect = () => {
      //subscribe to the "orders" topic
      client.subscribe('/topic/orders', (message) => {
        const order = JSON.parse(message.body)
        setOrders((prev) => [order, ...prev])
      })
    }

    client.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message'])
    }

    client.activate()

    // cleanup on unmount
    return () => client.deactivate()
  }, [])

  return (
    <div className="kitchen-orders">
      <h1>Incoming Orders</h1>
      {orders.length === 0 && <p>No orders yet…</p>}
      <ul>
        {orders.map((o) => (
          <li key={o.id} className="order-card">
            <strong>Order #{o.id}</strong> @ Table {o.tableId} — {o.items.length} item
            {o.items.length > 1 && 's'}
            <ul>
              {o.items.map((it) => (
                <li key={it.menuItemId}>
                  {it.qty} × {it.name}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  )
}