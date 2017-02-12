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
from app_server.common.instances.redis import impeachment_redis

class ImpeachmentItem(Resource):

    def get(self):
        """
        탄핵일 가져오기
        :return:
        """
        impeachment = db.session.query(Impeachment).order_by(desc(Impeachment.register_timestamp)).first()
        impeachment_res = impeachment.serialize
        member_num = impeachment_redis.get('member_num').decode('utf-8')
        impeachment_res['member_num'] = member_num
        return jsonify({'results': impeachment_res})


    def post(self):
        """
        탄핵일 등록 및 수정
        """
        impeachment_datetime_str = request.json.get('impeachment_datetime')
        impeachment_datetime = str_to_datetime(impeachment_datetime_str)
        if impeachment_datetime is None:
            abort(406, "impeachment_datetime format is invalid : %Y/%d/%m %H:%M:%S")
        impeachment = Impeachment(
            impeachment_datetime=impeachment_datetime
        )
        db.session.add(impeachment)
        db.session.commit()
        return jsonify({'results': {'success': True}})

