cd ..
source venv/bin/activate

if screen -list | grep -q "celery"; then
    echo 'celery process running'
else
    screen -S celery -d -m celery worker -A run.celery -P eventlet --loglevel=debug
    echo 'run celery'
fi


if screen -list | grep -q "gunicorn"; then
    echo 'gunicorn process running'
else
    screen -S gunicorn -d -m gunicorn -b 0.0.0.0:8080 --worker-class eventlet -w 1 run:app
    echo 'run gunicorn'
fi
