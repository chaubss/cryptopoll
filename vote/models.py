from django.db import models
from users.models import User
from choice.models import Choice
from poll.models import Poll
from block.models import Block

# Create your models here.
class Vote(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    choice = models.ForeignKey(Choice, on_delete=models.CASCADE)
    poll = models.ForeignKey(Poll, on_delete=models.CASCADE)
    datetime = models.DateTimeField(auto_now_add=True)
    block = models.ForeignKey(Block, on_delete=models.CASCADE)

    class Meta:
        unique_together = ('user', 'choice')
        unique_together = ('user', 'poll')
        ordering = ['datetime']