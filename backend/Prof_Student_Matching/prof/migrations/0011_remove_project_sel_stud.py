# Generated by Django 4.0.3 on 2022-05-13 05:42

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('prof', '0010_remove_project_advisor_id_remove_project_apl_stud'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='project',
            name='sel_stud',
        ),
    ]
