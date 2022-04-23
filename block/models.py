from django.db import models

# Create your models here.
class Block(models.Model):
    timestamp = models.DateTimeField(auto_now_add=True)
    height = models.IntegerField(default=0)
    hash = models.CharField(max_length=255)
    previous_hash = models.CharField(max_length=255)
    status = models.IntegerField(default=0)
    nounce = models.IntegerField(null=True)

    class Meta:
        ordering = ['height']
        unique_together = ('height',)

    def __str__(self):
        return self.hash
