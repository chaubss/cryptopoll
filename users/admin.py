from socket import AddressFamily
from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import User

class UserAdminConfig(UserAdmin):
    search_fields = ('email', 'first_name', 'last_name')
    list_filter = ('email', 'first_name', 'last_name', 'is_active', 'is_staff', 'role')
    ordering = ('email', )
    list_display = ('email', 'first_name', 'last_name', 'role')

    fieldsets = (
        (None, {'fields': ('email', 'password')}),
        ('Personal info', {'fields': ('first_name', 'last_name')}),
        ('Permissions & Role Management', {'fields': ('role', 'is_active', 'is_staff', 'is_superuser', 'groups', 'user_permissions')}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('email', 'password1', 'password2', 'first_name', 'last_name', 'role', 'is_staff', 'is_superuser')
        }),
    )

admin.site.register(User, UserAdminConfig)