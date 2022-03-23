from django.db import models
from django import forms

# Create your models here.
class ResearchField(models.Model):
    id = models.AutoField(primary_key=True,null=False)
    fieldName = models.CharField(max_length=500,null=False)

class Prof(models.Model):
    first_name = models.CharField(max_length=30)
    sec_name = models.CharField(max_length=30)
    email = models.CharField(max_length=256,primary_key=True)
    contact = models.CharField(max_length=13)
    room_no = models.CharField(max_length=30)
    website = models.CharField(max_length=500)
    des = models.CharField(max_length=30)
    #des for designation
    lab_name = models.CharField(max_length=30)
    res_interest1 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="prof_res_interest1",null=True)
    res_interest2 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="prof_res_interest2",null=True)
    res_interest3 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="prof_res_interest3",null=True)
    stud_list = models.JSONField(null= True, help_text="This includes the list of students roll no which have worked under a particular prof")


class Project(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=100)
    descr = models.CharField(max_length=400)
    time_req = models.IntegerField() # Number of months req for project 
    tech_stack = models.CharField(max_length=400,null=True,help_text="This includes the tech stack requirement for project")
    res_interest1 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="project_res_interest1",null=True)
    res_interest2 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="project_res_interest2",null=True)
    res_interest3 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="project_res_interest3",null=True)
    sel_stud = models.JSONField(null=True,help_text="Roll number of the students selected for this project")
    alloc_stat = models.BooleanField(default=False,help_text="This helps to determine if the project has been allocated to students")
    req_stu_no = models.IntegerField(null=False,help_text="This helps in determining the number of students required for a project")
    advisor_id = models.JSONField(null=True,help_text="This stores the advisor's name for this project")
    apl_stud = models.JSONField(null=True,help_text="Roll number of the students who applied this project")


