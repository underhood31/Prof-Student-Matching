from django.db import models
from django import forms

# Create your models here.
class Prof(models.Model):
    id = models.AutoField(primary_key=True)
    first_name = models.CharField(max_length=30)
    sec_name = models.CharField(max_length=30)
    des = models.CharField(max_length=30)
    #des for designation
    lab_name = models.CharField(max_length=30)
    res_interest = models.JSONField(null=True,help_text="This includes the field in which the prof is interested")
    stud_list = models.JSONField(null= True, help_text="This includes the list of students roll no which have worked under a particular prof")


class Project(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=30)
    descr = models.CharField(max_length=400)
    time_req = models.IntegerField() # Number of months req for project 
    tech_stack = models.JSONField(null=True,help_text="This includes the tech stack requirement for project")
    res_interest = models.JSONField(null=True,help_text="This includes the broad research field for a project")
    sel_stud = models.JSONField(null=True,help_text="Roll number of the students selected for this project")
    alloc_stat = models.BooleanField(default=False,help_text="This helps to determine if the project has been allocated to students")
    req_stu_no = models.IntegerField(null=False,help_text="This helps in determining the number of students required for a project")
    advisor_id = models.JSONField(null=True,help_text="This stores the advisor id's for this project")