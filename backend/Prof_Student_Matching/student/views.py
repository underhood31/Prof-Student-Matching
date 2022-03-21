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

