# Generated by Django 4.0.3 on 2022-03-22 10:55

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('prof', '0004_researchfield_remove_prof_res_interest_and_more'),
        ('student', '0005_student_deg_type_student_spec'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='student',
            name='res_interest',
        ),
        migrations.AddField(
            model_name='student',
            name='res_interest1',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='student_res_interest1', to='prof.researchfield'),
        ),
        migrations.AddField(
            model_name='student',
            name='res_interest2',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='student_res_interest2', to='prof.researchfield'),
        ),
        migrations.AddField(
            model_name='student',
            name='res_interest3',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='student_res_interest3', to='prof.researchfield'),
        ),
        migrations.AddField(
            model_name='student',
            name='resume_link',
            field=models.CharField(help_text="This includes the student's resume link", max_length=200, null=True),
        ),
    ]