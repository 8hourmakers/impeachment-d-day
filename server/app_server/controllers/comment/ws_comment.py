from flask_socketio import emit, join_room, Namespace, leave_room, rooms
from app_server.common.instances.web_socket import socketio
from app_server.common.instances.redis import impeachment_redis


class CommentNamespace(Namespace):
    def on_connect(self):
        print('ws connected')
        emit('listen/connect', {'results': 'success'})

    def on_disconnect(self):
        print('Client disconnected')


def new_comment(serialized_comment):
    comment_payload = {
        'results': serialized_comment
    }
    socketio.emit('listen/new_comment', comment_payload, broadcast=True)
    print(comment_payload)
    return


# socketio.on_namespace(CommentNamespace('/global'))
