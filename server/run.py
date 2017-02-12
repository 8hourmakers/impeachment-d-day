from app_server.common.instances.db import db
from app_server import create_app

app = create_app('config.default.Config')

from app_server.common.instances.celery import celery
import cgitb
cgitb.enable(format='text')

if __name__ == '__main__':
    db.create_all(app=app)
    app.run(host="0.0.0.0", port=8080, debug=True, threaded=True)
