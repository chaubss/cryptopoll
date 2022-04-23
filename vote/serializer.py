from rest_framework.serializers import ModelSerializer
from .models import Vote

class VoteSerializer(ModelSerializer):
    class Meta:
        model = Vote
        fields = ('id', 'user', 'choice', 'datetime', 'poll')