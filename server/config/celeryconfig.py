from datetime import timedelta
from celery.schedules import crontab


class CeleryConfig(object):
    """
        celery beat is a scheduler. It kicks off tasks at regular intervals,
        which are then executed by the worker nodes available in the cluster.
        crontab : If you want more control over when the task is executed,
        for example, a particular time of day or day of the week,
        you can use the crontab schedule type

    """

    CELERY_BROKER_URL = 'redis://localhost:6379/0'
    CELERY_RESULT_BACKEND = 'redis://localhost:6379/0'
    CELERY_ACCEPT_CONTENT = ['json', 'pickle']

    CELERYBEAT_SCHEDULE = {

    }
