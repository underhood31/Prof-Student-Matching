# Generated by Django 4.0.3 on 2022-03-20 18:58

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0002_student_res_interest_alter_student_cgpa_and_more'),
    ]

    operations = [
        migrations.AlterField(
            model_name='student',
            name='first_name',
            field=models.CharField(help_text='First Name', max_length=30),
        ),
        migrations.AlterField(
            model_name='student',
            name='sec_name',
            field=models.CharField(help_text='Second Name', max_length=30),
        ),
    ]
