__version__ = '0.1dev'
"""
    impeachment d-day
    ~~~~

"""

import hashlib
import eventlet
from datetime import timedelta
from flask import Flask
from app_server.common.instances.web_socket import socketio
import cgitb

cgitb.enable(format='text')
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

    socketio.init_app(app)

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
