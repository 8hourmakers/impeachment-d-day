from datetime import datetime
from app_server.common.instances.db import db

class Comment(db.Model):

    __tablename__ = 'comment'
    id = db.Column(db.Integer, autoincrement=True, primary_key=True, nullable=False)
    sender_name = db.Column(db.String(32))
    content = db.Column(db.String(256))
    register_timestamp = db.Column(db.DateTime, default=datetime.utcnow)

    @property
    def serialize(self):
        return {
            'id': self.id,
            'sender_name': self.sender_name,
            'content': self.content,
            'register_timestamp': int(self.register_timestamp.strftime('%s'))
        }
