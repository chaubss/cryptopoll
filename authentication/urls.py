from django.urls import path, include
from . import views
from rest_framework_simplejwt.views import TokenRefreshView

urlpatterns = [
    path('google/', views.GoogleLogin.as_view(), name='google_login'),
    path('token/refresh/', TokenRefreshView.as_view(),
         name='token_refresh'),
]