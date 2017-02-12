from flask import Blueprint
from app_server.controllers.comment.api_comment import CommentItemList
from flask_restful import Api

bp_comment = Blueprint('comment', __name__, url_prefix='/impeachment_d_day/api/comment')
comment_rest = Api(bp_comment)

comment_rest.add_resource(CommentItemList, '')
