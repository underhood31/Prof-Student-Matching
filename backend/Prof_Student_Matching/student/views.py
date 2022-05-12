import email
import ast
from http.client import HTTPResponse
from django.shortcuts import render
from django.http import HttpResponse
from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action
from .serializers import StudentSerializer
from .models import Student
from prof.models import Project

class StudentViewSet(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    serializer_class = StudentSerializer


    @action(methods=['get'], detail=False)
    def get_stud(self,request):
        return Response("Hello world",status=status.HTTP_200_OK)

    def perform_create(self, serializer):
        serializer.save()

    @action(methods=['post'], detail=True)
    def update_fields(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            stud_id = pk
            stud_instance = Student.objects.get(email=stud_id)
            for field in request.data:
                setattr(stud_instance,field,request.data[field])
            stud_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)

    @action(methods=['post'], detail=True)
    def add_proj_applied(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            stud_id = pk
            stud_instance = Student.objects.get(email=stud_id)
            
            for field in request.data:
                cur_lis = set()
                if(getattr(stud_instance,field)!="[]"):
                    cur_lis = set(map(int,getattr(stud_instance,field).strip('][').split(',')))
                new_add = int(request.data[field])
                cur_lis.add(new_add)
                final_proj = str(list(cur_lis))
                setattr(stud_instance,field,final_proj)
                
                proj_instance = Project.objects.get(id=new_add)
                app_username = set()
                # print(app_username)
                if(getattr(proj_instance,"apl_stud")!="[]"):
                    app_username = set(ast.literal_eval(getattr(proj_instance,"apl_stud")))
                    # app_username = set(getattr(proj_instance,"apl_stud").strip('][').split(","))
                print(app_username,type(app_username))
                app_username.add(stud_id)
                # print(app_username)

                print(str(list(app_username)))
                setattr(proj_instance,"apl_stud",str(list(app_username)))
                proj_instance.save()
            stud_instance.save()
        except :
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)

    @action(methods=['post'], detail=True)
    def withdraw_proj(self,request,pk):
        try:
            if(pk == None):
                raise Exception()
            stud_id = pk
            stud_instance = Student.objects.get(email=stud_id)
            for field in request.data:
                cur_lis = set()
                if(getattr(stud_instance,field)!="[]"):
                    cur_lis = set(map(int,getattr(stud_instance,field).strip('][').split(',')))
                new_add = int(request.data[field])
                if new_add in cur_lis:
                    cur_lis.remove(new_add)
                final_proj = str(list(cur_lis))
                setattr(stud_instance,field,final_proj)

                proj_instance = Project.objects.get(id=new_add)
                app_username = set()
                # print(app_username)
                if(getattr(proj_instance,"apl_stud")!="[]"):
                    app_username = set(ast.literal_eval(getattr(proj_instance,"apl_stud")))
                    # app_username = set(getattr(proj_instance,"apl_stud").strip('][').split(","))
                print(app_username,type(app_username))
                if stud_id in app_username:
                    app_username.remove(stud_id)
                # print(app_username)

                print(str(list(app_username)))
                setattr(proj_instance,"apl_stud",str(list(app_username)))
                proj_instance.save()


            stud_instance.save()
        except :
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)
