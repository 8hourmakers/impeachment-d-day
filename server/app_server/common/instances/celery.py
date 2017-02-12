from app_server.tasks.celery_manager import CeleryManager

celery = CeleryManager(config='config.celeryconfig.CeleryConfig')
