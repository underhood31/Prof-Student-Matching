from rest_framework.routers import DefaultRouter
from django.urls import path,include
from .views import ProfViewSet,ProjectViewSet,ResearchFieldViewSet

router = DefaultRouter()
router.register(r'prof', ProfViewSet, basename='prof')
router.register(r'project', ProjectViewSet, basename='project')
router.register(r'rsfield', ResearchFieldViewSet, basename='rsfield')

urlpatterns = [
    path('',include(router.urls)),
]

