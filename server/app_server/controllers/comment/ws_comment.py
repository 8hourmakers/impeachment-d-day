from flask_socketio import emit, join_room, Namespace
from app_server.common.instances.web_socket import socketio
from app_server.common.instances.redis import impeachment_redis


def new_comment(serialized_comment):
    comment_payload = {
        'results': serialized_comment
    }
    socketio.emit('listen/new_comment', comment_payload, broadcast=True)
    return


@socketio.on('disconnect')
def socket_disconnect():
    impeachment_redis.decr('member_num')
    member_num = impeachment_redis.get('member_num').decode('utf-8')
    member_num_payload = {
        'results': {
            'member_num': member_num
        }
    }
    socketio.emit('listen/update_member_num', member_num_payload, broadcast=True)
    print('disconnected')


@socketio.on('connect')
def socket_connect():
    impeachment_redis.incr('member_num')
    member_num = impeachment_redis.get('member_num').decode('utf-8')
    member_num_payload = {
        'results': {
            'member_num': member_num
        }
    }
    socketio.emit('listen/update_member_num', member_num_payload, broadcast=True)
    print('connected')
