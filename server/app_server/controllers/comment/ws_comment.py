from flask_socketio import emit, join_room, Namespace
from app_server.common.instances.web_socket import socketio


def new_comment(serialized_comment):
    comment_payload = {
        'results': serialized_comment
    }
    socketio.emit('listen/new_comment', comment_payload, broadcast=True)
    return


@socketio.on('disconnect')
def socket_disconnect():
    print('disconnected')


@socketio.on('connect')
def room_connect():
    print('connected')
