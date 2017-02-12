from datetime import datetime
from app_server.common.instances.db import db


class Impeachment(db.Model):

    __tablename__ = 'impeachment'
    id = db.Column(db.Integer, autoincrement=True, primary_key=True, nullable=False)
    impeachment_datetime = db.Column(db.DateTime)
    register_timestamp = db.Column(db.DateTime, default=datetime.utcnow)

    @property
    def serialize(self):
        return {
            'id': self.id,
            'impeachment_datetime': int(self.register_timestamp.strftime('%s'))
        }

