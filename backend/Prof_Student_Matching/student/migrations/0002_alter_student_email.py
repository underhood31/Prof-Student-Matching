# Generated by Django 4.0.3 on 2022-03-22 17:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='student',
            name='email',
            field=models.CharField(max_length=256, primary_key=True, serialize=False),
        ),
    ]
