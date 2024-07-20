export class WebSocketService {
  socketUrl; // socket-url
  _newSocket = null; // new socket pending handshake of connection
  handlersMap;
  topics = {
    admin: ['userLogin', 'userRegistration'],
    expert: [],
  };

  constructor(socketUrl, jwtToken) {
    this.socketUrl = socketUrl;
    if (jwtToken) {
      this.connect(jwtToken);
    }
  }

  subscribeToWebSocketTopics(notificationService, userRole) {
    try {
      const userTopics = this.topics[userRole] || [];
      userTopics.forEach((topic) => {
        this.subscribe(topic, async (topic, json) => {
          // console.log(`Received WebSocket message for topic ${topic}:`, json);
          notificationService.notifications.push(json);
          // notificationService.saveNotificationForUser(topic, json, userId);
        });
      });
    } catch (error) {
      console.error(
        'An error occurred while subscribing to WebSocket topics:',
        error
      );
    }
  }

  connect(jwtToken) {
    this.handlersMap = new Map();

    let url = new URL(this.socketUrl);
    const urlString = JSON.stringify(url);

    // if is local use http and ws else https and wss
    const isLocal = urlString.includes('localhost');
    const ws = 'ws://';
    const http = isLocal ? 'http://' : 'https://';

    if (url.searchParams.has('token')) {
      url.searchParams.set('token', jwtToken);
    } else {
      url = new URL(`${this.socketUrl.replace(http, ws)}`);
      url.searchParams.set('token', jwtToken);
    }

    this._newSocket = new WebSocket(url.toString());

    const theAdaptor = this;
    theAdaptor._newSocket._isConnected = new Promise(function (
      resolve,
      reject
    ) {
      theAdaptor._newSocket.onopen = function () {
        console.log('Opened new WebSocket connection.');
        resolve();
      };
      theAdaptor._newSocket.onerror = function (error) {
        reject(error);
      };
    }).catch((error) => {
      console.error('WebSocket connection error:', error);
    });
    theAdaptor._newSocket.onmessage = (e) => {
      let msg = e.data.split(' ');
      if (msg[0] === 'notify') {
        console.log(`Received notification for target ${msg[1]}`);

        let json = null;
        try {
          const jsonString = e.data.substring(e.data.indexOf('{'));
          json = JSON.parse(jsonString);
        } catch (e) {
          console.log(`WARNING obsolete notification for ${e.data}`);
        }

        theAdaptor.distributeNotification(msg[1], json);
      }
    };
    theAdaptor._newSocket.onclose = () =>
      console.log('WebSocket connection closed.');
  }

  async disconnect() {
    if (this.isConnected()) {
      await this._newSocket.close();
    } else {
      console.log('WebSocket connection is not open.');
    }
  }

  isConnected() {
    return (
      this._newSocket &&
      this._newSocket.readyState !== WebSocket.CLOSED &&
      this._newSocket.readyState !== WebSocket.CLOSING
    );
  }

  distributeNotification(topic, json) {
    let handlers = this.handlersMap.get(topic.toString());
    if (handlers != null) {
      for (let handler of handlers) {
        if (typeof handler === 'function') {
          handler(topic, json);
        }
      }
    } else {
      console.log(`WARNING obsolete notification for ${topic} ${json}`);
    }
  }

  async subscribe(topic, handler) {
    let handlers = this.handlersMap.get(topic);
    if (handlers == null) {
      this.handlersMap.set(topic, [handler]);
      await this._newSocket._isConnected;
      await this.sendMessage(`subscribe ${topic}`);
    } else {
      const index = handlers.indexOf(handler);
      if (index < 0) {
        handlers.push(handler);
      } else console.log(`WARNING duplicate handler subscription for ${topic}`);
    }
  }

  unsubscribe(topic, handler) {
    let handlers = this.handlersMap.get(topic);

    if (handlers != null) {
      const index = handlers.indexOf(handler);

      if (index >= 0) {
        handlers.splice(index, 1);

        if (handlers.length === 0) {
          this.sendMessage(`unsubscribe ${topic}`);
          this.handlersMap.delete(topic);
        }
      } else {
        console.log(
          `WARNING trying to unsubscribe a non-subscribed handler for ${topic}`
        );
      }
    }
  }

  notify(topic) {
    this.sendMessage(`notify ${topic}`);
  }

  sendMessage(message) {
    console.log('New announcement:', message);
    this._newSocket.send(message);
  }
}
