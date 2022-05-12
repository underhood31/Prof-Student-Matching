# Generated by Django 4.0.3 on 2022-05-12 18:01

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0002_alter_student_email'),
    ]

    operations = [
        migrations.AlterField(
            model_name='student',
            name='proj_applied',
            field=models.CharField(default='[]', help_text='List of projects in which the student has applied', max_length=400, null=True),
        ),
        migrations.AlterField(
            model_name='student',
            name='proj_selected',
            field=models.CharField(default='[]', help_text='List of projects in which the student has been selected', max_length=400, null=True),
        ),
    ]