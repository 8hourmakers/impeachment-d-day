__version__ = '0.1dev'
"""
    impeachment d-day
    ~~~~

"""

import hashlib
import eventlet
from datetime import timedelta
from flask import Flask, request
from app_server.common.instances.redis import impeachment_redis
from flask_socketio import emit, join_room, Namespace, leave_room, rooms
from redis import Redis

eventlet.monkey_patch(socket=True, select=True)
hash_mod = hashlib.sha1()

def create_app(config_filepath='config.default.DevelopmentConfig'):
    """
    The main generator for the application.
    You need to create app with this method: ::

    >>> app = create_app()

    You can insert config path 'config_filepath'
    It create flask instance and set config, initialize external module with app
    and register flask blueprint on the current_app

    - bp_ : real estate blueprint

    :param config_filepath: the application config file path
    :type config_filepath: str

    :returns: application instance
    """
    app = Flask(__name__)
    app.config.from_object(config_filepath)

    app.secret_key = app.config['SECRET_KEY']
    app.permanent_session_lifetime = timedelta(minutes=app.config['SESSION_ALIVE_MINUTES'])

    from app_server.common.instances.web_socket import socketio
    socketio.init_app(app, message_queue=app.config['SOCKETIO_REDIS_URL'])

    impeachment_redis = Redis(host=app.config['REDIS_HOST'], port=app.config['REDIS_PORT'], db=app.config['REDIS_DB'])
    impeachment_redis.set('member_num', 0)
    visit_cnt = impeachment_redis.get('visit_cnt')
    if visit_cnt is None:
        impeachment_redis.set('visit_cnt', 0)

    @socketio.on('disconnect')
    def test_disconnect():
        impeachment_redis.decr('member_num')
        member_num = impeachment_redis.get('member_num').decode('utf-8')
        visit_cnt = impeachment_redis.get('visit_cnt').decode('utf-8')
        member_num_payload = {
            'results': {
                'member_num': int(member_num),
                'visit_cnt': int(visit_cnt)
            }
        }
        emit('listen/update_member_num', member_num_payload, broadcast=True, namespace='/chat')

    @socketio.on('connect')
    def test_connect():
        emit('my_response', {'data': 'Connected', 'count': 0})
        impeachment_redis.incr('member_num')
        impeachment_redis.incr('visit_cnt')

        member_num = impeachment_redis.get('member_num').decode('utf-8')
        visit_cnt = impeachment_redis.get('visit_cnt').decode('utf-8')
        join_room('auth-' + str(member_num))
        member_num_payload = {
            'results': {
                'member_num': int(member_num),
                'visit_cnt': int(visit_cnt)
            }
        }
        emit('listen/update_member_num', member_num_payload, broadcast=True, namespace='/chat')

    from app_server.common.instances.redis import impeachment_redis

    # logging module
    from app_server.common.instances.db import db
    db.init_app(app)

    from app_server.common.instances.celery import celery
    celery.init_app(app, 'config.celeryconfig.CeleryConfig')

    from app_server.models.comment_model import Comment
    from app_server.controllers.impeachment.rest import bp_impeachment
    from app_server.controllers.comment.rest import bp_comment
    app.register_blueprint(bp_comment)
    app.register_blueprint(bp_impeachment)
    return app
