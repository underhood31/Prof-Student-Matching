# Generated by Django 4.0.3 on 2022-03-21 13:07

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0003_alter_student_first_name_alter_student_sec_name'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='student',
            name='id',
        ),
        migrations.AlterField(
            model_name='student',
            name='roll_no',
            field=models.IntegerField(help_text="Student's Roll number", primary_key=True, serialize=False),
        ),
    ]
