# Generated by Django 4.0.3 on 2022-03-22 11:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('prof', '0006_alter_prof_room_no'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='researchfield',
            name='id',
        ),
        migrations.AlterField(
            model_name='researchfield',
            name='fieldName',
            field=models.CharField(max_length=500, primary_key=True, serialize=False),
        ),
    ]
