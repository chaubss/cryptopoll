from rest_framework import serializers
from .models import Block
from vote.models import Vote
from users.models import User
from choice.models import Choice

class BlockSerializer(serializers.ModelSerializer):
    class Meta:
        model = Block
        fields = ('height', 'hash', 'previous_hash', 'timestamp', 'status', 'nounce')

class BlockVoteSerializer(serializers.ModelSerializer):
    user = serializers.SlugRelatedField(slug_field='full_name', queryset=User.objects.all())
    choice = serializers.SlugRelatedField(slug_field='name', queryset=Choice.objects.all())
    poll = serializers.SlugRelatedField(slug_field='name', queryset=Choice.objects.all())
    class Meta:
        model = Vote
        fields = ('id', 'datetime', 'user', 'choice', 'poll', )