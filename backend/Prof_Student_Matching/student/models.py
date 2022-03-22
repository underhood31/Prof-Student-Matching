from django.db import models
from prof.models import ResearchField

# Create your models here.
class Student(models.Model):
    class specialization(models.TextChoices):
        CSE = "CSE","Computer Science Engineering"
        ECE = "ECE","Electronics and Communication Enginneering"
        CSAI = "CSAI","Computer Science & Artificial Intelligence"
        CSD = "CSD","Computer Science & Design"
        CSSS = "CSSS","Computer Science & Social Science"
        CSB = "CSB","Computer Science & Bio Science"
        CSAM = "CSAM","Computer Science & Applied Mathematics"
    
    class degree(models.TextChoices):
        UG = "UG","Under Graduate Student"
        PG = "PG","Under Graduate Student"
        PhD = "PhD","Post Doctorate Student"


    first_name = models.CharField(max_length=30,help_text="First Name")
    sec_name = models.CharField(max_length=30,help_text="Second Name")
    email = models.CharField(max_length=256,primary_key=True)    
    cgpa = models.FloatField(null=False,help_text="Student's CGPA")
    roll_no = models.IntegerField(null=False,help_text="Student's Roll number")
    #des for designation
    res_interest1 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="student_res_interest1",null=True)
    res_interest2 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="student_res_interest2",null=True)
    res_interest3 = models.ForeignKey(ResearchField, on_delete=models.CASCADE,related_name="student_res_interest3",null=True)
    resume_link = models.CharField(max_length=200,help_text="This includes the student's resume link",null=True)
    proj_applied = models.JSONField(null=True,help_text="List of projects in which the student has applied")
    proj_selected = models.JSONField(null=True,help_text="List of projects in which the student has been selected")
    spec = models.CharField(
        max_length=4, 
        choices=specialization.choices,
        null=False,
        help_text="Students specialization",
        default="CSE"
    )
    deg_type = models.CharField(max_length = 4, choices=degree.choices,null=False,help_text="Student is a UG/PG or PhD student",default="UG")