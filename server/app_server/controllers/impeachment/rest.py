from flask import Blueprint
from app_server.controllers.impeachment.api_impleachment import ImpeachmentItem
from flask_restful import Api

bp_impeachment = Blueprint('impeachment', __name__, url_prefix='/impeachment_d_day/api/impeachment')
impeachment_rest = Api(bp_impeachment)
impeachment_rest.add_resource(ImpeachmentItem, '')
