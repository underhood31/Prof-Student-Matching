# Generated by Django 4.0.3 on 2022-03-22 11:19

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('prof', '0004_researchfield_remove_prof_res_interest_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='prof',
            name='contact',
            field=models.CharField(default='', max_length=13),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='prof',
            name='email',
            field=models.EmailField(default='', max_length=256),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='prof',
            name='room_no',
            field=models.CharField(default='', max_length=10),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='prof',
            name='website',
            field=models.CharField(default='', max_length=500),
            preserve_default=False,
        ),
    ]