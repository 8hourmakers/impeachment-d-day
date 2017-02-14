# -*- coding: utf-8 -*-

from datetime import datetime, timedelta
import json
from flask import request, jsonify, current_app, session, abort
from sqlalchemy import asc, desc
from app_server.common.instances.db import db
from app_server.models.impeachment_model import Impeachment
from app_server.models.comment_model import Comment
from flask import abort
from flask_restful import Resource, reqparse, marshal
import codecs
from sqlalchemy import or_
from app_server.common.instances.db import db
from app_server.common.utils.time_util import str_to_datetime
from .ws_comment import new_comment
from app_server.common.instances.redis import impeachment_redis

class CommentItemList(Resource):


    def get(self):
        """
        코멘트 가지고오기
        :return:
        """
        start = request.args.get('start')
        comment_list = db.session.query(Comment).order_by(desc(Comment.id)).offset(start).limit(20).all()
        comment_serialize_list = []
        for each_comment in comment_list:
            comment_serialize_list.append(each_comment.serialize)

        return jsonify({'results': comment_serialize_list})


    def post(self):
        """
        코멘트 등록
        """
        sender_name = request.json.get('sender_name')
        content = request.json.get('content')
        if content is None:
            abort(406, "content is required")
        if sender_name is None:
            abort(406, "sender_name is required")
        if sender_name == 'admin':
            abort(406, "Only admin can send as admin")
        if sender_name == '8hourmakers':
            sender_name = 'admin'

        comment_expire = impeachment_redis.exists(sender_name)
        if comment_expire is True:
            abort(403, "Input too fast")
        impeachment_redis.setex(sender_name, '', 1)
        comment = Comment(
            sender_name=sender_name,
            content=content
        )
        db.session.add(comment)
        db.session.commit()
        new_comment(comment.serialize)

        return jsonify({'results': {'success': True}})

