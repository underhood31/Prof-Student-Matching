from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action
from .serializers import ProfSerializer,ProjectSerializer,ResearchFieldSerializer
from .models import Prof,Project, ResearchField
# Create your views here.
class ProfViewSet(viewsets.ModelViewSet):
    queryset = Prof.objects.all()
    serializer_class = ProfSerializer
    @action(methods=['post'], detail=True)
    def update_fields(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            prof_id = pk
            prof_instance = Prof.objects.get(email=prof_id)
            for field in request.data:
                if field == "stud_list" :
                    setattr(prof_instance,field,request.data[field])
                else:
                    pass
            prof_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)
    

class ProjectViewSet(viewsets.ModelViewSet):
    queryset = Project.objects.all()
    serializer_class = ProjectSerializer
    @action(methods=['post'], detail=True)
    def update_fields(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            proj_id = pk
            proj_instance = Project.objects.get(id=proj_id)
            for field in request.data:
                setattr(proj_instance,field,request.data[field])
            proj_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)

class ResearchFieldViewSet(viewsets.ModelViewSet):
    queryset = ResearchField.objects.all()
    serializer_class = ResearchFieldSerializer