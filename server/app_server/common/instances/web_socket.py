from flask_socketio import SocketIO

socketio = SocketIO(async_mode='eventlet', ping_timeout=15, message_queue='redis://')
