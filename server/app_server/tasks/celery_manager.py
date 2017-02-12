from celery import Celery
from config.celeryconfig import CeleryConfig
from app_server.common.patterns.singleton import Singleton


class CeleryManager(Celery, metaclass=Singleton):
    def __init__(self, main=None, loader=None, backend=None,
                 amqp=None, events=None, log=None, control=None,
                 set_as_current=True, accept_magic_kwargs=False,
                 tasks=None, broker=None, include=None, changes=None,
                 config_source=None, fixups=None, task_cls=None,
                 autofinalize=True, config='config.celeryconfig.CeleryConfig',
                 **kwargs):
        super(CeleryManager, self). \
            __init__(__name__, broker=CeleryConfig.CELERY_BROKER_URL, baekend=CeleryConfig.CELERY_RESULT_BACKEND)
        self.config_from_object(config)

    def init_app(self, app, celery_config):
        print('init app')
        TaskBase = self.Task

        class ContextTask(TaskBase):
            abstract = True

            def __call__(self, *args, **kwargs):
                with app.app_context():
                    return TaskBase.__call__(self, *args, **kwargs)

        self.Task = ContextTask
