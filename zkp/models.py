from django.db import models

# Create your models here.

class ZKPObject(models.Model):
    h = models.IntegerField()
    y = models.IntegerField()
    b = models.IntegerField()

    def __str__(self):
        return str(self.h) + " " + str(self.y) + " " + str(self.b)