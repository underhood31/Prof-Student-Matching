from django.db import models
from django import forms

# Create your models here.
class prof(models.Model):
    first_name = models.CharField(max_length=30)
    sec_name = models.CharField(max_length=30)
    des = models.CharField(max_length=30)
    #des for designation
    lab_name = models.CharField(max_length=30)
    res_interest = models.JSONField(null=True,help_text="This includes the field in which the prof is interested")

class project(models.Model):
    title = models.CharField(max_length=30)
    descr = models.CharField(max_length=400)
    time_req = models.IntegerField() # Number of months req for project 
    tech_stack = models.JSONField(null=True,help_text="This includes the tech stack requirement for project")
    res_interest = models.JSONField(null=True,help_text="This includes the broad research field for a project")
