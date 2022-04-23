from rest_framework import serializers
from .models import Poll
from choice.models import Choice


class PollChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Choice
        fields = ('id', 'name', 'votes')


class PollSerializer(serializers.ModelSerializer):
    choices = PollChoiceSerializer(many=True, read_only=True)
    class Meta:
        model = Poll
        fields = ('id', 'name', 'description', 'start_date', 'end_date', 'choices')