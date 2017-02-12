from flask_socketio import SocketIO

class IMPEACHMENTSocketIO(SocketIO):
    def __init__(self, *args, **kargs):
        super().__init__(*args, **kargs)
        print('socketio init')

socketio = IMPEACHMENTSocketIO(async_mode=None, ping_timeout=15, message_queue='redis://')
