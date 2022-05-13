from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action
from .serializers import ProfSerializer,ProjectSerializer,ResearchFieldSerializer
from .models import Prof,Project, ResearchField
import ast
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
                setattr(prof_instance,field,request.data[field])
            prof_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)
    @action(methods=['post'], detail=True)
    def create_proj(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            prof_id = pk
            prof_instance = Prof.objects.get(email=prof_id)
            proj_lis = ast.literal_eval(getattr(prof_instance,"proj_list"))
            print(proj_lis)
            if request.data["res_interest1"]:
                res_1 = ResearchField.objects.get(id=int(request.data["res_interest1"]))
            else:
                res_1 = request.data["res_interest1"]
            if request.data["res_interest2"]:
                res_2 = ResearchField.objects.get(id=int(request.data["res_interest2"]))
            else:
                res_2 = request.data["res_interest2"]
            if request.data["res_interest3"]:
                res_3 = ResearchField.objects.get(id=int(request.data["res_interest3"]))
            else:
                res_3 = request.data["res_interest3"]

            proj_instance = Project.objects.create(
                                                    title = request.data["title"],
                                                    descr =  request.data["descr"],
                                                    time_req =  request.data["time_req"],
                                                    tech_stack =  request.data["tech_stack"],
                                                    alloc_stat =  request.data["alloc_stat"],
                                                    req_stu_no =  request.data["req_stu_no"],
                                                    advisor_id =  request.data["advisor_id"],
                                                    res_interest1 =  res_1,
                                                    res_interest2 = res_2,
                                                    res_interest3 =  res_3)
            proj_lis.append(getattr(proj_instance,"id")) 
            print(proj_lis)                                       
            setattr(prof_instance,"proj_list",str(proj_lis))
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
                print(request.data[field])
                setattr(proj_instance,field,request.data[field])
            proj_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)



class ResearchFieldViewSet(viewsets.ModelViewSet):
    queryset = ResearchField.objects.all()
    serializer_class = ResearchFieldSerializer