from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('auth/', include('authentication.urls')),
    path('poll/', include('poll.urls')),
    path('miner/', include('miner.urls')),
    path('block/', include('block.urls')),
    path('zkp/', include('zkp.urls')),
]
