from django.urls import path, include
from .views import start_miner

urlpatterns = [
    path('', start_miner, name='start_miner'),
]