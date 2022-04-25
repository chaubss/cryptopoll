from django.urls import path, include
from .views import zkp_first, zkp_second

urlpatterns = [
    path('first/', zkp_first, name='zkp_first'),
    path('second/', zkp_second, name='zkp_second'),
]