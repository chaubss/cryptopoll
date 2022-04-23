from django.db import models

# Create your models here.
class Poll(models.Model):
    name = models.CharField(max_length=255)
    description = models.TextField()
    start_date = models.DateTimeField()
    end_date = models.DateTimeField()
    def __str__(self):
        return self.name

    