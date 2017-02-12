from flask import request
from flask_socketio import emit, join_room, Namespace, leave_room, rooms
from app_server.common.instances.web_socket import socketio
from app_server.common.instances.redis import impeachment_redis

thread = None

def new_comment(serialized_comment):
    comment_payload = {
        'results': serialized_comment
    }
    socketio.emit('listen/new_comment', comment_payload, broadcast=True, namespace='/chat')
    print(comment_payload)
    return
