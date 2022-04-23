from django.urls import path, include
from .views import get_blocks, get_block_votes

urlpatterns = [
    path('', get_blocks, name='get_blocks'),
    path('votes/', get_block_votes, name='get_block_votes'),
]