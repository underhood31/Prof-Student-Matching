from http.client import HTTPResponse
from django.shortcuts import render
from django.http import HttpResponse
from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action
from .serializers import StudentSerializer
from .models import Student

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
            stud_instance = Student.objects.get(roll_no=stud_id)
            print("here1")
            for field in request.data:
                if field == "proj_applied" or field=="proj_selected":
                    print("here2")
                    setattr(stud_instance,field,request.data[field])
                else:
                    pass
            stud_instance.save()
        except:
            return Response("Invalid Request", status=status.HTTP_400_BAD_REQUEST)

        return Response("Update Accepted", status=status.HTTP_200_OK)

