from django.db import models

# Create your models here.
class student(models.Model):
    first_name = models.CharField(max_length=30,help_text="First Name")
    sec_name = models.CharField(max_length=30,help_text="Second Name")
    cgpa = models.FloatField(null=False,help_text="Student's CGPA")
    roll_no = models.IntegerField(null=False,help_text="Student's Roll number")
    #des for designation
    res_interest = models.JSONField(null=True,help_text="This includes the field in which the student is interested")
    