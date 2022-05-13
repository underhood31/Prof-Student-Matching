# Generated by Django 4.0.3 on 2022-05-13 05:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('prof', '0012_project_advisor_id_project_apl_stud_project_sel_stud'),
    ]

    operations = [
        migrations.AlterField(
            model_name='project',
            name='advisor_id',
            field=models.CharField(default='[]', help_text="This stores the advisor's name for this project", max_length=400),
        ),
        migrations.AlterField(
            model_name='project',
            name='apl_stud',
            field=models.CharField(default='[]', help_text='Roll number of the students who applied this project', max_length=400),
        ),
    ]
