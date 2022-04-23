from django.db import models
from poll.models import Poll

# Create your models here.
class Choice(models.Model):
    name = models.CharField(max_length=255)
    poll = models.ForeignKey(Poll, on_delete=models.CASCADE, related_name='choices')
    votes = models.IntegerField(default=0)
    def __str__(self):
        return self.poll.name + ' - ' + self.name