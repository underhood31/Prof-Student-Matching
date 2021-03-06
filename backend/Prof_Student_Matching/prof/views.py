from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action
from .serializers import ProfSerializer,ProjectSerializer,ResearchFieldSerializer
from .models import Prof,Project, ResearchField
# Create your views here.
class ProfViewSet(viewsets.ModelViewSet):
    queryset = Prof.objects.all()
    serializer_class = ProfSerializer

class ProjectViewSet(viewsets.ModelViewSet):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer

class ResearchFieldViewSet(viewsets.ModelViewSet):
    queryset = ResearchField.objects.all()
    serializer_class = ResearchFieldSerializer