from http.client import HTTPResponse
from django.shortcuts import render
from django.http import HttpResponse
from rest_framework.response import Response
from rest_framework import viewsets, permissions, status
from rest_framework.decorators import action


class StudentViewSet(viewsets.ModelViewSet):
    @action(methods=['get'], detail=False)
    def get_stud(self,request):
        return Response("Hello world",status=status.HTTP_200_OK)

