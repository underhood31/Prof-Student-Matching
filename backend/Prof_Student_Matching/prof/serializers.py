from rest_framework import serializers
from .models import Prof,Project,ResearchField

class ProfSerializer(serializers.ModelSerializer):
    class Meta:
        model = Prof
        fields = '__all__'

class ProjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = Project
        fields = '__all__'

        

class ResearchFieldSerializer(serializers.ModelSerializer):
    class Meta:
        model = ResearchField
        fields = '__all__'