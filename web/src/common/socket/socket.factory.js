import io from 'socket.io-client';

let id = 0;

class Socket {
    constructor() {
        const injector = Socket.injector;

        this.id = id++;
        this.client = null;
        this.url = 'http://localhost/';
        this.options = {
            multiplex: true,
            'force new connection': true
        };
        this.listeners = [];

        this.$rootScope = injector.$rootScope;
    }

    connect() {
        const id = this.id;
        const $rootScope = this.$rootScope;

        this.client = io(this.url, this.options);

        this.client.on('connect', () => {
            $rootScope.$broadcast('SOCKET_CONNECT', { id });
        });

        this.client.on('disconnect', () => {
            $rootScope.$broadcast('SOCKET_DISCONNECT', { id });
        });

        this.client.on('reconnect', () => {
            $rootScope.$broadcast('SOCKET_RECONNECT', { id });
        });

        this.client.on('connect_error', (error) => {
            $rootScope.$broadcast('SOCKET_ERROR', { id, error });
        });

        this.client.on('reconnect_error', (error) => {
            $rootScope.$broadcast('SOCKET_ERROR', { id, error });
        });

        return this;
    }

    listen(listener, callback) {
        if (!this.client) throw new Error();

        this.client.on(listener, (message) => {
            callback(message);
        });

        this.listeners.push(listener);

        return this;
    }

    disconnect() {
        if (!this.client) return this;

        this.client.off('connect');
        this.client.off('disconnect');
        this.client.off('reconnect');
        this.client.off('connect_error');
        this.client.off('reconnect_error');

        this.listeners.forEach((listener) => {
            this.client.off(listener);
        });

        if (this.client.connected) {
            this.client.disconnect();
        }

        this.client = null;

        return this;
    }
}

export default ['$rootScope', ($rootScope) => {
    Socket.injector = { $rootScope };
    return Socket;
}];
