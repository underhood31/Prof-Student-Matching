from django.db import models

# Create your models here.
class student(models.Model):
    first_name = models.CharField(max_length=30)
    sec_name = models.CharField(max_length=30)
    cgpa = models.FloatField(null=False)
    roll_no = models.IntegerField(null=False)
    #des for designation
    # research_interest = ArrayField(max_length=30,dbtype="char")

