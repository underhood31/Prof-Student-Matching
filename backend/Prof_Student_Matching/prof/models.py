from django.db import models
from django import forms

FIELD_CHOICES =(
    ("AI", "Artificial Intelligence"),
    ("Sys", "Systems"),
    ("Bio", "Bio Sciences"),
    ("Mth", "Mathematics"),
)    

# Create your models here.
class prof(models.Model):
    first_name = models.CharField(max_length=30)
    sec_name = models.CharField(max_length=30)
    des = models.CharField(max_length=30)
    #des for designation
    lab_name = models.CharField(max_length=30)
    res_interest = models.MultipleChoiceField(choice = FIELD_CHOICES)

class project(models.Model):
    FIELD_CHOICES =(
        ("AI", "Artificial Intelligence"),
        ("Sys", "Systems"),
        ("Bio", "Bio Sciences"),
        ("Mth", "Mathematics"),
    )    
    title = models.CharField(max_length=30)
    descr = models.CharField(max_length=400)
    time_req = models.IntegerField() # Number of months req for project 
    tech_stack = models.CharField(max_length=100)
    field = forms.MultipleChoiceField(choice = FIELD_CHOICES)