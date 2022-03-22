from django.db import models

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
    cgpa = models.FloatField(null=False,help_text="Student's CGPA")
    roll_no = models.IntegerField(null=False,help_text="Student's Roll number",primary_key=True)
    #des for designation
    res_interest = models.JSONField(null=True,help_text="This includes the field in which the student is interested")
    resume_link = models.CharField(max_length=200,help_text="This includes the student's resume link",null=True)
    spec = models.CharField(
        max_length=4, 
        choices=specialization.choices,
        null=False,
        help_text="Students specialization",
        default="CSE"
    )
    deg_type = models.CharField(max_length = 4, choices=degree.choices,null=False,help_text="Student is a UG/PG or PhD student",default="UG")