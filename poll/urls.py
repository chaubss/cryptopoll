from django.urls import path, include
from .views import create_poll, get_polls, cast_vote
urlpatterns = [
    path('', get_polls, name='get_polls'),
    path('new/', create_poll, name='create_poll'),
    path('vote/', cast_vote, name='cast_vote'),
]