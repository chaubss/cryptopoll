from rest_framework import exceptions
from rest_framework import permissions

class IsRoleAdmin(permissions.BasePermission):
    def has_permission(self, request, view):
        return request.user.role == 'AD'

class IsRoleUser(permissions.BasePermission):
    def has_permission(self, request, view):
        return request.user.role == 'US'

class IsRoleUserOrAdmin(permissions.BasePermission):
    def has_permission(self, request, view):
        return request.user.role == 'US' or request.user.role == 'AD'