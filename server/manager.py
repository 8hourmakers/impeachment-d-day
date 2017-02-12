# -*- coding: utf-8 -*-

import os
import random
import sys
import time

from flask import current_app
from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager, Server

from app_server import create_app

app = create_app('config.default.Config')

from sqlalchemy.orm import sessionmaker
from app_server.common.instances.db import db
from app_server.models.impeachment_model import Impeachment
from app_server.models.comment_model import Comment
from app_server.common.utils.time_util import str_to_datetime

manager = Manager(app)
migrate = Migrate()
migrate.init_app(app, db, directory="./migrations")

server = Server(host="0.0.0.0", port=8082)
manager.add_command('db', MigrateCommand)


@manager.command
def initall():
    app.config['RUN'] = False
    createdb()
    init_impeachment()


@manager.command
def init_impeachment():
    impeachment_datetime = str_to_datetime(app.config['IMPEACHMENT_DATETIME'])
    impeachment = Impeachment(
        impeachment_datetime=impeachment_datetime
    )
    db.session.add(impeachment)
    db.session.commit()

@manager.command
def dummy_comment():
    for each_idx in range(0, 100):
        comment = Comment(
            sender_name='test',
            content='test'
        )
        db.session.add(comment)
    db.session.commit()

@manager.command
def createdb():
    db.init_app(app)
    db.create_all()


@manager.command
def dropdb():
    db.init_app(current_app)
    db.drop_all()

if __name__ == "__main__":
    manager.run()
